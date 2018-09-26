package suncere.gansu.androidapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import suncere.gansu.androidapp.BR;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.HomeViewPageAdapter;
import suncere.gansu.androidapp.customview.MyUIPagerControlView;
import suncere.gansu.androidapp.customview.PollutantsView;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.customview.VerticalViewPager;
import suncere.gansu.androidapp.customview.kjchart.ChartView;
import suncere.gansu.androidapp.model.HomeDataChart24Model;
import suncere.gansu.androidapp.model.entity.HomeAllCitiesBean;
import suncere.gansu.androidapp.model.entity.HomeStationChartBean;
import suncere.gansu.androidapp.presenter.HomePresenter;
import suncere.gansu.androidapp.ui.iview.IHomeView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.ToolUtils;
import suncere.gansu.androidapp.utils.Tools;

/**
 * Created by Hjo on 2017/7/20.
 */

public class HomeFagment extends MvpFragment<HomePresenter> implements IHomeView, HomeViewPageAdapter.ViewpagerOnBindView {


    HomePresenter mHomePresenter;
    String mCityCode;

    private List<HomeAllCitiesBean> mHomeAllCitiesBean;
    private CharSequence[] mTitleCityNames;
    Tools mTool;
//    String[] mAreaCodeAndLevel;

    @BindView(R.id.home_viewPager)
    ViewPager home_viewPager;
    HomeViewPageAdapter<HomeAllCitiesBean> homeViewPageAdapter;

    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;

    @BindView(R.id.home_title_refresh_image)
    ImageView home_title_refresh_image;

    @BindView(R.id.selected_city_iv)
    ImageView selected_city_iv;

    @BindView(R.id.home_UIpager)
    MyUIPagerControlView home_UIpager;

    @BindView(R.id.home_chart24)
    ChartView chart24;

    @BindView(R.id.home_TimeRange_CP)
    SegmentControl home_TimeRange_CP;

    @BindView(R.id.home_PollutantsView)
    PollutantsView home_PollutantsView;

    @BindView(R.id.home_lfScrollView)
    VerticalViewPager home_ScrollView;

    @BindView(R.id.home_refresh_SwipeRefreshLayout)
    SwipeRefreshLayout refresh_SwipeRefreshLayout;

    String mpollutantCode = "99";

