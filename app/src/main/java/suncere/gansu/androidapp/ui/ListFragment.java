package suncere.gansu.androidapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.BR;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.MyAdapter;
import suncere.gansu.androidapp.adapter.MyViewHolder;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.databinding.ListFragmentBinding;
import suncere.gansu.androidapp.model.entity.ListBean;
import suncere.gansu.androidapp.presenter.ListPresenter;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.ListSort;
import suncere.gansu.androidapp.utils.ListType;
import suncere.gansu.androidapp.utils.TimerPikerTools;

/**
 * Created by Hjo on 2017/5/12.
 * DataType: 0为站点、1为区县、2为城市（暂限0,2）
 * CountType:0为日排名、1为周排名、2为季排名、3为年排名
 * CountIndex:统计范围，第几周、第几季（暂无作用）
 * PollutantType:参数类型aqi||pm2_5||pm10||no2||so2||o3||co
 * <p>
 * dataType 0：站点 1：区县  2：站点
 * countType 0实时 1日均 2 月  3 年
 * pollutantType  综合指数 、aqi、pm2_5、pm10；  分别对应： 98  99  100 101
 * StationTypeID 1国控 2 省控
 */
public class ListFragment extends MvpFragment<ListPresenter> implements IView, MyAdapter.OnItmeViewBinding {

    ListPresenter mListPresenter;
    ListFragmentBinding mBinding;
    int mdataType = 0, mcountType, mpollutantType, mStationTypeID;

    MyAdapter mMyAdapter;

    List<ListBean> mlistBean;
    List<ListBean> mtempCity; // 江西11城市
    boolean misSequence = true; //正序 倒序
    private ArrayAdapter<String> mPollutants;
    List<String> mlistPollutants;
    //  String []Pollutants={"综合指数","AQI","SO2","NO2","O3","CO","PM2.5","PM10"};
    String[] Pollutants;

    @BindView(R.id.selected_time_tv)
    TextView selected_time_tv;

    TimePickerView mTimePikerView;//时间选择器
    String mHourdate;//选择的时间
    String mDayDate;
    String mMonDate;
    boolean isShowDay;
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");//日时间格式
    SimpleDateFormat mothFormat = new SimpleDateFormat("yyyy-MM-01");//月时间格式
    SimpleDateFormat mothFormat2 = new SimpleDateFormat("yyyy-MM");//月时间格式

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    protected ListPresenter createPresenter() {
        mListPresenter = new ListPresenter(this);
        return mListPresenter;
    }


    private void initView() {
        mdataType = 0;
        mcountType = 0;
        mStationTypeID = 1;
        mpollutantType = 98;

        mlistBean = new ArrayList<>();
        mtempCity = new ArrayList<>();
        mlistPollutants = new ArrayList<>();

        mDayDate = TimerPikerTools.getNowDay() + "";
        mMonDate = TimerPikerTools.getNowMonth() + "";
        mHourdate = mDayDate;

        //实时  日  月  年
        mBinding.listTimeSelect.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mcountType = index;
                changeUI();
                getData();
            }
        });


        /**
         * 优良天数=97，综合指数=98,AQI=99,SO2=100,NO2=101,O3=102,CO=103,PM2.5=104,PM10=104
         */
        mBinding.listTimeRangeCP.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mStationTypeID = index + 1;
                switch (index) {
                    case 0: // 站点 国控
                        mStationTypeID = ListType.Country.getIndex();
                        mdataType = ListType.Stationg.getIndex();
                        break;
                    case 1: // 站点 省控
                        mStationTypeID = ListType.Province.getIndex();
                        mdataType = ListType.Stationg.getIndex();
                        break;
                    case 2:// 区县
                        mdataType = ListType.CountyStationg.getIndex();
                        break;
                    case 3: // 城市
                        mdataType = ListType.CityStationg.getIndex();
                        break;
                    case 4://全国
                        mdataType = ListType.CountryStationg.getIndex();
                        break;

                    case 5://169城市
                        mdataType = ListType.City169.getIndex();
                        break;

                    case 6://31省
                        mdataType = ListType.Provice31.getIndex();
                        break;
                }
                changeUI();
                getData();//获取数据
            }
        });

        // 污染物
        Pollutants = new String[]{"AQI", "SO2", "NO2", "O3_8H", "CO", "PM10", "PM2.5"};
        mlistPollutants.addAll(Arrays.asList(Pollutants));
        mPollutants = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, mlistPollutants);
        mPollutants.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.listPollutantRale.setAdapter(mPollutants);
        mBinding.listPollutantRale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (mcountType != ListType.MonthTime.getIndex() && mcountType != ListType.YearTime.getIndex()) { // 实时、日排
                        mpollutantType = ListType.AQI.getIndex(); // AQI
                    } else {//月排或年排
                        mpollutantType = ListType.GoodDay.getIndex();  // 优良天数
                    }
                }else {
                    if ((mcountType == ListType.MonthTime.getIndex() || mcountType == ListType.YearTime.getIndex())&&(mdataType == ListType.Provice31.getIndex() || mdataType == ListType.City169.getIndex() || mdataType == ListType.CountryStationg.getIndex())) { // 月排 年排
                       //全国或者169城市  31省
                        if(position==1)
                            mpollutantType=ListType.Zonghezhishu.getIndex();
                        else{
                            mpollutantType=ListType.Zonghezhishu.getIndex()+position;
                        }
                    }else{
                        mpollutantType = position + ListType.AQI.getIndex();
                    }
                }
