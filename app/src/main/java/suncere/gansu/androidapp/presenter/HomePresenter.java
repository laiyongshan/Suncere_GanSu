package suncere.gansu.androidapp.presenter;

import java.util.List;

import rx.Observable;
import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.entity.HomeAllCitiesBean;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IHomeView;
import suncere.gansu.androidapp.utils.CatchManager;
import suncere.gansu.androidapp.utils.NetWorkUtil;

import static suncere.gansu.androidapp.utils.CatchManager.getCatchData;


/**
 * Created by Hjo on 2017/5/11.
 */
public class HomePresenter extends BasePresenter<IHomeView> {

    private static final String mKey="AllCitiesData";

    private   String mChartsKey;
   public HomePresenter(IHomeView Iview){
        attrchIView(Iview);
    }

    public void getHomeAllCitiesData(){
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetAllCitiesData();
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    /**
     * 获取数据
     */
    private void getNetAllCitiesData(){
        addSubscription(mRetrofitService.homeAllCitiesData(), new ApiNetCallBack<List<HomeAllCitiesBean>>() {
            @Override
            public void onSuccess(List<HomeAllCitiesBean> response) {
                mIView.getDataSuccess(response);
                CatchManager.putData2Cache(mKey,response);
            }

            @Override
            public void onError(String msg) {
                mIView.getDataFail(msg);
            }
            @Override
            public void onFinish() {
                mIView.finishRefresh();
            }
        });
    }

    public void getChartData(String uniqueCode,String pollutantCode,int DataType){
        mChartsKey=uniqueCode+pollutantCode+DataType;
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetChartData(uniqueCode,pollutantCode,"24","0",DataType);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getCharts( getCatchData(mChartsKey));
            mIView.finishRefresh();
        }
    }

    private void getNetChartData(String uniqueCode,String pollutantCode,String count,String Type,int DataType){
        //合并两个请求
        Observable chart;
       switch (DataType){
           case 0:
               chart=mRetrofitService.getHomeStationChartData(uniqueCode,pollutantCode,"24",Type); //24小时
               break;
           case 1:
               chart=mRetrofitService.getDayStationChartData(uniqueCode,pollutantCode,"30",Type); //30天
               break;
           default:
               chart=mRetrofitService.getMonthStationChartData(uniqueCode,pollutantCode,"12",Type); //12月
               break;
       }
//        合并请求
//        Observable chart24=mRetrofitService.getHomeStationChartData(uniqueCode,pollutantCode,count,Type); //24小时
//        Observable chart30=mRetrofitService.getDayStationChartData(uniqueCode,pollutantCode,count,Type); //30天
//        Observable chart12=mRetrofitService.getMonthStationChartData(uniqueCode,pollutantCode,count,Type); //12月
//        Observable observable=   Observable.merge(chart24,chart30).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        addSubscription(chart, new ApiNetCallBack() {
            @Override
            public void onSuccess(Object response) {
                mIView.getCharts(response);
                CatchManager.putData2Cache(mChartsKey,response);
            }
            @Override
            public void onError(String msg) {
                mIView.getDataFail(msg);
            }
            @Override
            public void onFinish() {
                mIView.finishRefresh();
            }
        });
    }
}
