package suncere.gansu.androidapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.WarnAdapter;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.model.entity.WarnBean2;
import suncere.gansu.androidapp.presenter.WarnPresenter;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.IsConnectionNet;
import suncere.gansu.androidapp.utils.Tool;

@SuppressLint("NewApi")
public class ForecastWaringFragment extends MvpFragment<WarnPresenter> implements OnClickListener, IView {//, IViewPageItemViewInitHandlerV2

    TextView titleWarning_tv, titleAlert_tv, fw_timePoint;

    TextView Leven_tv;
    RecyclerView fw_listView;
    WarnAdapter mWarnAdapter;

    LinearLayout emptyview;

    SegmentControl forcast_warning_sc;

    private SwipeRefreshLayout refresh_layout = null;//刷新控件

    private int type = 0; //0 表示预警  1表示警报
    private int pollutantCode = 104;  //104表示PM10，105表示PM2.5

    SegmentControl segmentControl;
    IsConnectionNet connectionNet;

    private View view;

    WarnPresenter mWarnPresenter;

    @Override
    protected WarnPresenter createPresenter() {
        mWarnPresenter = new WarnPresenter(this);
        return mWarnPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forecastwarning_frag, null);
        InitViews();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        RefreshViewData();
    }

    @SuppressLint("NewApi")
    public void InitViews() {
        // TODO Auto-generated method stub
        titleWarning_tv = (TextView) view.findViewById(R.id.titleWarning);
        titleAlert_tv = (TextView) view.findViewById(R.id.titleAlert);
        fw_timePoint = (TextView) view.findViewById(R.id.fw_timePoint);

        segmentControl = (SegmentControl) view.findViewById(R.id.pollutantType);

        forcast_warning_sc=view.findViewById(R.id.forcast_warning_sc);
        forcast_warning_sc.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                if(index==0){
                    type = 0;
                    RefreshViewData();
                    refresh_layout.setRefreshing(true);
                    titleWarning_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_select));
                    titleAlert_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_unselect));
                    //选中颗粒物预警逻辑
                }else if(index==1){
                    type = 1;
                    RefreshViewData();
                    refresh_layout.setRefreshing(true);
                    titleAlert_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_select));
                    titleWarning_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_unselect));
                    //选中颗粒物警报逻辑
                }
            }
        });

        fw_listView = view.findViewById(R.id.fw_List);
        fw_listView.setLayoutManager(new LinearLayoutManager(getActivity()));


        emptyview = (LinearLayout) view.findViewById(R.id.emptyview);
        emptyview.setVisibility(View.GONE);

        bindListenter();

        refresh_layout = (SwipeRefreshLayout)
                view.findViewById(R.id.swipeRefresh);

        refresh_layout.setColorSchemeResources(R.color.aqi_1g, R.color.aqi_2g,
                R.color.aqi_3g, R.color.aqi_4g);


        connectionNet = new IsConnectionNet(getActivity());
        refresh_layout.setRefreshing(true);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refresh_layout.setRefreshing(true);

                if (connectionNet.isNetWorkAble()) {//网络可用情况下才会去检测是否能连接上服务器

                    connectionNet.pingHost();

                } else {
                    Toast.makeText(getActivity(), "当前网络不可用！", Toast.LENGTH_LONG).show();
                }
                RefreshViewData();

            }
        });

    }

    private void bindListenter() {
        titleAlert_tv.setOnClickListener(this);
        titleWarning_tv.setOnClickListener(this);

        segmentControl.setOnSegmentControlClickListener(controlClickListener);
    }


    public void BindData(List<WarnBean2> warnList) {
        // TODO Auto-generated method stub

        if(warnList==null||warnList.isEmpty()){
            emptyview.setVisibility(View.VISIBLE);
            fw_listView.setVisibility(View.GONE);
        }else{
            emptyview.setVisibility(View.GONE);
            fw_listView.setVisibility(View.VISIBLE);
        }

        mWarnAdapter = new WarnAdapter(warnList,getActivity());
        fw_listView.setAdapter(mWarnAdapter);
        fw_timePoint.setText("今天" + Tool.getNowHour() + ":00更新");
    }

    //刷新视图
    public void RefreshViewData() {
        if(connectionNet.isNetWorkAble()) {
            mWarnPresenter.getStationHourExecptionData(type + "", pollutantCode + "");
        }else{
            refresh_layout.setRefreshing(false);
            fw_listView.setVisibility(View.GONE);
            emptyview.setVisibility(View.VISIBLE);
        }
    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.titleAlert:
                type = 1;
                RefreshViewData();
                refresh_layout.setRefreshing(true);
                titleAlert_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_select));
                titleWarning_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_unselect));
                //选中颗粒物警报逻辑
                break;
            case R.id.titleWarning:
                type = 0;
                RefreshViewData();
                refresh_layout.setRefreshing(true);
                titleWarning_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_select));
                titleAlert_tv.setBackgroundColor(getActivity().getResources().getColor(R.color.forecastwarning_title_unselect));
                //选中颗粒物预警逻辑
                break;
            default:
                break;
        }
    }

    SegmentControl.OnSegmentControlClickListener controlClickListener = new SegmentControl.OnSegmentControlClickListener() {

        @Override
        public void onSegmentControlClick(int index) {
            // TODO Auto-generated method stub
            if (index == 0) {
                pollutantCode = 104;
            } else if (index == 1) {
                pollutantCode = 105;
            }
            refresh_layout.setRefreshing(true);
            RefreshViewData();


        }
    };

    @Override
    public void getDataFail(String msg) {
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void showRefresh() {
        refresh_layout.setRefreshing(true);
    }

    @Override
    public void finishRefresh() {
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void getDataSuccess(Object response) {
        if (response != null) {
            BindData((List<WarnBean2>) response);
        }

    }

}
