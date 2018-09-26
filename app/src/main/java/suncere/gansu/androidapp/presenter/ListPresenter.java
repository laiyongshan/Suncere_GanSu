package suncere.gansu.androidapp.presenter;

import java.util.List;

import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.entity.ListBean;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.CatchManager;
import suncere.gansu.androidapp.utils.NetWorkUtil;

import static suncere.gansu.androidapp.utils.CatchManager.getCatchData;

/**
 * Created by Hjo on 2017/5/12.
 */

public class ListPresenter extends BasePresenter<IView> {

    public ListPresenter(IView iview){
        attrchIView(iview);
    }

    String mKey;
    public void getListData(String dataType , String countType, String pollutantType , String StationTypeID,String Hourdate){
        mKey=dataType+countType+pollutantType+StationTypeID+Hourdate;
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetListData(dataType,countType,pollutantType,StationTypeID,Hourdate);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    private void getNetListData(String dataType , String countType , String pollutantType, String StationTypeID,String Hourdate ){
        addSubscription(mRetrofitService.getListData2(dataType, countType, pollutantType,StationTypeID,Hourdate), new ApiNetCallBack<List<ListBean>>() {
            @Override
            public void onSuccess(List<ListBean> response) {
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
