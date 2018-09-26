package suncere.gansu.androidapp.ui.calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.suncere.lib.CalendarDay;
import com.suncere.lib.CalendarMode;
import com.suncere.lib.MaterialCalendarView;
import com.suncere.lib.OnMonthChangedListener;
import com.suncere.lib.format.TitleFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.model.AQDayCountModel;
import suncere.gansu.androidapp.model.entity.AQIDayInfoEty;
import suncere.gansu.androidapp.presenter.CalendarPresenter;
import suncere.gansu.androidapp.ui.MvpFragment;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.IsConnectionNet;
import suncere.gansu.androidapp.utils.ListType;
import suncere.gansu.androidapp.utils.Tool;
import suncere.gansu.androidapp.utils.ToolUtils;


@SuppressLint("ValidFragment")
public class MoreCalendarFragment extends MvpFragment<CalendarPresenter> implements IView, SwipeRefreshLayout.OnRefreshListener {

    String mSelectCityName = "甘肃14城市";

    IsConnectionNet connectionNet;
    Tool mTool;
    List<CharSequence> titleCityName;
    int[] viewArr;

    LinearLayout more_calendar_year_layout, more_calendar_month_layout, more_calendar_citySelect, more_calendar_PollutantName_layout;
    TextView more_calendar_year, more_calendar_month, more_calendar_cityName, more_calendar_PollutantName;

    @BindView(R.id.to_dayCount)
    TextView to_dayCount;

    @BindView(R.id.day_time_tv)
    TextView day_time_tv;

    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;

    @BindView(R.id.calendar_daycount_select)
    SegmentTabLayout calendar_daycount_select;

    @BindView(R.id.selected_city_iv)
    ImageView selected_city_iv;

    SwipeRefreshLayout refresh_layout;

    private String Year;
    private String Month;

    MaterialCalendarView materialCalendarView;
    TitleFormatter mFormatter;
    private CharSequence[] mWeekDay = {"日", "一", "二", "三", "四", "五", "六"};

    private String[] mTitles = {"空气日历", "统计天数"};

    List<CalendarDay> mlist; //日期
    List<Integer> mlistclors;//对应的颜色等级
    protected int[] colors;//条带颜色数组
    Calendar mCalendar;

    double[] MonthDatasource;
    String[] levelArr;//等级
    String mPollutantName = "AQI";
    CharSequence[] mPollutantNames;

    CalendarPresenter mCalendarPresenter;

    View view;

    CalendarAndCountFragment calendarAndCountFragment;

    @SuppressLint("ValidFragment")
    public MoreCalendarFragment(CalendarAndCountFragment calendarAndCountFragment) {
        this.calendarAndCountFragment = calendarAndCountFragment;
    }

