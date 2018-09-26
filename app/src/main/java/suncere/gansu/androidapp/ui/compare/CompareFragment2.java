package suncere.gansu.androidapp.ui.compare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.CompareAdapter;
import suncere.gansu.androidapp.customview.DateTimeTool;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.model.entity.CompareBean;
import suncere.gansu.androidapp.presenter.ComparePresenter2;
import suncere.gansu.androidapp.ui.MvpFragment;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.IsConnectionNet;
import suncere.gansu.androidapp.utils.TimerPikerTools;
import suncere.gansu.androidapp.utils.Tool;
import suncere.gansu.androidapp.utils.ToolUtils;
import suncere.gansu.androidapp.utils.Tools;


public class CompareFragment2 extends MvpFragment<ComparePresenter2> implements IView, OnClickListener {
    private static final String TAG = "CompareFragment2";

    AlertDialog.Builder cityAlert;
    String selectCityName, baseYear, compareYear, countIndex;
    int countType;
    TextView cityName, baseYearTextView, contrastYearTextView,
            TimePeriodTextView, com_time;
    SegmentControl segTimeRange;

    View TimePeriodLinearLayout;
    String[] preTimeIndex;

    Context context;
    SwipeRefreshLayout com_swipeRefresh;

    List<CharSequence> titleCityName;
    RecyclerView com_Listview;
    LinearLayout com_emptyText;
    IsConnectionNet connectionNet;

    private View view;

    ComparePresenter2 mComparePresenter2;
    CompareAdapter mCompareAdapter;

    TextView Stime_tv, ETime_tv, Pre_Stime_tv, Pre_Etime_tv, compare_query_tv;
    String Stime, ETime, Pre_Stime, Pre_Etime;

    SegmentControl isRemoveSandDust_segment;

    TimePickerView mTimePikerView;
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");//日时间格式

    Tools tools;

    boolean IsRemoveSandDust;

    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;

    @BindView(R.id.selected_city_iv)
    ImageView selected_city_iv;

    @Override
    protected ComparePresenter2 createPresenter() {
        mComparePresenter2 = new ComparePresenter2(this);
        return mComparePresenter2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        tools = new Tools(getActivity());

        baseYear = Tool.GetYearItems()[1].toString();
        compareYear = Tool.GetYearItems()[0].toString();

        preTimeIndex = new String[3];
        preTimeIndex[0] = String.format("" + Tool.GetSimpleTimeRange(1, baseYear, compareYear)[0]);
        preTimeIndex[1] = String.format("" + Tool.GetSimpleTimeRange(2, baseYear, compareYear)[0]);
        preTimeIndex[2] = String.format("" + Tool.GetSimpleTimeRange(3, baseYear, compareYear)[0]);

        selectCityName = tools.getCompareSelectedCity();
        //selecttimeIndex = preTimeIndex[0];

        countType = 5;
        countIndex = Tool.GetSimpleTimeRange(1, baseYear, compareYear)[0].toString();
        context = CompareFragment2.this.context;
    }