//                if (position == 1 && (mcountType == ListType.MonthTime.getIndex() || mcountType == ListType.YearTime.getIndex())) {//月排或年排
//                    if (mdataType == ListType.Provice31.getIndex() || mdataType == ListType.City169.getIndex() || mdataType == ListType.CountryStationg.getIndex()) {
//                        mpollutantType = ListType.Zonghezhishu.getIndex();
//                    }
//                }

                changePollutSelected();
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBinding.listSwipeRefreshLayout.setColorSchemeResources(R.color.aqi_1g, R.color.aqi_2g, R.color.aqi_3g, R.color.aqi_4g);
        mBinding.listSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        mMyAdapter = new MyAdapter<ListBean>(getActivity(), R.layout.list_recyclerview_itme, BR.listbeanItme);
        mBinding.listRecyclerView.setAdapter(mMyAdapter);
        mMyAdapter.setOnItmeViewBinding(this);
        mBinding.listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        getData();
    }

    /**
     * 界面点击输入事件的处理逻辑
     */
    private void changeUI() {
        if ((mcountType != 1 && mcountType != 2) || (mStationTypeID == 5))
            selected_time_tv.setVisibility(View.GONE);//时间选择框隐藏不可见
        else
            selected_time_tv.setVisibility(View.VISIBLE);//时间选择框可见

        if (mcountType == 1) {
            selected_time_tv.setText(mDayDate + "");
            mHourdate = mDayDate;
            isShowDay = true;//日期选择显示到天
        } else if (mcountType == 2) {
            mHourdate = mMonDate;
            try {
                selected_time_tv.setText(mothFormat2.format(mothFormat2.parse(mMonDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            isShowDay = false;//日期选择显示到月
        }

        if (mcountType == 2 || mcountType == 3) {//月排或年排
            if (mdataType == ListType.Provice31.getIndex() || mdataType == ListType.City169.getIndex() || mdataType == ListType.CountryStationg.getIndex()) {
                Pollutants = new String[]{"优良天数", "综合指数", "SO2", "NO2", "O3_8H", "CO", "PM10", "PM2.5"};
            } else {
                Pollutants = new String[]{"优良天数", "SO2", "NO2", "O3_8H", "CO", "PM10", "PM2.5"};
            }
            if (mpollutantType == ListType.AQI.getIndex()) { //切换到月排或年排时   如果上一个选择的是AQI   则变为优良天数
                mpollutantType = ListType.GoodDay.getIndex();
            }
        } else {
            Pollutants = new String[]{"AQI", "SO2", "NO2", "O3_8H", "CO", "PM10", "PM2.5"};
            if (mpollutantType == ListType.GoodDay.getIndex() || mpollutantType == ListType.Zonghezhishu.getIndex()) {//切换到实时、日排时   如果上一个选择的是优良天数或者综合指数   则变为AQI
                mpollutantType = ListType.AQI.getIndex();
            }
        }

        mlistPollutants.clear();
        mlistPollutants.addAll(Arrays.asList(Pollutants));
        mPollutants.notifyDataSetChanged();

        changePollutSelected();
    }


    private void changePollutSelected() {
        if (mpollutantType == ListType.GoodDay.getIndex()) {
            mBinding.listPollutantRale.setSelection(0);
            mBinding.listTitleTab.setText("优良天数");
        } else if (mpollutantType == ListType.Zonghezhishu.getIndex()) {
            mBinding.listPollutantRale.setSelection(1);
            mBinding.listTitleTab.setText("综合指数");
        } else if (mpollutantType == ListType.AQI.getIndex()) {
            mBinding.listPollutantRale.setSelection(0);
            mBinding.listTitleTab.setText("AQI");
        }

        if ((mcountType == 2 || mcountType == 3) && (mdataType == ListType.Provice31.getIndex() || mdataType == ListType.City169.getIndex() || mdataType == ListType.CountryStationg.getIndex())) {//月排或年排
            if (mpollutantType == ListType.SO2.getIndex()) {
                mBinding.listPollutantRale.setSelection(2);
            } else if (mpollutantType == ListType.NO2.getIndex()) {
                mBinding.listPollutantRale.setSelection(3);
            } else if (mpollutantType == ListType.O3.getIndex()) {
                mBinding.listPollutantRale.setSelection(4);
            } else if (mpollutantType == ListType.CO.getIndex()) {
                mBinding.listPollutantRale.setSelection(5);
            } else if (mpollutantType == ListType.PM10.getIndex()) {
                mBinding.listPollutantRale.setSelection(6);
            } else if (mpollutantType == ListType.PM25.getIndex()) {
                mBinding.listPollutantRale.setSelection(7);
            }
        } else {
            if (mpollutantType == ListType.SO2.getIndex()) {
                mBinding.listPollutantRale.setSelection(1);
            } else if (mpollutantType == ListType.NO2.getIndex()) {
                mBinding.listPollutantRale.setSelection(2);
            } else if (mpollutantType == ListType.O3.getIndex()) {
                mBinding.listPollutantRale.setSelection(3);
            } else if (mpollutantType == ListType.CO.getIndex()) {
                mBinding.listPollutantRale.setSelection(4);
            } else if (mpollutantType == ListType.PM10.getIndex()) {
                mBinding.listPollutantRale.setSelection(5);
            } else if (mpollutantType == ListType.PM25.getIndex()) {
                mBinding.listPollutantRale.setSelection(6);
            }
        }

        if (mpollutantType == ListType.SO2.getIndex()) {
            mBinding.listTitleTab.setText("SO2");
        } else if (mpollutantType == ListType.NO2.getIndex()) {
            mBinding.listTitleTab.setText("NO2");
        } else if (mpollutantType == ListType.O3.getIndex()) {
            mBinding.listTitleTab.setText("O3");
        } else if (mpollutantType == ListType.CO.getIndex()) {
            mBinding.listTitleTab.setText("CO");
        } else if (mpollutantType == ListType.PM10.getIndex()) {
            mBinding.listTitleTab.setText("PM10");
        } else if (mpollutantType == ListType.PM25.getIndex()) {
            mBinding.listTitleTab.setText("PM2.5");
        }
    }


    @OnClick({R.id.list_sore_way, R.id.selected_time_tv})
    public void On_click(View view) {
        switch (view.getId()) {
            case R.id.list_sore_way:
                if (misSequence) {
                    misSequence = false;
                    mBinding.listSoreImageUp.setImageResource(R.drawable.down);
                    mBinding.listSoreImageDown.setImageResource(R.drawable.down_w);
                } else {
                    misSequence = true;
                    mBinding.listSoreImageUp.setImageResource(R.drawable.up_w);
                    mBinding.listSoreImageDown.setImageResource(R.drawable.up);
                }
                SequenceData();
                break;
            case R.id.selected_time_tv:

                mTimePikerView = TimerPikerTools.creatTimePickerView(getActivity(), "选择时间", true, true, isShowDay, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        if (isShowDay) {
                            mHourdate = dayFormat.format(date);
                            mDayDate = mHourdate;
                            selected_time_tv.setText(mDayDate + "");
                        } else {
                            mHourdate = mothFormat.format(date);
                            mMonDate = mHourdate;
                            try {
                                selected_time_tv.setText(mothFormat2.format(mothFormat2.parse(mMonDate)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        getData();//获取数据，刷新数据
                    }
                });
                mTimePikerView.show();
                break;
        }
    }

    @Override
    public void getDataSuccess(Object response) {

        mlistBean.clear();
        if (response != null)
            mlistBean.addAll((List<ListBean>) response);

        if (mlistBean.isEmpty()) {
            mBinding.listRecyclerView.setVisibility(View.GONE);
            mBinding.listEmptyText.setVisibility(View.VISIBLE);
        } else {
            mBinding.listRecyclerView.setVisibility(View.VISIBLE);
            mBinding.listEmptyText.setVisibility(View.GONE);
        }

        SequenceData();
    }

    private void getData() {
        if (mpollutantType == ListType.GoodDay.getIndex() || mpollutantType == ListType.Zonghezhishu.getIndex()) {
            mBinding.listTitleTabPollutant.setVisibility(View.GONE);
        } else {
            mBinding.listTitleTabPollutant.setVisibility(View.VISIBLE);
        }

        if (mdataType == ListType.CityStationg.getIndex() || mdataType == ListType.CountyStationg.getIndex() || mdataType == ListType.CountryStationg.getIndex() || mdataType == ListType.City169.getIndex() || mdataType == ListType.Provice31.getIndex()) {
            mBinding.listTitleTabStation.setVisibility(View.GONE);
        } else {
            mBinding.listTitleTabStation.setVisibility(View.VISIBLE);
        }

        if (mdataType == ListType.Provice31.getIndex()) {
            mBinding.listTitleTabCity.setText("省份");
        } else {
            mBinding.listTitleTabCity.setText("城市");
        }

        if (mcountType == 0)//实时
            mListPresenter.getListData(mdataType + "", mcountType + "", mpollutantType + "", mStationTypeID + "", "");
        else
            mListPresenter.getListData(mdataType + "", mcountType + "", mpollutantType + "", mStationTypeID + "", mHourdate + "");
    }

    private void SequenceData() {
        if (mlistBean != null && mlistBean.size() > 0) {
            if (mcountType == ListType.LiveTime.getIndex()) { //实时
//                mBinding.listPuttime.setText("今日" + ColorUtils.stringToData(mlistBean.get(0).getTimePoint(), "yyyy-MM-dd HH:mm:ss", "HH:mm") + "更新");
                mBinding.listPuttime.setText(ColorUtils.stringToData(mlistBean.get(0).getTimePoint(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm") + "更新");
            } else {
                mBinding.listPuttime.setText(ColorUtils.stringToData(mlistBean.get(0).getTimePoint(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd") + "更新");
            }
        }

        int size = 0;

        if(mdataType == ListType.CountryStationg.getIndex())
            size=14;
        else if(mdataType==ListType.City169.getIndex())
            size=1;

        /************全国排名时  江西的11个城市排在前面   排序前处理***********/
        if ((mdataType == ListType.CountryStationg.getIndex()||mdataType==ListType.City169.getIndex()) && mlistBean != null && mlistBean.size() > 0) {
            mtempCity.clear();
            for (int i = 0; i < size; i++) {
                ListBean bean = mlistBean.get(0);
                mtempCity.add(bean);
                mlistBean.remove(0); // 一直删除第0个
            }
            Collections.sort(mtempCity, new ListSort(misSequence));
        }




        /************全国排名时  江西的11个城市排在前面   排序前处理***********/
        Collections.sort(mlistBean, new ListSort(misSequence));
        if (!misSequence) { //倒序的时候处理
            changeData();
        }


        /************全国排名时  江西的11个城市排在前面   排序后处理***********/
        if ((mdataType == ListType.CountryStationg.getIndex()||mdataType==ListType.City169.getIndex())) {
            mlistBean.addAll(0, mtempCity);
        }
        /************全国排名时  江西的11个城市排在前面   排序后处理***********/
        mMyAdapter.setData(mlistBean);
    }

    @Override
    public void getDataFail(String msg) {
    }

    @Override
    public void showRefresh() {
        mBinding.listSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void finishRefresh() {
        mBinding.listSwipeRefreshLayout.setRefreshing(false);
    }

    private void changeData() {  //将无效数据放在底部
        List<ListBean> listtepm = new ArrayList<>();
        for (int i = 0; i < mlistBean.size(); i++) {
            if (mlistBean.get(i).getSortValue().equals("—")) {
                listtepm.add(mlistBean.get(i));
                mlistBean.remove(i);
                i--;
            }
        }
        mlistBean.addAll(listtepm);
    }

    @Override
    public void OnItmeView(View view, Object obejct, int position) {
        ListBean bean = (ListBean) obejct;
        TextView textView = MyViewHolder.getView(view, R.id.list_itme_value);
        //数据修约
        if (mdataType == ListType.Stationg.getIndex() && mpollutantType == ListType.PM25.getIndex()) {
            textView.setText(ColorUtils.PM2_5DataChange(bean.getSortValue()));
        } else if (mdataType == ListType.Stationg.getIndex() && mpollutantType == ListType.PM10.getIndex()) {
            textView.setText(ColorUtils.PM10DataChange(bean.getSortValue()));
        } else {
            textView.setText(bean.getSortValue());
        }
    }
}
