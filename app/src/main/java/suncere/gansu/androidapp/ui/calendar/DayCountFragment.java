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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.customview.DateTimeTool;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.customview.kjchart.ChartView;
import suncere.gansu.androidapp.model.AQDayCountModel;
import suncere.gansu.androidapp.model.TimeRangeEnum;
import suncere.gansu.androidapp.presenter.CalendarPresenter;
import suncere.gansu.androidapp.ui.MvpFragment;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.IsConnectionNet;
import suncere.gansu.androidapp.utils.Tool;
import suncere.gansu.androidapp.utils.ToolUtils;


@SuppressLint("ValidFragment")
public class DayCountFragment extends MvpFragment<CalendarPresenter> implements IView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.day_count_citySelect)
    LinearLayout day_count_citySelect;

    @BindView(R.id.day_count_cityName)
    TextView day_count_cityName;

    @BindView(R.id.to_calendar)
    TextView to_calendar;

//    @BindView(R.id.pieChart)
//    PieChart pieChart;

    @BindView(R.id.barChart)
    ChartView barChart;

    @BindView(R.id.segTimeRange)
    SegmentControl segTimeRange;

    @BindView(R.id.TimePeriodLinearLayout)
    RelativeLayout TimePeriodLinearLayout;

    @BindView(R.id.timeIndexDDL)
    TextView timeIndex;

    @BindView(R.id.YearTextView)
    TextView YearTextView;

    @BindView(R.id.day_time_tv)
    TextView day_time_tv;

    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    @BindView(R.id.calendar_daycount_select)
    SegmentTabLayout calendar_daycount_select;

    @BindView(R.id.selected_city_iv)
    ImageView selected_city_iv;

    private String[] mTitles = {"空气日历", "统计天数"};

    int[] viewArr;
    String[] levelArr;//等级

    String mSelectCityName = "甘肃14城市";
    IsConnectionNet connectionNet;
    Tool mTool;
    List<CharSequence> titleCityName;

    AlertDialog.Builder cityAlert;

    String selectCityName, selecttimeIndex, selectYear;
    int countType = 4;

    String preTimeIndex[];
    CharSequence[] timeIndexArray;

    TimeRangeEnum timeRange;

    private View view;

    CalendarPresenter mCalendarPresenter;

    CalendarAndCountFragment calendarAndCountFragment;

    List<String> mYvalue;
    List<String> mXvalue;
    List<Integer> mColors;

    @SuppressLint("ValidFragment")
    public DayCountFragment(CalendarAndCountFragment calendarAndCountFragment) {
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
        view = inflater.inflate(R.layout.fragment_day_count, null);
        ButterKnife.bind(this, view);
        initView();
        initTabLayout();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        RefreshViewData();
    }

    public void initView() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setColorSchemeColors(ColorUtils.Colors);

        mYvalue = new ArrayList<>();
        mXvalue = new ArrayList<>();
        mColors = new ArrayList<>();

        mXvalue.add("优");
        mXvalue.add("良");
        mXvalue.add("轻度污染");
        mXvalue.add("中度污染");
        mXvalue.add("重度污染");
        mXvalue.add("严重污染");


        mColors.add(ColorUtils.Colors[0]);
        mColors.add(ColorUtils.Colors[1]);
        mColors.add(ColorUtils.Colors[2]);
        mColors.add(ColorUtils.Colors[3]);
        mColors.add(ColorUtils.Colors[4]);
        mColors.add(ColorUtils.Colors[5]);


        calendar_daycount_select.setTabData(mTitles);
        calendar_daycount_select.setCurrentTab(1);
        calendar_daycount_select.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==0){
                    calendarAndCountFragment.setTabSelection(0);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

//        creatChart(pieChart);

        viewArr = new int[]{R.id.day_count_tv, R.id.day_excellent_tv, R.id.g1Val, R.id.g2Val, R.id.g3Val, R.id.g4Val,
                R.id.g5Val, R.id.g6Val};
        levelArr = new String[]{"优良天数", "优良率", "优", "良", "轻度污染", "中度污染", "重度污染", "严重污染"};

        preTimeIndex = new String[4];

        Date date = DateTimeTool.GetNow();
        preTimeIndex[0] = "" + GetWeekItems((String) Tool.GetYearItems()[0])[0];
        preTimeIndex[1] = String.format("第%d月", DateTimeTool.GetMonth(date));
//		preTimeIndex[2]=String.format("第%d季度",DateTimeTool.GetSeason(date) );// 2017-01-01 16:41 分修复bug 用getSeasonHJO(date)替换DateTimeTool.GetSeason(date)
        //原因为：DateTimeTool.GetSeason(date)的方法中返回的月份是从0-11的 造成季度不正确
        preTimeIndex[2] = String.format("第%d季度", Tool.getSeasonHJO(date));
        preTimeIndex[3] = (String) Tool.GetYearItems()[0];

        // TODO Auto-generated method stub
        connectionNet = new IsConnectionNet(getActivity());
        mTool = new Tool();
        titleCityName = new ArrayList<CharSequence>();
        titleCityName = Arrays.asList(Tool.getCitys222());

        selectCityName = "甘肃14城市";
        timeRange = TimeRangeEnum.Year;
        selecttimeIndex = preTimeIndex[0];
        selectYear = preTimeIndex[3];
        connectionNet = new IsConnectionNet(getActivity());

        day_count_cityName.setText(mSelectCityName);
        YearTextView.setText(selectYear);
        timeIndex.setText(getSimpleTimeRange(timeRange, selecttimeIndex));

        segTimeRange.setSelectedIndex(3);
        segTimeRange.setOnSegmentControlClickListener(On_TimeRange_Click);
        TimePeriodLinearLayout.setVisibility(View.INVISIBLE);

        day_time_tv.setText("更新至" + Tool.getYesterday());
    }

    SegmentControl.OnSegmentControlClickListener On_TimeRange_Click = new SegmentControl.OnSegmentControlClickListener() {
        @Override
        public void onSegmentControlClick(int index) {
            timeRange = TimeRangeEnum.ValueOf(index + 1);
            countType = index + 1;
            if (index == 3) {
                TimePeriodLinearLayout.setVisibility(View.INVISIBLE);
            } else {
                TimePeriodLinearLayout.setVisibility(View.VISIBLE);
                selecttimeIndex = preTimeIndex[index];
                timeIndex.setText(getSimpleTimeRange(timeRange, preTimeIndex[index]));
            }
            RefreshViewData();
        }
    };


    public void initTabLayout() {
        for (CharSequence cityName : titleCityName) {
            xTablayout.addTab(xTablayout.newTab().setText(cityName.toString()));
        }

        xTablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                mSelectCityName = titleCityName.get(tab.getPosition()).toString();
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

    @OnClick({R.id.to_calendar, R.id.day_count_citySelect, R.id.YearTextView, R.id.TimePeriodLinearLayout,R.id.selected_city_iv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.to_calendar:
                calendarAndCountFragment.setTabSelection(0);
                break;

            case R.id.selected_city_iv:
                selected_city_iv.setImageResource(R.mipmap.icon_shangla);
                new AlertDialog.Builder(getActivity()).setTitle(
                        "请选择城市").setItems((CharSequence[]) titleCityName.toArray(new CharSequence[titleCityName.size()]),
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface sender, int index) {
                                mSelectCityName = titleCityName.get(index).toString();
                                day_count_cityName.setText(mSelectCityName);
                                xTablayout.getTabAt(index).select();
                                selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                                RefreshViewData();
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                    }
                }).show();
                break;

            case R.id.YearTextView:
                chooseYear();
                break;

            case R.id.TimePeriodLinearLayout:
                timeIndexArray = GetTimeRange(timeRange, selectYear);
                AlertDialog.Builder alert = new AlertDialog.Builder(DayCountFragment.this.getActivity()).setTitle("请选择时间").setItems(timeIndexArray, new AlertDialog.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(DialogInterface sender, int index) {
                        timeIndex.setText(getSimpleTimeRange(timeRange, "" + timeIndexArray[index]));
                        selecttimeIndex = timeIndexArray[index].toString();
                        preTimeIndex[timeRange.Value() - 1] = selecttimeIndex;

                        RefreshViewData();
                    }
                });
                alert.show();
                break;
        }

    }




    public void chooseYear() {
        cityAlert = new AlertDialog.Builder(getActivity()).setTitle(
                "请选择年份").setItems(Tool.GetYearItems(),
                new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface sender, int index) {
                        // cityName.setText(cityArray[index]);
                        selectYear = Tool.GetYearItems()[index].toString();

                        //selecttimeIndex=day_yearString+day;
                        YearTextView.setText(selectYear);
                        preTimeIndex[3] = selectYear;
//                        updateTimeRange(timeRange, selectYear, selecttimeIndex);
                        RefreshViewData();
                    }
                });
        cityAlert.show();
    }


    private void RefreshViewData() {
        refresh_layout.setRefreshing(true);
        if (countType == 1) {
            Log.i("lys", ToolUtils.subString(selecttimeIndex, "第", "周"));
            selecttimeIndex = ToolUtils.subString(selecttimeIndex, "第", "周");
        }
        mCalendarPresenter.getCityDayCount(mSelectCityName, countType + "", ToolUtils.exceptChinese(selectYear), ToolUtils.exceptChinese(selecttimeIndex));
    }


    @Override
    public void getDataFail(String msg) {
        refresh_layout.setRefreshing(false);
        barChart.setVisibility(View.INVISIBLE);
        haveNoCountData();
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
            barChart.setVisibility(View.VISIBLE);
            bindCountData((AQDayCountModel) response);
//            bindChartData((AQDayCountModel) response);
            bindBarChartData((AQDayCountModel) response);
        } else {
            barChart.setVisibility(View.INVISIBLE);
            haveNoCountData();
        }
    }


    //{"优良天数", "优良率", "优", "良", "轻度污染", "中度污染", "重度污染", "严重污染"};
    public void bindBarChartData(AQDayCountModel aqDayCountModel) {
        mYvalue.clear();
        if (aqDayCountModel != null) {
            mYvalue.add(aqDayCountModel.getG1() + "");
            mYvalue.add(aqDayCountModel.getG2() + "");
            mYvalue.add(aqDayCountModel.getG3() + "");
            mYvalue.add(aqDayCountModel.getG4() + "");
            mYvalue.add(aqDayCountModel.getG5() + "");
            mYvalue.add(aqDayCountModel.getG6() + "");
        }

        barChart.mPointColors = mColors;
        barChart.bindBarChart(mYvalue, mXvalue);
        // holder.barChart.chartEnum = ChartEnum.BarChart;
        barChart.mIs_AccordingTo_List_SetMin = true;
        barChart.isNeedMinValueMoreSmall = true;
        barChart.mYLineColor = Color.WHITE;
        barChart.mXLineColor = Color.WHITE;

        barChart.mIsShowPointColor = true;

        barChart.mIs_AccordingTo_PointLabelValue_JudgmentColor = false;

        barChart.mDefaultPointColor = Color.rgb(22, 189, 62);
        barChart.mdefaulYValueTextColor = Color.WHITE;

        barChart.mXAxisEveryFewParagraphs = 0;

        barChart.mBarChartSize = barChart.dp2px(24);
        barChart.mXScaleWidth = barChart.dp2px(43);

        barChart.mXAxisTextColor = Color.WHITE;
        barChart.mYAxisTextColor = Color.WHITE;
        barChart.refreshChartView();

    }


    public void bindCountData(AQDayCountModel aqDayCountModel) {
        ((TextView) view.findViewById(viewArr[0])).setText(String.format("优良天数：" + (Integer.valueOf(aqDayCountModel.getG1()) + Integer.valueOf(aqDayCountModel.getG2())) + "天"));
        ((TextView) view.findViewById(viewArr[1])).setText(String.format("优良率为：" + aqDayCountModel.getG12Per() + "%%"));
        ((TextView) view.findViewById(viewArr[2])).setText(String.format("%s：%d 天", levelArr[2], aqDayCountModel.getG1()));
        ((TextView) view.findViewById(viewArr[3])).setText(String.format("%s：%d 天", levelArr[3], aqDayCountModel.getG2()));
        ((TextView) view.findViewById(viewArr[4])).setText(String.format("%s：%d 天", levelArr[4], aqDayCountModel.getG3()));
        ((TextView) view.findViewById(viewArr[5])).setText(String.format("%s：%d 天", levelArr[5], aqDayCountModel.getG4()));
        ((TextView) view.findViewById(viewArr[6])).setText(String.format("%s：%d 天", levelArr[6], aqDayCountModel.getG5()));
        ((TextView) view.findViewById(viewArr[7])).setText(String.format("%s：%d 天", levelArr[7], aqDayCountModel.getG6()));
    }

    public void haveNoCountData() {
        ((TextView) view.findViewById(viewArr[0])).setText(String.format("优良天数："));
        ((TextView) view.findViewById(viewArr[1])).setText(String.format("优良率为："));
        ((TextView) view.findViewById(viewArr[2])).setText(String.format("%s：", levelArr[2]));
        ((TextView) view.findViewById(viewArr[3])).setText(String.format("%s：", levelArr[3]));
        ((TextView) view.findViewById(viewArr[4])).setText(String.format("%s：", levelArr[4]));
        ((TextView) view.findViewById(viewArr[5])).setText(String.format("%s：", levelArr[5]));
        ((TextView) view.findViewById(viewArr[6])).setText(String.format("%s：", levelArr[6]));
        ((TextView) view.findViewById(viewArr[7])).setText(String.format("%s：", levelArr[7]));
    }

