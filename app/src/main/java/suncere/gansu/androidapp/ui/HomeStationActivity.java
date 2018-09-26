package suncere.gansu.androidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.BR;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.MyAdapter;
import suncere.gansu.androidapp.adapter.MyViewHolder;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.model.HomeDataModel;
import suncere.gansu.androidapp.model.entity.HomeStationBean;
import suncere.gansu.androidapp.presenter.HomeStationPresenter;
import suncere.gansu.androidapp.ui.iview.IHomeStationView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.ToolUtils;

/**
 * Created by Hjo on 2017/7/6.
 */

public class HomeStationActivity extends MvpActivity<HomeStationPresenter> implements IHomeStationView,MyAdapter.RecyclerViewOnItmeClickListener,MyAdapter.OnItmeViewBinding {

    @BindView(R.id.home_title_refresh_image)
    ImageView home_title_refresh_image;

    @BindView(R.id.home_SwipeRefreshLayout)
    SwipeRefreshLayout home_SwipeRefreshLayout;

    @BindView(R.id.home_recyclerView)
    RecyclerView home_recyclerView;

    @BindView(R.id.home_title_text)
    TextView home_title_text;

    @BindView(R.id.home_TimeRange)
    SegmentControl home_TimeRange;

    @BindView(R.id.home_emptyText)
    LinearLayout home_emptyText;

    @BindView(R.id.home_time)
    TextView home_time;

    HomeStationPresenter mHomePresenter;
    String mCityCode;
    String mStationTypeID="1"; // 国控为1 省控为2 区县为3
    MyAdapter mMyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_station_activity);
        ButterKnife.bind(this );
        initView();
    }


    @Override
    public void onStart() {
        super.onStart();
        getHomeData();
        getAllCities();
    }

    @Override
    protected HomeStationPresenter createPresenter() {
        mHomePresenter=new HomeStationPresenter(this);
        return mHomePresenter;
    }

    private void initView(){
        home_SwipeRefreshLayout.setColorSchemeResources(R.color.aqi_1g,R.color.aqi_2g, R.color.aqi_3g,R.color.aqi_4g);
        home_SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeData();
            }
        });

        home_TimeRange.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mStationTypeID=""+(index+1);
                getHomeData();
            }
        });


        mCityCode=getIntent().getStringExtra("CityCode");

        mMyAdapter=new MyAdapter<HomeStationBean>(this,R.layout.home_recyclerview_itme, BR.homeStationBean);
        mMyAdapter.setOnItmeClickListener(this);
        mMyAdapter.setOnItmeViewBinding(this);
        home_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        home_recyclerView.setAdapter(mMyAdapter);
    }

    private void getHomeData(){
        mHomePresenter.getHomeData(mCityCode,mStationTypeID);
    }

    private void getAllCities(){
        mHomePresenter.getCities("2");
    }


    @OnClick({R.id.home_title_refresh_rela,R.id.home_title_back})//R.id.home_title_lin,
    public void on_Click(View view){
        switch (view.getId()){
            case R.id.home_title_refresh_rela:
                getHomeData();
                break;

            case R.id.home_title_back  :
                finish();
                break;
        }
    }

    @Override
    public void getDataSuccess(Object response) {
        if(response!=null) {
            HomeDataModel homeDataModel = (HomeDataModel) response;
            if (homeDataModel.getStationList() != null && homeDataModel.getStationList().size() > 0) {
                home_time.setText(ColorUtils.stringToData(homeDataModel.getStationList().get(0).getTimePoint(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm") + "更新");
                home_title_text.setText(homeDataModel.getCityName());
                home_emptyText.setVisibility(View.GONE);
            }else {
                home_emptyText.setVisibility(View.VISIBLE);
            }
            mMyAdapter.setData(homeDataModel.getStationList());
        }
    }

    @Override
    public void getAllCities(Object response) {

    }


    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showRefresh() {
        home_title_refresh_image.setAnimation(ToolUtils.getRefreshAnimation());
        home_SwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void finishRefresh() {
        home_title_refresh_image.clearAnimation();
        home_SwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void OnItemClick(Object obejct, int position) {
        Intent intent =new Intent(this,HomeStationChartActivity.class);
        HomeStationBean bean=(HomeStationBean)obejct;
        intent.putExtra("homeStationBean",bean);
        if (mStationTypeID.equals("3")){
            intent.putExtra("Type","1");
        }else{
            intent.putExtra("Type","2");
        }
        startActivity(intent);
    }

    @Override
    public void OnItmeView(View view, Object obejct , int position) {  // 数据修约
        HomeStationBean bean= (HomeStationBean) obejct;
        TextView pm2_5= MyViewHolder.getView(view,R.id.homestation_itme_pm2_5);
        TextView pm10 = MyViewHolder.getView(view,R.id.homestation_itme_pm10);

        if (!mStationTypeID.equals("3")){
            pm2_5.setText(ColorUtils.PM2_5DataChange(bean.getPM2_5()));
            pm10.setText(ColorUtils.PM2_5DataChange(bean.getPM10()));
        }else{
            pm2_5.setText(bean.getPM2_5());
            pm10.setText(bean.getPM10());
        }
    }
}
