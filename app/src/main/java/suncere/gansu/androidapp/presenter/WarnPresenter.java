package suncere.gansu.androidapp.presenter;

import java.util.List;

import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.entity.WarnBean;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.CatchManager;
import suncere.gansu.androidapp.utils.NetWorkUtil;

import static suncere.gansu.androidapp.utils.CatchManager.getCatchData;

/**
 * Created by Hjo on 2017/7/4.
 */

public class WarnPresenter  extends  BasePresenter<IView> {

    public  WarnPresenter(IView iView){
        attrchIView(iView);
    }
    String mKey;

    public void getListData(String type,String CityCode ){
        mKey="Warn"+type+CityCode;
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetListData(type,CityCode);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    private void getNetListData(String type ,String CityCode){
        addSubscription(mRetrofitService.getWarnData(type,CityCode), new ApiNetCallBack<List<WarnBean>>() {
            @Override
            public void onSuccess(List<WarnBean> response) {
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


    public void getStationHourExecptionData(String type,String pollutantCode){
        mKey="Warn"+type+pollutantCode;
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetStationExecptionData(type,pollutantCode);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    public void getNetStationExecptionData(String type,String pollutantCode){
        addSubscription(mRetrofitService.getStationExecptionData(type,pollutantCode), new ApiNetCallBack<List<WarnBean>>() {
            @Override
            public void onSuccess(List<WarnBean> response) {
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
}
