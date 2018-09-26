package suncere.gansu.androidapp.presenter;

import java.util.List;

import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.HomeDataModel;
import suncere.gansu.androidapp.model.entity.AllCityBean;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IHomeStationView;
import suncere.gansu.androidapp.utils.CatchManager;
import suncere.gansu.androidapp.utils.NetWorkUtil;

import static suncere.gansu.androidapp.utils.CatchManager.getCatchData;


/**
 * Created by Hjo on 2017/5/11.
 */
public class HomeStationPresenter extends BasePresenter<IHomeStationView> {

   public HomeStationPresenter(IHomeStationView Iview){
        attrchIView(Iview);
    }

   String mKey;
    public void getHomeData(String cityCode,String StationTypeID){
        mKey=StationTypeID+cityCode;
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetHomeData(cityCode,StationTypeID);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    /**
     * 获取数据
     * @param cityCode
     */
    private void getNetHomeData( String cityCode,String StationTypeID){
        addSubscription(mRetrofitService.gethomeData2(cityCode,StationTypeID), new ApiNetCallBack<HomeDataModel>() {
            @Override
            public void onSuccess(HomeDataModel response) {
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

    /**
     * 获取所有城市或区县
     * @param level
     */
    private void getNetCities(final String level){
        addSubscription(mRetrofitService.getAllCity(level), new ApiNetCallBack<List<AllCityBean>>() {
            @Override
            public void onSuccess(List<AllCityBean >response) {
                CatchManager.putData2Cache(level,response);
                mIView.getAllCities(response);
            }
            @Override
            public void onError(String msg) {}
            @Override
            public void onFinish() {}
        });
    }

    /**
     * 城市或区县的数据  只需要获取一次  其余的读取缓存即可
     * @param level
     */
    public void getCities(String level){
       Object object= CatchManager.getCatchData(level);
        if (object!=null){
            mIView.getAllCities(object);
        }else{
            getNetCities(level);
        }
    }







}