    @Override
    protected CalendarPresenter createPresenter() {
        mCalendarPresenter = new CalendarPresenter(this);
        return mCalendarPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.more_calendar_frag, null);
        ButterKnife.bind(this, view);
        initViews();
        initTabLayout();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        RefreshViewData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("lys","MoreCalendatFragment is onResume()");
    }

    public void initViews() {
        // TODO Auto-generated method stub
        connectionNet = new IsConnectionNet(getActivity());
        mTool = new Tool();
        titleCityName = new ArrayList<CharSequence>();
        titleCityName = Arrays.asList(Tool.getCitys222());

        Year = Tool.getNowYear() + "年";
        Month = Tool.getNowMonth() + "月";
        colors = new int[]{
                Color.rgb(109, 105, 105),//0 为无效值
                Color.rgb(97, 179, 16),
                Color.rgb(223, 209, 6),
                Color.rgb(237, 158, 5),
                Color.rgb(212, 10, 26),
                Color.rgb(173, 11, 174),
                Color.rgb(99, 6, 17)
        };
        mCalendar = Calendar.getInstance();


        calendar_daycount_select.setTabData(mTitles);
        calendar_daycount_select.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==1){
                    calendarAndCountFragment.setTabSelection(1);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        viewArr = new int[]{R.id.day_count_tv, R.id.day_excellent_tv, R.id.g1Val, R.id.g2Val, R.id.g3Val, R.id.g4Val,
                R.id.g5Val, R.id.g6Val};
        levelArr = new String[]{"优良天数", "优良率", "优", "良", "轻度污染", "中度污染", "重度污染", "严重污染"};
        MonthDatasource = new double[8];

        refresh_layout = view.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(ColorUtils.Colors);
        refresh_layout.setOnRefreshListener(this);


        more_calendar_year_layout = (LinearLayout) view.findViewById(R.id.more_calendar_year_layout);
        more_calendar_year_layout.setOnClickListener(on_Year_click);
        more_calendar_year = (TextView) view.findViewById(R.id.more_calendar_year);
        more_calendar_year.setText(Year);


        more_calendar_month_layout = (LinearLayout) view.findViewById(R.id.more_calendar_month_layout);
        more_calendar_month_layout.setOnClickListener(on_TimeMonth_click);
        more_calendar_month = (TextView) view.findViewById(R.id.more_calendar_month);
        more_calendar_month.setText(Month);

        more_calendar_citySelect = (LinearLayout) view.findViewById(R.id.more_calendar_citySelect);
        more_calendar_citySelect.setOnClickListener(on_titleCity_click);
        selected_city_iv.setOnClickListener(on_titleCity_click);
        more_calendar_cityName = (TextView) view.findViewById(R.id.more_calendar_cityName);
        more_calendar_cityName.setText(mSelectCityName);

        mPollutantNames = new CharSequence[]{"AQI", "PM2.5", "PM10"};
        more_calendar_PollutantName_layout = (LinearLayout) view.findViewById(R.id.more_calendar_PollutantName_layout);
        more_calendar_PollutantName_layout.setOnClickListener(on_PollutantName_click);
        more_calendar_PollutantName = (TextView) view.findViewById(R.id.more_calendar_PollutantName);
        more_calendar_PollutantName.setText(mPollutantName);

        mlist = new ArrayList<CalendarDay>();
        mlistclors = new ArrayList<Integer>();

        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.more_calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2013, 0, 1))
                .setMaximumDate(CalendarDay.from(Tool.getNowYear(), Tool.getNowMonth() - 1, Tool.getNowDay()))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);//SELECTION_MODE_MULTIPLE可选多个日期
        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_DECORATED_DISABLED);//SHOW_DECORATED_DISABLED 在当前月份不显示其他月份的日期
        materialCalendarView.setSelectionColor(Color.parseColor("#678abc"));//设置选中的日期背景色
        materialCalendarView.setArrowColor(Color.parseColor("#678abc"));//设置头部左和右箭头的颜色
        materialCalendarView.setWeekDayLabels(mWeekDay);
        materialCalendarView.setTitleTextColor(Color.parseColor("#678abc"));
        mFormatter = new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                return day.getYear() + "年" + (day.getMonth() + 1) + "月";
            }
        };

        materialCalendarView.setTitleFormatter(mFormatter);
        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {//设置对滑动月份的监听
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Year = date.getYear() + "年";
                Month = date.getMonth() + 1 + "月";

                more_calendar_year.setText(Year);
                more_calendar_month.setText(Month);
                RefreshViewData();
            }
        });

        day_time_tv.setText("更新至" + Tool.getYesterday());

    }

    public void initTabLayout(){
        for(CharSequence cityName:titleCityName){
            xTablayout.addTab(xTablayout.newTab().setText(cityName.toString()));
        }

        xTablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                mSelectCityName = titleCityName.get(tab.getPosition()).toString();
                more_calendar_cityName.setText(mSelectCityName);
                RefreshViewData();
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });
    }


    @OnClick({R.id.to_dayCount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_dayCount:
                calendarAndCountFragment.setTabSelection(1);
                break;
        }
    }


    public void bindCountData(AQDayCountModel aqDayCountModel) {
        ((TextView) view.findViewById(viewArr[0])).setText(String.format("优良天数：" + (Integer.valueOf(aqDayCountModel.getG1()) + Integer.valueOf(aqDayCountModel.getG2())) + "天"));
        ((TextView) view.findViewById(viewArr[1])).setText(String.format("优良率为：" + aqDayCountModel.getG12Per()+"%%"));
        ((TextView) view.findViewById(viewArr[2])).setText(String.format("%s：%d 天", levelArr[2], aqDayCountModel.getG1()));
        ((TextView) view.findViewById(viewArr[3])).setText(String.format("%s：%d 天", levelArr[3], aqDayCountModel.getG2()));
        ((TextView) view.findViewById(viewArr[4])).setText(String.format("%s：%d 天", levelArr[4], aqDayCountModel.getG3()));
        ((TextView) view.findViewById(viewArr[5])).setText(String.format("%s：%d 天", levelArr[5], aqDayCountModel.getG4()));
        ((TextView) view.findViewById(viewArr[6])).setText(String.format("%s：%d 天", levelArr[6], aqDayCountModel.getG5()));
        ((TextView) view.findViewById(viewArr[7])).setText(String.format("%s：%d 天", levelArr[7], aqDayCountModel.getG6()));
    }


    public void bindData(List<AQIDayInfoEty> aqiDayInfoEtyList) {
        // 获取日历中的数据   并渲染日历表
        if (aqiDayInfoEtyList != null && aqiDayInfoEtyList.size() != 0) {
            materialCalendarView.setVisibility(View.VISIBLE);
            mlist.clear();
            mlistclors.clear();
            for (int i = 0; i < aqiDayInfoEtyList.size(); i++) {
                // 添加选中的时间  和对应的等级颜色值
                mlist.add(CalendarDay.from(Integer.valueOf(ToolUtils.getYear(aqiDayInfoEtyList.get(i).getDate())),
                        Integer.valueOf(ToolUtils.getMonth(aqiDayInfoEtyList.get(i).getDate())) - 1,
                        Integer.valueOf(ToolUtils.getDay(aqiDayInfoEtyList.get(i).getDate()))
                ));
                mlistclors.add(getColorForLevel(aqiDayInfoEtyList.get(i).getLevel().toString()));


                Log.i("lys", "年：" + Integer.valueOf(ToolUtils.getYear(aqiDayInfoEtyList.get(i).getDate()))
                        + "月：" + Integer.valueOf(ToolUtils.getMonth(aqiDayInfoEtyList.get(i).getDate()))
                        + "日：" + Integer.valueOf(ToolUtils.getDay(aqiDayInfoEtyList.get(i).getDate())));

                Log.i("lys", "颜色等级：" + getColorForLevel(aqiDayInfoEtyList.get(i).getLevel().toString()));
            }
            materialCalendarView.setSelectDatas(mlist, mlistclors);
            materialCalendarView.setSelectedDate(mCalendar);
        } else {
            materialCalendarView.setVisibility(View.GONE);
        }
    }


    public void BindData(HashMap<String, List<HashMap<String, Object>>> datasourceCollection) {
        // TODO Auto-generated method stub


////		获取优良数据    给TextView 赋值
//        if (datasourceCollection.get("MorePieChartModel")!=null && datasourceCollection.get("MorePieChartModel").size()!=0) {
//
////			Log.e("hjo"," 优良天数的数据： MorePieChartModel ="+ datasourceCollection.get("MorePieChartModel").toString());
//
//            MonthDatasource[0]=Double.valueOf(datasourceCollection.get("MorePieChartModel").get(0).get("G1").toString());
//            MonthDatasource[1]=Double.valueOf(datasourceCollection.get("MorePieChartModel").get(0).get("G2").toString());
//            MonthDatasource[2]=Double.valueOf(datasourceCollection.get("MorePieChartModel").get(0).get("G3").toString());
//            MonthDatasource[3]=Double.valueOf(datasourceCollection.get("MorePieChartModel").get(0).get("G4").toString());
//            MonthDatasource[4]=Double.valueOf(datasourceCollection.get("MorePieChartModel").get(0).get("G5").toString());
//            MonthDatasource[5]=Double.valueOf(datasourceCollection.get("MorePieChartModel").get(0).get("G6").toString());
//
//            int G1G2=(int) (MonthDatasource[0] + MonthDatasource[1]);
//            CoundDay0.setText("优良天数："+G1G2);
//            CoundDay1.setText("优良率为："+datasourceCollection.get("MorePieChartModel").get(0).get("G12Per").toString());
//        }
//        for (int i = 0; i < MonthDatasource.length; i++) {
//            ((TextView)view.findViewById( viewArr[i] )).setText( String.format( "%s：%d 天",levelArr[i], (int)MonthDatasource[i])  );
//        }


        // 获取日历中的数据   并渲染日历表
        if (datasourceCollection.get("MoreCalendarDayModel") != null && datasourceCollection.get("MoreCalendarDayModel").size() != 0) {
            materialCalendarView.setVisibility(View.VISIBLE);
            mlist.clear();
            mlistclors.clear();
            for (int i = 0; i < datasourceCollection.get("MoreCalendarDayModel").size(); i++) {
                // 添加选中的时间  和对应的等级颜色值
                mlist.add(CalendarDay.from(Integer.valueOf(datasourceCollection.get("MoreCalendarDayModel").get(i).get("Date_Year").toString()),
                        Integer.valueOf(datasourceCollection.get("MoreCalendarDayModel").get(i).get("Date_Month").toString()) - 1,
                        Integer.valueOf(datasourceCollection.get("MoreCalendarDayModel").get(i).get("Date_Day").toString())
                ));
                mlistclors.add(getColorForLevel(datasourceCollection.get("MoreCalendarDayModel").get(i).get("Level").toString()));
            }
            materialCalendarView.setSelectDatas(mlist, mlistclors);
            materialCalendarView.setSelectedDate(mCalendar);
        } else {
            materialCalendarView.setVisibility(View.GONE);
        }
    }


    OnClickListener on_titleCity_click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            selected_city_iv.setImageResource(R.mipmap.icon_shangla);
            new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择城市").setItems((CharSequence[]) titleCityName.toArray(new CharSequence[titleCityName.size()]),
                    new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            // cityName.setText(cityArray[index]);
                            mSelectCityName = titleCityName.get(index).toString();
                            more_calendar_cityName.setText(mSelectCityName);
                            selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                            RefreshViewData();
                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                }
            }).show();
        }
    };

    OnClickListener on_Year_click = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择年份").setItems(Tool.GetYearItems(),
                    new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            // cityName.setText(cityArray[index]);
                            sender.dismiss();
                            Year = Tool.GetYearItems()[index]
                                    .toString();
                            if (mTool.getIntYear(Year).equals(Tool.getNowYear() + "")) {//如果跳转到当前年份
                                if (Integer.valueOf(mTool.getIntYear(Month)) > Tool.getNowMonth()) { //当前年份的
                                    Month = Tool.getNowMonth() + "月";
                                    more_calendar_month.setText(Month);
                                }
                            }
                            more_calendar_year.setText(Year);
                            jumpToPage(Integer.valueOf(mTool.getIntYear(Year)), Integer.valueOf(mTool.getIntYear(Month)));
                            RefreshViewData();
                        }
                    }).show();
        }

    };


    OnClickListener on_TimeMonth_click = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择月份").setItems(mTool.getMonthForYear(mTool.getIntYear(Year)),
                    new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            sender.dismiss();
                            Month = mTool.getMonthForYear(mTool.getIntYear(Year))[index].toString();
                            more_calendar_month.setText(Month);
                            jumpToPage(Integer.valueOf(mTool.getIntYear(Year)), Integer.valueOf(mTool.getIntYear(Month)));
                            RefreshViewData();
                        }
                    }).show();
        }

    };

    OnClickListener on_PollutantName_click = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择污染物").setItems(mPollutantNames,
                    new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            sender.dismiss();
                            mPollutantName = mPollutantNames[index].toString();
                            more_calendar_PollutantName.setText(mPollutantName);
                            RefreshViewData();
                        }
                    }).show();
        }

    };

    /**
     * 根据等级获取颜色值
     *
     * @param level
     * @return
     */
    public int getColorForLevel(String level) {

        try {
            return colors[Integer.valueOf(level)];
        } catch (Exception e) {
            // TODO: handle exception
            return colors[0];//0 为数据无效时返回
        }
    }