//    ArrayList<PieEntry> entries;
//    List<Integer> colors;
//
//    public void bindChartData(AQDayCountModel aqDayCountModel) {
//        entries = new ArrayList<>();
//        colors = new ArrayList<>();
//        entries.add(new PieEntry((float) aqDayCountModel.getG1(), levelArr[2]));
//        colors.add(ColorUtils.Colors[0]);
//        entries.add(new PieEntry((float) aqDayCountModel.getG2(), levelArr[3]));
//        colors.add(ColorUtils.Colors[1]);
//        entries.add(new PieEntry((float) aqDayCountModel.getG3(), levelArr[4]));
//        colors.add(ColorUtils.Colors[2]);
//        entries.add(new PieEntry((float) aqDayCountModel.getG4(), levelArr[5]));
//        colors.add(ColorUtils.Colors[3]);
//        entries.add(new PieEntry((float) aqDayCountModel.getG5(), levelArr[6]));
//        colors.add(ColorUtils.Colors[4]);
//        entries.add(new PieEntry((float) aqDayCountModel.getG6(), levelArr[7]));
//        colors.add(ColorUtils.Colors[5]);
//
//        PieDataSet dataSet = new PieDataSet(entries, null);
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//        dataSet.setColors(colors);
////        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.BLACK);
//
//        pieChart.setData(data);
//        pieChart.highlightValues(null);
//        pieChart.invalidate();
//        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//
//        Legend l = pieChart.getLegend();
//        l.setEnabled(true);                    //是否启用图列（true：下面属性才有意义）
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
////        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setForm(Legend.LegendForm.CIRCLE); //设置图例的形状
//        l.setFormSize(8f);                    //设置图例的大小
//        l.setFormToTextSpace(5f);            //设置每个图例实体中标签和形状之间的间距
//        l.setDrawInside(false);
//        l.setWordWrapEnabled(true);           //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
//        l.setXEntrySpace(6f);                //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
//        l.setYEntrySpace(8f);                 //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
//        l.setTextSize(7f);                   //设置图例标签文本的大小
//
////        l.setTextColor(Color.parseColor("#ff9933"));//设置图例标签文本的颜色
//    }

    //    private void creatChart(PieChart chart) {
