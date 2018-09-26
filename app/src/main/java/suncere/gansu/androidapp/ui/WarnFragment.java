package suncere.gansu.androidapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.BR;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.MyViewHolder;
import suncere.gansu.androidapp.adapter.ViewAdapterTwoView;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.model.entity.WarnBean;
import suncere.gansu.androidapp.presenter.WarnPresenter;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.ToolUtils;
import suncere.gansu.androidapp.utils.Tools;

/**
 * Created by Hjo on 2017/7/4.
 */

public class WarnFragment extends MvpFragment<WarnPresenter> implements IView {

    WarnPresenter mWarnPresenter;
    @BindView(R.id.warn_title_refresh_image)
    ImageView refresh_image;

    @BindView(R.id.warn_swipeRefreshLayout)
    SwipeRefreshLayout warn_swipeRefreshLayout;

    @BindView(R.id.warn_TimeRange)
    SegmentControl warn_TimeRange;

    @BindView(R.id.warn_recyclerView)
    RecyclerView warn_recyclerView;

    Tools mTool;
    String [] mAreaCodeAndLevel;
    String mtype="0"; // 0:突变警报  1：异常警报
    String mCityCode;
    ViewAdapterTwoView mMyAdapter;

    @BindView(R.id.warn_emptyText)
    LinearLayout warn_emptyText;

    @Override
    protected WarnPresenter createPresenter() {
        mWarnPresenter=new WarnPresenter(this);
        return mWarnPresenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.warn_fragment,container,false);
        ButterKnife.bind(this,view);
        initView( );
        return view;
    }

    private void initView( ){
        mTool=new Tools(getActivity());
        mAreaCodeAndLevel=mTool.getAreaCodeAndLevel();
        mCityCode=mAreaCodeAndLevel[0];

        warn_TimeRange.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mtype=""+ index;
                getData();
            }
        });
        warn_swipeRefreshLayout.setColorSchemeResources(R.color.aqi_1g,R.color.aqi_2g, R.color.aqi_3g,R.color.aqi_4g);
        warn_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        mMyAdapter=new ViewAdapterTwoView(getActivity(),R.layout.warn_recyclerview_itme_0, R.layout.warn_recyclerview_itme_1,BR.warnBean,BR.warnBean1);
        mMyAdapter.setOnBindItmeView(new ViewAdapterTwoView.RecyclerViewOnBindItmeView() {
            @Override
            public void OnBindItmeView(View view, Object obejct, int position, int ViewType) {
                Log.e("warn"," 类型：ViewType= "+ ViewType + "   位置：position= "+position);
                if (ViewType==1){
                    if (mtype.equals("0"))
                    ( (TextView)MyViewHolder.getView(view,R.id.warn_time_itme)) .setText( ColorUtils.getPutTime("HH时突变信息", ( (WarnBean)obejct ).getTimePoint()) );//
                    else if (mtype.equals("1"))
                        ( (TextView)MyViewHolder.getView(view,R.id.warn_time_itme)) .setText( ColorUtils.getPutTime("HH时异常信息", ( (WarnBean)obejct ).getTimePoint()) );//
                   else
                        ( (TextView)MyViewHolder.getView(view,R.id.warn_time_itme)) .setText( ColorUtils.getPutTime("HH时超标信息", ( (WarnBean)obejct ).getTimePoint()) );//
                }
            }
        });
        warn_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        warn_recyclerView.setAdapter(mMyAdapter);
    }


    private void getData(){
        mWarnPresenter.getListData(mtype,mCityCode);
    }

    @Override
    public void getDataSuccess(Object response) {
        if (response!=null && ((List<WarnBean>)response).size()>0){
            warn_emptyText.setVisibility(View.GONE);
            warn_recyclerView.setVisibility(View.VISIBLE);
            seachTimeData( (List<WarnBean>)response );
        }else{
            warn_emptyText.setVisibility(View.VISIBLE);
            warn_recyclerView.setVisibility(View.GONE);
        }

    }
    @OnClick(R.id.warn_title_refresh_rela)
    public void OnClick(View view){
        getData();

    }

    /**
     * 查找数据中  第一个不同时间的bean  并将其置为true
     * @param listData
     */
    private void seachTimeData(List<WarnBean>listData){
        String time="00000";
        for(WarnBean bean : listData){
            if (bean.getTimePoint().equals(time)){ //同一类时间警报
                bean.setIsheadView(false);
            }else{ // 不同类时间
                time=bean.getTimePoint();
                bean.setIsheadView(true);
            }
        }
        mMyAdapter.setData(listData);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showRefresh() {
        warn_swipeRefreshLayout.setRefreshing(true);
        refresh_image.setAnimation(ToolUtils.getRefreshAnimation());
    }

    @Override
    public void finishRefresh() {
        warn_swipeRefreshLayout.setRefreshing(false);
        refresh_image.clearAnimation();
    }
}