//	public int  getColorForLevel(String level){
//			for (int i = 0; i < levelArr.length; i++) {
//				if (level.equals(levelArr[i])) {
//					return colors[i];
//				}
//			}
//		return colors[6];
//
//	}

    public void jumpToPage(int year, int month) {
        int index = (year - 2013) * 12 + month - 1;//跳转到的页面
        materialCalendarView.setSelectMonthPage(index);
    }

    public void RefreshViewData() {
        refresh_layout.setRefreshing(true);
        mCalendarPresenter.getCityDayLevelInfoData(mSelectCityName, mPollutantName, mTool.getIntYear(Year), mTool.getIntYear(Month));
        mCalendarPresenter.getCityDayCount(mSelectCityName, ListType.MonthTime.getIndex() + "", mTool.getIntYear(Year), mTool.getIntYear(Month));
    }


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
            if (response instanceof AQDayCountModel) {
                bindCountData((AQDayCountModel) response);
            } else {
                bindData((List<AQIDayInfoEty>) response);
            }
        }
    }

    @Override
    public void onRefresh() {
        if (connectionNet.isNetWorkAble()) {//网络可用情况下才会去检测是否能连接上服务器
            connectionNet.pingHost();
        } else {
            Toast.makeText(getActivity(), "当前网络不可用！", Toast.LENGTH_LONG).show();
        }
        RefreshViewData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden)
            calendar_daycount_select.setCurrentTab(0);

    }
}