//
//        chart.setUsePercentValues(true);
//        chart.getDescription().setEnabled(false);
//        chart.setExtraOffsets(15, 0, 15, 10);
//        chart.setDragDecelerationFrictionCoef(0.95f);
//        chart.setHoleColor(Color.WHITE);
//        chart.setTransparentCircleColor(Color.WHITE);
//        chart.setTransparentCircleAlpha(110);
//
//        chart.setDrawHoleEnabled(true);
//        chart.setHoleRadius(20f); //设置PieChart内部圆的半径
//        chart.setTransparentCircleRadius(30f);
//        chart.setCenterTextRadiusPercent(30f);
//
//        chart.setRotationAngle(0);
//        chart.setRotationEnabled(true);
//        chart.setHighlightPerTapEnabled(true);
//        chart.highlightValues(null);// 不显示文字
//        chart.setDrawEntryLabels(false);  //  设置是否绘制标签
//        chart.setEntryLabelTextSize(11f);
//        chart.setDrawSlicesUnderHole(false);
//        chart.setDrawHoleEnabled(false);
//    }
//
    @Override
    public void onRefresh() {
        RefreshViewData();
    }


    @SuppressWarnings("unused")
    private String getSimpleTimeRange(TimeRangeEnum TE, String timeRange) {

        if (TE == TimeRangeEnum.Week) {
            return timeRange.substring(0, timeRange.indexOf("("));
        } else {
            return timeRange;
        }
    }

    private void updateTimeRange(TimeRangeEnum index, String year, String timeRange) {

        if (index != TimeRangeEnum.Year) {


            CharSequence[] aCharSequences = GetTimeRange(index, year);
            String New = aCharSequences[0].toString().replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

            if (index == TimeRangeEnum.Week) {
                timeRange = timeRange.substring(0, timeRange.indexOf("("));
                New = New.substring(0, New.indexOf("("));
            }
            String Old = timeRange.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

            int News = Integer.parseInt(New);
            int Olds = Integer.parseInt(Old);

            if (Olds > News) {
                selecttimeIndex = aCharSequences[0].toString();
                preTimeIndex[index.Value() - 1] = selecttimeIndex;
                timeIndex.setText(selecttimeIndex);
            }
        }
    }


    private CharSequence[] GetTimeRange(TimeRangeEnum index, String year) {

        if (index == TimeRangeEnum.Week) {
            return GetWeekItems(year);
        } else if (index == TimeRangeEnum.Month) {
            return GetMonthItems(year);

        } else if (index == TimeRangeEnum.Season) {
            return GetSeasonItems(year);
        }
        return null;
    }


    //	private CharSequence[] GetWeekItems(String year)
