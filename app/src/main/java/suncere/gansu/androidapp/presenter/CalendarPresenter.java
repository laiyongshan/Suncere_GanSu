package suncere.gansu.androidapp.presenter;

import java.util.List;

import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.AQDayCountModel;
import suncere.gansu.androidapp.model.entity.AQIDayInfoEty;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.NetWorkUtil;

import static suncere.gansu.androidapp.utils.CatchManager.getCatchData;

/**
 * @author lys
 * @time 2018/8/29 18:26
 * @desc:
 */

public class CalendarPresenter extends BasePresenter<IView> {
    public CalendarPresenter(IView iView){
        attrchIView(iView);
    }

    String mKey="calendar";
    public void getCityDayLevelInfoData(String cityName,String pollutantName,String year, String month){
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetCityDayLevelInfoData(cityName,pollutantName,year,month);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    public void getCityDayCount(String cityName,String countType,String year,String countIndex){
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetCityDayCount(cityName,countType,year,countIndex);
        }else{
            mIView.getDataFail("无网络连接！");
//            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    private void getNetCityDayCount(String cityName,String countType,String year,String countIndex){
        addSubscription(mRetrofitService.getCityDayCount(cityName,countType,year,countIndex), new ApiNetCallBack<AQDayCountModel>() {
            @Override
            public void onSuccess(AQDayCountModel response) {
                mIView.getDataSuccess(response);
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



    private void  getNetCityDayLevelInfoData(String cityName,String pollutantName,String year, String month){
        addSubscription(mRetrofitService.getCityDayLevelInfo(cityName,pollutantName,year,month), new ApiNetCallBack<List<AQIDayInfoEty>>() {
            @Override
            public void onSuccess(List<AQIDayInfoEty> response) {
                mIView.getDataSuccess(response);
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
