package suncere.gansu.androidapp.presenter;

import java.util.List;

import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.entity.CompareBean2;
import suncere.gansu.androidapp.model.entity.CompareBean3;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.NetWorkUtil;

import static suncere.gansu.androidapp.utils.CatchManager.getCatchData;

/**
 * @author lys
 * @time 2018/8/27 18:30
 * @desc:
 */

public class ComparePresenter2 extends BasePresenter<IView> {

    public ComparePresenter2(IView iView){
        attrchIView(iView);
    }

    String mKey="compare";
    public void getCompareData(String cityName,String baseYear,String compareYear,String countType,String countIndex){
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetCompareData(cityName,baseYear,compareYear,countType,countIndex);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }


    String mKey2="compare2";
    public void getCompareData2(String cityName,String Stime,String ETime,String Pre_Stime,String Pre_Etime,String IsRemoveSandDust){
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetCompareData2(cityName,Stime,ETime,Pre_Stime,Pre_Etime,IsRemoveSandDust);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey2));
            mIView.finishRefresh();
        }
    }


    private void  getNetCompareData(String cityName,String baseYear,String compareYear,String countType,String countIndex){
        addSubscription(mRetrofitService.getGetCompareData(cityName,baseYear,compareYear,countType,countIndex), new ApiNetCallBack<List<CompareBean2>>() {
            @Override
            public void onSuccess(List<CompareBean2> response) {
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


    private void  getNetCompareData2(String cityName,String Stime,String ETime,String Pre_Stime,String Pre_Etime,String IsRemoveSandDust){
        addSubscription(mRetrofitService.getGetCompareData2(cityName,Stime,ETime,Pre_Stime,Pre_Etime,IsRemoveSandDust), new ApiNetCallBack<List<CompareBean2>>() {
            @Override
            public void onSuccess(List<CompareBean2> response) {
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


    String mKey3="compare3";
    public void getCompareData3(String cityName,String Stime,String ETime){
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetYearTargetCompare(cityName,Stime,ETime);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey3));
            mIView.finishRefresh();
        }
    }

    private void getNetYearTargetCompare(String cityName,String Stime,String ETime){
        addSubscription(mRetrofitService.getYearTargetCompare(cityName,Stime,ETime), new ApiNetCallBack<List<CompareBean3>>() {
            @Override
            public void onSuccess(List<CompareBean3> response) {
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