    List<String> mYvalue;
    List<String> mXvalue;
    List<Integer> mColors;
    int mindex = 1;
    List<TextView> mpollutantViews;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fagemnt, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getHomeAllCities();
//        getCityCharts();
    }

    @Override
    protected HomePresenter createPresenter() {
        mHomePresenter = new HomePresenter(this);
        return mHomePresenter;
    }

    private void initView() {
        mTool = new Tools(getActivity());

        mCityCode = "620100";//兰州市
        mHomeAllCitiesBean = new ArrayList<>();

        refresh_SwipeRefreshLayout.setColorSchemeResources(R.color.aqi_1g, R.color.aqi_2g, R.color.aqi_3g, R.color.aqi_4g);
        refresh_SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeAllCities();
            }
        });

        homeViewPageAdapter = new HomeViewPageAdapter<>(mHomeAllCitiesBean, R.layout.home_viewpage_itme, BR.homeAllCitiesBean);
        homeViewPageAdapter.setViewpagerOnBindView(this);
        home_viewPager.setAdapter(homeViewPageAdapter);


        xTablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                home_viewPager.setCurrentItem(tab.getPosition());
                getCityCharts();
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        home_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                xTablayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        home_PollutantsView.setmSelceTextListener(new PollutantsView.SelceTextListener() {
            @Override
            public void onSelect(String pollutantName, String pollutantCode) {
                mpollutantCode = pollutantCode;
                if(mindex==1||mindex==2){
                    if(mpollutantCode.equals("102"))
                        mpollutantCode="1028";
                }
                getCityCharts();
            }
        });
        mpollutantViews = home_PollutantsView.getListView();

        home_ScrollView.setmOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageChange(int pageIndex) {
                if (pageIndex == 1) {
                    getCityCharts();
                }
            }
        });

        home_TimeRange_CP.setSelectedIndex(1);
        home_TimeRange_CP.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mindex = index;
                Log.e("home mindex", "" + mindex);
                switch (index) {
                    case 0:
                        changPollutantViewText("AQI", "O3");
                        break;
                    case 1:
                        changPollutantViewText("AQI", "O3_8h");
                        break;
                    case 2:
                        changPollutantViewText("综合指数", "O3_8h");
                        break;
                }
                getCityCharts();
            }
        });

        mYvalue = new ArrayList<>();
        mXvalue = new ArrayList<>();
        mColors = new ArrayList<>();
    }

    private void getHomeAllCities() {
        mHomePresenter.getHomeAllCitiesData();
    }

    private void getCityCharts() {
        if (mindex == 2 && mpollutantCode.equals("99")) {
            mpollutantCode = "98";
        } else if (mindex != 2 && mpollutantCode.equals("98")) {
            mpollutantCode = "99";
        }
        mHomePresenter.getChartData(mCityCode, mpollutantCode, mindex);
    }


    @OnClick({R.id.home_title_refresh_rela, R.id.home_title_open_city, R.id.selected_city_iv})
    public void on_Click(View view) {
        switch (view.getId()) {
            case R.id.home_title_refresh_rela:
                getHomeAllCities();
                getCityCharts();
                break;
            case R.id.home_title_open_city:
                showCitySelectAlertDialog();
                break;

            case R.id.selected_city_iv:

                selected_city_iv.setImageResource(R.mipmap.icon_shangla);

                showCitySelectAlertDialog();
                break;
        }
    }

    @OnPageChange(R.id.home_viewPager)
    public void on_Pagechang(int index) {
        mCityCode = mHomeAllCitiesBean.get(index).getCityCode();
        home_UIpager.setSelectedIndex(index);
        mTool.setPageIndex(index);
    }

    @Override
    public void getDataSuccess(Object response) {

        mHomeAllCitiesBean.clear();
        xTablayout.removeAllTabs();
        if (response != null)
            mHomeAllCitiesBean.addAll((List<HomeAllCitiesBean>) response);

        mTitleCityNames = new CharSequence[mHomeAllCitiesBean.size()];
        for (int i = 0; i < mHomeAllCitiesBean.size(); i++) {
            HomeAllCitiesBean bean = mHomeAllCitiesBean.get(i);
            mTitleCityNames[i] = bean.getCityName();
            xTablayout.addTab(xTablayout.newTab().setText(bean.getCityName()));
        }

        homeViewPageAdapter.setData(mHomeAllCitiesBean);
        home_viewPager.setCurrentItem(mTool.getPageIndex());
        home_UIpager.setCount(home_viewPager.getAdapter().getCount());
        home_UIpager.setSelectedIndex(mTool.getPageIndex());

    }

    private void changPollutantViewText(String AQIText, String O3Text) {
        if (mpollutantViews != null && mpollutantViews.size() > 4) {
            if (mindex == 2) {
                mpollutantViews.get(0).setTextSize(10f);
            } else {
                mpollutantViews.get(0).setTextSize(14f);
            }
            mpollutantViews.get(0).setText(AQIText);
            mpollutantViews.get(3).setText(O3Text);

        }
    }

    @Override
    public void getCharts(Object response) {
        if (response != null)
            bindChart24(((HomeDataChart24Model) response).getChartDatas());
    }

    private void bindChart24(List<HomeStationChartBean> Chartdatas) {
        mYvalue.clear();
        mXvalue.clear();
        mColors.clear();

        if (Chartdatas != null && Chartdatas.size() > 0) {
            for (HomeStationChartBean bean : Chartdatas) {
                mYvalue.add(bean.getYValue());
                if (mindex == 0) {
                    mXvalue.add(ColorUtils.stringToData(bean.getLabelXValue(), "yyyy-MM-dd HH:mm:ss", "HH:mm"));
                } else if (mindex == 1) {
                    mXvalue.add(ColorUtils.stringToData(bean.getLabelXValue(), "yyyy-MM-dd HH:mm:ss", "MM-dd"));
                } else {
                    mXvalue.add(ColorUtils.stringToData(bean.getLabelXValue(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM"));
                }
                if (mindex == 2&&mpollutantCode.equals("98")) {
                    mColors.add(ColorUtils.getColorWithLevel("1"));
                } else {
                    mColors.add(ColorUtils.getColorWithLevel(bean.getLevel()));
                }
            }
        }

        chart24.mPointColors = mColors;
//        chart24.bindSingleLineChart(mYvalue,mXvalue);
        chart24.bindBarChart(mYvalue, mXvalue);
        chart24.mIs_AccordingTo_List_SetMin = true;
        chart24.isNeedMinValueMoreSmall = true;
        chart24.mYLineColor = Color.WHITE;
        chart24.mXLineColor = Color.WHITE;
        chart24.mIsShowPointColor = true;
        chart24.mLineColor = Color.WHITE;
        chart24.mdefaulYValueTextColor = Color.WHITE;

        chart24.mIsShowPiontToBottomBg = true;

        chart24.mBarChartSize = chart24.dp2px(16);
        chart24.mXScaleWidth = chart24.dp2px(24);
        chart24.mXAxisEveryFewParagraphs = 3;

        chart24.mXAxisTextColor = Color.WHITE;
        chart24.mYAxisTextColor = Color.WHITE;

        chart24.refreshChartView();
        chart24.mIs_AccordingTo_PointLabelValue_JudgmentColor = false;
    }


    private void showCitySelectAlertDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("请选择城市")
                .setItems(mTitleCityNames,
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface sender, int index) {
                                mCityCode = mHomeAllCitiesBean.get(index).getCityCode();
                                home_viewPager.setCurrentItem(index);
                                xTablayout.getTabAt(index).select();
                            }
                        })
                .setCancelable(true)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                    }
                }).show();

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showRefresh() {
        home_title_refresh_image.setAnimation(ToolUtils.getRefreshAnimation());
        refresh_SwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void finishRefresh() {
        home_title_refresh_image.clearAnimation();
        refresh_SwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBindingView(View view, Object object) {
        TextView home3_cityName_tv = (TextView) view.findViewById(R.id.home3_cityName_tv);
        home3_cityName_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeStationActivity.class);
                intent.putExtra("CityCode", mCityCode);
                startActivity(intent);
            }
        });

        RoundTextView station_detail_rtv = view.findViewById(R.id.station_detail_rtv);
        station_detail_rtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeStationActivity.class);
                intent.putExtra("CityCode", mCityCode);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mTool.setPageIndex(0);
        super.onDestroyView();
    }

}