    @SuppressLint("NewApi")
    @Override
    public void onStart() {
        // TODO Auto-generate
        super.onStart();
        TimePeriodTextView.setText(countIndex);
        cityName.setText(selectCityName);
        baseYearTextView.setText(baseYear);
        contrastYearTextView.setText(compareYear);

        RefreshViewData();//获取数据
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.compare_frag, null);
        ButterKnife.bind(this, view);
        InitViews();
        initTabLayout();
        return view;
    }


    public void InitViews() {
        // TODO Auto-generated method stub

        Stime_tv = view.findViewById(R.id.Stime_tv);
        Stime_tv.setOnClickListener(this);
        ETime_tv = view.findViewById(R.id.ETime_tv);
        ETime_tv.setOnClickListener(this);
        Pre_Stime_tv = view.findViewById(R.id.Pre_Stime_tv);
        Pre_Stime_tv.setOnClickListener(this);
        Pre_Etime_tv = view.findViewById(R.id.Pre_Etime_tv);
        Pre_Etime_tv.setOnClickListener(this);
        compare_query_tv = view.findViewById(R.id.compare_query_tv);
        compare_query_tv.setOnClickListener(on_query_click);
        isRemoveSandDust_segment = view.findViewById(R.id.isRemoveSandDust_segment);
        isRemoveSandDust_segment.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                if (index == 0) {
                    IsRemoveSandDust = false;
                } else if (index == 1) {
                    IsRemoveSandDust = true;
                }
                RefreshViewData();
            }
        });

        Stime = ToolUtils.getThisYear1day();
        Stime_tv.setText(Stime + "");
        ETime = ToolUtils.getLastDay();
        ETime_tv.setText(ETime + "");
        Pre_Etime = ToolUtils.getLastYearLastday();
        Pre_Etime_tv.setText(Pre_Etime + "");
        Pre_Stime = ToolUtils.getLastYearThisMonth1day();
        Pre_Stime_tv.setText(Pre_Stime + "");

        cityName = (TextView) view.findViewById(R.id.cityName);
        baseYearTextView = (TextView) view.findViewById(R.id.baseYearTextView);
        contrastYearTextView = (TextView) view.findViewById(R.id.contrastYearTextView);
        TimePeriodTextView = (TextView) view.findViewById(R.id.TimePeriodTextView);

        connectionNet = new IsConnectionNet(getActivity());

        selected_city_iv.setOnClickListener(on_title_click);


        View layout = (LinearLayout) view.findViewById(R.id.citySelect);
        layout.setOnClickListener(on_title_click);

        segTimeRange = (SegmentControl) view.findViewById(R.id.segTimeRange);
        segTimeRange.setSelectedIndex(3);
        segTimeRange.setOnSegmentControlClickListener(On_TimeRange_Click);


        View baseYearLinearLayout = (LinearLayout) view
                .findViewById(R.id.baseYearLinearLayout);
        baseYearLinearLayout.setOnClickListener(on_baseYearLinearLayout_click);

        View contrastYearLinearLayout = (LinearLayout) view
                .findViewById(R.id.contrastYearLinearLayout);
        contrastYearLinearLayout
                .setOnClickListener(on_contrastYearLinearLayout_click);


        TimePeriodLinearLayout = (RelativeLayout) view
                .findViewById(R.id.TimePeriodLinearLayout);
        TimePeriodLinearLayout
                .setOnClickListener(on_TimePeriodLinearLayout_click);
        TimePeriodLinearLayout.setVisibility(View.GONE);

        titleCityName = new ArrayList<>();
        titleCityName = Arrays.asList(Tool.getCitys222());

        com_Listview = view.findViewById(R.id.com_List);
        com_Listview.setLayoutManager(new LinearLayoutManager(getActivity()));

        com_emptyText = view.findViewById(R.id.com_emptyText);

        com_time = view.findViewById(R.id.com_time);


        com_swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.com_swipeRefresh);

        com_swipeRefresh.setColorSchemeResources(R.color.aqi_1g, R.color.aqi_2g,
                R.color.aqi_3g, R.color.aqi_4g);

        com_swipeRefresh.setRefreshing(true);

        com_swipeRefresh
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                    @Override
                    public void onRefresh() {
                        com_swipeRefresh.setRefreshing(true);

                        if (connectionNet.isNetWorkAble()) {//网络可用情况下才会去检测是否能连接上服务器

                            connectionNet.pingHost();

                        } else {
                            Toast.makeText(getActivity(), "当前网络不可用！", Toast.LENGTH_LONG).show();
                        }
                        RefreshViewData();
                    }
                });
    }

    public void initTabLayout() {
        for (CharSequence cityName : titleCityName) {
            xTablayout.addTab(xTablayout.newTab().setText(cityName.toString()));
        }

        xTablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                selectCityName = titleCityName.get(tab.getPosition()).toString();
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


    public void bindData(List<CompareBean> compareBeanList) {

        mCompareAdapter = new CompareAdapter(compareBeanList);
        com_Listview.setAdapter(mCompareAdapter);

        if (connectionNet.isNetWorkAble()) {//有网
            com_time.setText("更新至" + Tool.getYesterday());
        } else {
            com_time.setText("更新至" + connectionNet.getLastRefreshTime());//无网络时显示上一次更新时间
        }

        if (compareBeanList.isEmpty()) {
            com_emptyText.setVisibility(View.VISIBLE);
            com_Listview.setVisibility(View.INVISIBLE);
        } else {
            com_emptyText.setVisibility(View.GONE);
            com_Listview.setVisibility(View.VISIBLE);
        }

        titleCityName.clear();
    }


    public void RefreshViewData() {
        mComparePresenter2.getCompareData2(selectCityName, Stime, ETime, Pre_Stime, Pre_Etime, String.valueOf(IsRemoveSandDust));
    }

    public void OnAsyncLoadPostExecute() {
        com_swipeRefresh.setRefreshing(false);

    }


    OnClickListener on_query_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RefreshViewData();
        }
    };


    OnClickListener on_title_click = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            selected_city_iv.setImageResource(R.mipmap.icon_shangla);
            // TODO Auto-generated method stub
            cityAlert = new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择城市").setItems((CharSequence[]) titleCityName.toArray(new CharSequence[titleCityName.size()]),
                    new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            // cityName.setText(cityArray[index]);
                            selectCityName = titleCityName.get(index).toString();
                            cityName.setText(selectCityName);
                            com_swipeRefresh.setRefreshing(true);
                            xTablayout.getTabAt(index).select();
                            selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                            RefreshViewData();
                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                }
            });

            cityAlert.show();
        }

    };
    OnClickListener on_baseYearLinearLayout_click = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            // if (cityAlert == null) {

            cityAlert = new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择基准年").setItems(Tool.GetYearItems(),
                    new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            // cityName.setText(cityArray[index]);
                            baseYear = Tool.GetYearItems()[index]
                                    .toString().replace("年", "");
                            baseYearTextView.setText(baseYear);
                            changeTimePeriodTextView(baseYear, compareYear);
                            com_swipeRefresh.setRefreshing(true);
                            RefreshViewData();
                        }
                    });
            cityAlert.show();
        }

    };
    OnClickListener on_contrastYearLinearLayout_click = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            cityAlert = new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择目标年").setItems(Tool.GetYearItems(),
                    new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            // cityName.setText(cityArray[index]);
                            compareYear = Tool.GetYearItems()[index]
                                    .toString().replace("年", "");
                            contrastYearTextView.setText(compareYear);
                            changeTimePeriodTextView(baseYear, compareYear);
                            com_swipeRefresh.setRefreshing(true);
                            RefreshViewData();
                        }
                    });
            cityAlert.show();
        }

    };


    OnClickListener on_TimePeriodLinearLayout_click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            cityAlert = new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择时间").setItems(Tool.GetSimpleTimeRange(countType, baseYear, compareYear),
                    new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            // cityName.setText(cityArray[index]);
                            countIndex = Tool
                                    .GetSimpleTimeRange(countType, baseYear, compareYear)[index]
                                    .toString();
                            TimePeriodTextView.setText(countIndex);
                            preTimeIndex[countType - 1] = countIndex;

                            com_swipeRefresh.setRefreshing(true);
                            RefreshViewData();

                        }
                    });
            cityAlert.show();
        }

    };

    public void changeTimePeriodTextView(String baseYearString, String compareYearString) {


        String bString = baseYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        String cString = compareYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        String countString = countIndex.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

        int baseYear = 0;
        int compareYear = 0;
        int count = 0;
        try {
            baseYear = Integer.valueOf(bString);
            compareYear = Integer.valueOf(cString);
            count = Integer.valueOf(countString);
        } catch (Exception e) {
            // TODO: handle exception
        }

        Date date = DateTimeTool.GetNow();
        int year = DateTimeTool.GetYear(date);


        if (year != baseYear && year != compareYear) {

        } else {
            if (countType == 1) {
                int week2 = DateTimeTool.GetWeekOfYear(date);
                if (count > week2) {
                    countIndex = Tool.GetSimpleWeekItems(baseYearString, compareYearString)[0] + "";
                    TimePeriodTextView.setText(countIndex);
                }
            } else if (countType == 2) {
                int week2 = DateTimeTool.GetMonth(date);
                if (count > week2) {
                    countIndex = Tool.GetSimpleMonthItems(baseYearString, compareYearString)[0] + "";
                    TimePeriodTextView.setText(countIndex);
                }
            } else if (countType == 3) {
                int week2 = DateTimeTool.GetSeason(date);
                if (count > week2) {
                    countIndex = Tool.GetSimpleSeasonItems(baseYearString, compareYearString)[0] + "";
                    TimePeriodTextView.setText(countIndex);
                }
            }
        }
    }

    SegmentControl.OnSegmentControlClickListener On_TimeRange_Click = new SegmentControl.OnSegmentControlClickListener() {
        @Override
        public void onSegmentControlClick(int index) {
            countType = index + 1;
            if (index != 3) {
                TimePeriodTextView.setText(preTimeIndex[index]);
            }
            if (index == 3) {
                countType = 5;
                com_time.setVisibility(View.VISIBLE);
                TimePeriodLinearLayout.setVisibility(View.GONE);
            } else {
                com_time.setVisibility(View.GONE);
                TimePeriodLinearLayout.setVisibility(View.VISIBLE);
                countIndex = preTimeIndex[index];
            }
            com_swipeRefresh.setRefreshing(true);
            RefreshViewData();
        }
    };


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

        if (response != null) {
            bindData((List<CompareBean>) response);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Stime_tv:
                mTimePikerView = TimerPikerTools.creatTimePickerView(getActivity(), "选择基准起始时间", true, true, true, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Stime = dayFormat.format(date);
                        Stime_tv.setText(Stime + "");
                    }
                });
                mTimePikerView.show();
                break;

            case R.id.ETime_tv:
                mTimePikerView = TimerPikerTools.creatTimePickerView(getActivity(), "选择基准结束时间", true, true, true, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ETime = dayFormat.format(date);
                        ETime_tv.setText(ETime + "");
                    }
                });
                mTimePikerView.show();
                break;

            case R.id.Pre_Stime_tv:
                mTimePikerView = TimerPikerTools.creatTimePickerView(getActivity(), "选择对比起始时间", true, true, true, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Pre_Stime = dayFormat.format(date);
                        Pre_Stime_tv.setText(Pre_Stime + "");
                    }
                });
                mTimePikerView.show();
                break;

            case R.id.Pre_Etime_tv:
                mTimePikerView = TimerPikerTools.creatTimePickerView(getActivity(), "选择基准结束时间", true, true, true, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Pre_Etime = dayFormat.format(date);
                        Pre_Etime_tv.setText(Pre_Etime + "");
                    }
                });
                mTimePikerView.show();
                break;
        }
    }

    //必须使用EventBus的订阅注解
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Bundle bundle){
        selectCityName = bundle.getString("selectCityName");
        RefreshViewData();
        Log.e("lys", "onEvent: "+selectCityName );

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