//	{
//		Date date=DateTimeTool.GetNow();
//
////		CharSequence[] result=new CharSequence[52];
////		int week=DateTimeTool.GetWeekOfYear(date);
//
//		int NowYear=DateTimeTool.GetYear(date);
//		//String strings = year.substring(0, year.indexOf("("));
//		String oldYearString = year.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
//
//
//		int OldYear = Integer.parseInt(oldYearString);
//
//
//
//		CharSequence[] result = null;
//
//		if (NowYear == OldYear) {
//			int week2 = Tool.GetWeekOfYear2(date);
//			 result = new CharSequence[week2];
//
//			for (int i = 0; i < result.length; i++) {
//				result[i] = String.format("第%d周(%s)",week2,getWeekDateRange(OldYear, week2));
//				week2--;
//			}
//		}else {
//			int week = 52;
//		    result = new CharSequence[week];
//
//			for (int i = 0; i < 52; i++) {
//			result[i] = String.format("第%d周(%S)",week,getWeekDateRange(OldYear, week));
//			week--;
//		    }
//		}
//
//
//
//
//		//Date date=DateTimeTool.GetNow();
////		for(int i=0;i<result.length;i++)
////		{
////			int year=DateTimeTool.GetYear(date);
////			int week=DateTimeTool.GetWeekOfYear(date);
////			result[i]=String.format("%d年第%d周",  year,week);
////			date=DateTimeTool.AddDays(date, -7);
////		}
//
//		return result;
//	}
    private CharSequence[] GetWeekItems(String year) {
        Date date = DateTimeTool.GetNow();

//		CharSequence[] result=new CharSequence[52];
//		int week=DateTimeTool.GetWeekOfYear(date);

        int NowYear = DateTimeTool.GetYear(date);
        //String strings = year.substring(0, year.indexOf("("));
        String oldYearString = year.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

        int OldYear = Integer.parseInt(oldYearString);

        CharSequence[] result = null;

        if (NowYear == OldYear) {
            int week2 = Tool.GetWeekOfYear2(date);
            result = new CharSequence[week2];

            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("第%d周(%s)", week2, getWeekDateRange(OldYear, week2));
                week2--;
            }
        } else {
            int week = getAllWeeks(oldYearString);
            result = new CharSequence[week];

            for (int i = 0; i < getAllWeeks(oldYearString); i++) {
                result[i] = String.format("第%d周(%S)", week, getWeekDateRange(OldYear, week));
                week--;
            }
        }


        //Date date=DateTimeTool.GetNow();
