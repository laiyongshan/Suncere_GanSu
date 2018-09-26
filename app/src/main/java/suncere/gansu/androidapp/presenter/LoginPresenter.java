package suncere.gansu.androidapp.presenter;

import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.entity.UserBean;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.NetWorkUtil;

/**
 * Created by Hjo on 2017/5/27.
 */

public class LoginPresenter extends BasePresenter<IView> {

    public LoginPresenter(IView iView){
        attrchIView(iView);
    }

    public void login(String UserName,String Password) {
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())) {
            getNetlogin(UserName,Password);
        } else {
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess(null);
            mIView.finishRefresh();
        }
    }

    private void getNetlogin(String UserName,String Password){
        addSubscription(mRetrofitService.getLoginReust(UserName,Password), new ApiNetCallBack<UserBean>() {
            @Override
            public void onSuccess(UserBean response) {
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
