package suncere.gansu.androidapp.ui.compare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.CompareAdapter3;
import suncere.gansu.androidapp.model.entity.CompareBean3;
import suncere.gansu.androidapp.presenter.ComparePresenter2;
import suncere.gansu.androidapp.ui.MvpFragment;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.TimerPikerTools;
import suncere.gansu.androidapp.utils.ToolUtils;
import suncere.gansu.androidapp.utils.Tools;

/**
 * @author lys
 * @time 2018/9/21 10:52
 * @desc:
 */

public class CompareFragment3 extends MvpFragment<ComparePresenter2> implements IView,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.compare_rv)
    RecyclerView  compare_rv;

    @BindView(R.id.com_emptyText)
    LinearLayout com_emptyText;

    @BindView(R.id.com_swipeRefresh)
    SwipeRefreshLayout com_swipeRefresh;

    @BindView(R.id.Stime_tv)
    TextView Stime_tv;

    @BindView(R.id.ETime_tv)
    TextView ETime_tv;

    String Stime, ETime;
    String cityName="14";

    TimePickerView mTimePikerView;
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");//日时间格式

    ComparePresenter2 comparePresenter;
    CompareAdapter3 compareAdapter3;

    String selectCityName;

    Tools tools;

    List<CompareBean3> compareBean3s=new ArrayList<>();

    private View view;

    @Override
    protected ComparePresenter2 createPresenter() {
        comparePresenter=new ComparePresenter2(this);
        return comparePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_compare3, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RefreshData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView() {
        tools = new Tools(getActivity());
        selectCityName = tools.getCompareSelectedCity() + "";

        compare_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        compareAdapter3=new CompareAdapter3(compareBean3s);

        com_swipeRefresh.setColorSchemeColors(ColorUtils.Colors);
        com_swipeRefresh.setOnRefreshListener(this);

        Stime_tv = view.findViewById(R.id.Stime_tv);
        ETime_tv = view.findViewById(R.id.ETime_tv);
        Stime = ToolUtils.getThisYear1day();
        Stime_tv.setText(Stime + "");
        ETime = ToolUtils.getLastDay();
        ETime_tv.setText(ETime + "");
    }

    @OnClick({R.id.Stime_tv,R.id.ETime_tv,R.id.compare_query_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.Stime_tv:
                mTimePikerView = TimerPikerTools.creatTimePickerView(getActivity(), "选择起始时间", true, true, true, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Stime = dayFormat.format(date);
                        Stime_tv.setText(Stime + "");
                    }
                });
                mTimePikerView.show();
                break;

            case R.id.ETime_tv:
                mTimePikerView = TimerPikerTools.creatTimePickerView(getActivity(), "选择结束时间", true, true, true, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ETime = dayFormat.format(date);
                        ETime_tv.setText(ETime + "");
                    }
                });
                mTimePikerView.show();
                break;

            case R.id.compare_query_tv:
                RefreshData();
                break;
        }
    }

    //获取数据
    public void RefreshData(){
        comparePresenter.getCompareData3(cityName,Stime,ETime);
    }

    @Override
    public void getDataFail(String msg) {
        com_swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showRefresh() {
        com_swipeRefresh.setRefreshing(true);
    }

    @Override
    public void finishRefresh() {
        com_swipeRefresh.setRefreshing(false);
    }

    @Override
    public void getDataSuccess(Object response) {
        if(response!=null){
            compareBean3s= (List<CompareBean3>) response;
            compareAdapter3.setNewData(compareBean3s);
            compareAdapter3.notifyDataSetChanged();
            compare_rv.setAdapter(compareAdapter3);

        }

        if(compareBean3s!=null&&!compareBean3s.isEmpty()){
            compare_rv.setVisibility(View.VISIBLE);
            com_emptyText.setVisibility(View.INVISIBLE);
        }else{
            compare_rv.setVisibility(View.INVISIBLE);
            com_emptyText.setVisibility(View.VISIBLE);
        }

    }

    //必须使用EventBus的订阅注解
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Bundle bundle) {
        selectCityName = bundle.getString("selectCityName");
//        RefreshViewData();
        Log.e("lys", "onEvent: " + selectCityName);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        RefreshData();
    }
}