//		for(int i=0;i<result.length;i++)
//		{
//			int year=DateTimeTool.GetYear(date);
//			int week=DateTimeTool.GetWeekOfYear(date);
//			result[i]=String.format("%d年第%d周",  year,week);
//			date=DateTimeTool.AddDays(date, -7);
//		}

        return result;
    }

    public int getAllWeeks(String year) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(year + "-12-31"));

        } catch (ParseException e) {

        }
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week != 53) {
            week = 52;
        }
        return week;
    }

    public String getWeekDateRange(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);

        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
        cal.add(Calendar.DATE, -day_of_week);

        String one = (new SimpleDateFormat("M月d号")).format(cal.getTime());

        cal.add(Calendar.DATE, 6);

        String two = (new SimpleDateFormat("M月d号")).format(cal.getTime());

        return one + "-" + two;
    }

//	@SuppressWarnings("unused")
//	private String getWeekDateRange(int year, int week) {
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf=new SimpleDateFormat("M月d号");
//        cal.clear();
//
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        cal.set(Calendar.YEAR, year);
//        cal.set(Calendar.WEEK_OF_YEAR,week);
//
////		Log.e("hjo", "一周的开始是 ："+Calendar.DAY_OF_WEEK);
//		int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
//	    if (day_of_week == 0 ) {
//	        day_of_week = 7 ;
//	    }
//
//	    cal.add(Calendar.DATE , -day_of_week + 1 );
//
//
//	    //cal.setTime(date);
//        String one, two;
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        one = sdf.format(cal.getTime());
//
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//        two = sdf.format(cal.getTime());
//
//        return one+"—"+two;
//	}


    private CharSequence[] GetMonthItems(String year) {
        Date date = DateTimeTool.GetNow();
        int NowYear = DateTimeTool.GetYear(date);
        String oldYearString = year.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        int OldYear = Integer.parseInt(oldYearString);
        CharSequence[] result = null;
        if (NowYear == OldYear) {
            int month = DateTimeTool.GetMonth(date);
            result = new CharSequence[month];
            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("%d月", month);
                month--;
            }

        } else {
            int month = 12;
            result = new CharSequence[month];
            for (int i = 0; i < 12; i++) {
                result[i] = String.format("%d月", month);
                month--;
            }

        }
//		CharSequence[] result=new CharSequence[12];
//
//		Date date=DateTimeTool.GetNow();
//		for(int i=0;i<result.length;i++)
//		{
//			int year=DateTimeTool.GetYear(date);
//			int month=DateTimeTool.GetMonth(date);
//			result[i]=String.format("%d月",month);
//			date=DateTimeTool.AddMonths(date, -1);
//		}

        return result;
    }

    private CharSequence[] GetSeasonItems(String year) {

        Date date = DateTimeTool.GetNow();
        int NowYear = DateTimeTool.GetYear(date);
        String oldYearString = year.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        int OldYear = Integer.parseInt(oldYearString);
        CharSequence[] result = null;
        if (NowYear == OldYear) {
//			int season=DateTimeTool.GetSeason(date);
            int season = Tool.getSeasonHJO(date);
            result = new CharSequence[season];

            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("第%d季度", season);
                season--;
            }

        } else {
            int season = 4;
            result = new CharSequence[season];

            for (int i = 0; i < 4; i++) {
                result[i] = String.format("第%d季度", season);
                season--;
            }

        }


//		CharSequence[] result=new CharSequence[4];
//
//		Date date=DateTimeTool.GetNow();
//		for(int i=0;i<result.length;i++)
//		{
//			int year=DateTimeTool.GetYear(date);
//			int season=DateTimeTool.GetSeason(date);
//			result[i]=String.format("第%d季度",season);
//			date=DateTimeTool.AddMonths(date, -3);
//		}

        return result;
    }

    private CharSequence[] GetYearItems() {
        CharSequence[] result = new CharSequence[5];

        Date date = DateTimeTool.GetNow();
        int year = DateTimeTool.GetYear(date);
        for (int i = 0; i < result.length; i++) {
            result[i] = String.format("%d年", year - i);
        }
        //new AsyncLoad().c
        return result;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden)
            calendar_daycount_select.setCurrentTab(1);
    }
}
