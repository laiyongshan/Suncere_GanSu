package suncere.gansu.androidapp.presenter;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;

import java.util.ArrayList;
import java.util.List;

import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.entity.MapBean;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IMapView;
import suncere.gansu.androidapp.utils.CatchManager;
import suncere.gansu.androidapp.utils.NetWorkUtil;

/**
 * Created by Hjo on 2017/5/12.
 */

public class MapPresenter extends BasePresenter<IMapView> {

    String mCatchKe="MapDistrict";
    public MapPresenter(IMapView iview){
        attrchIView(iview);
    }


    private String mKey;
    public void getMapData(String dataType,String pollutantType,String StationTypeId){
        mKey=dataType+pollutantType+StationTypeId;
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetMapData(dataType,pollutantType,StationTypeId);
        }else{
            mIView.getDataFail("无网络连接！");
            mIView.getDataSuccess( CatchManager.getCatchData(mKey));
            mIView.finishRefresh();
        }
    }

    private void getNetMapData(String dataType,String pollutantType,String StationTypeId){
        addSubscription(mRetrofitService.getMapData(dataType, pollutantType,StationTypeId), new ApiNetCallBack<List<MapBean>>() {
            @Override
            public void onSuccess(List<MapBean> response) {
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

    public void getDistrictData(){

        final Object obiect=CatchManager.getCatchData(mCatchKe);
        Log.e("huangjinou","绘制边界");
        if ( obiect!=null){
            new Thread(){
                public void run() {
                    String[] polyStr= (String[]) obiect;
                    analysisPolylineOptions(polyStr);
                }
            }.start();

        }else{
            DistrictSearch mSearch = new DistrictSearch(MyApplication.getMyApplicationContext());
            DistrictSearchQuery mQuery = new DistrictSearchQuery();
            mQuery.setKeywords("甘肃省");//传入关键字
            mQuery.setShowBoundary(true);//是否返回边界值
            mSearch.setQuery(mQuery);
            mSearch.setOnDistrictSearchListener(new DistrictSearch.OnDistrictSearchListener() {
                @Override
                public void onDistrictSearched(DistrictResult districtResult) {
                    mapdata(districtResult);
                }
            });
            mSearch.searchDistrictAsyn();//开始搜索
        }
    }

    private void mapdata(final DistrictResult districtResult){

        if (null==districtResult   || null==districtResult.getDistrict()) {
            return;
        }
        //通过ErrorCode判断是否成功
        if(districtResult.getAMapException() != null && districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
            final DistrictItem item = districtResult.getDistrict().get(0);
            if (item == null) {
                return;
            }
            new Thread() {
                public void run() {
                    String[] polyStr = item.districtBoundary();
                    analysisPolylineOptions(polyStr);
                    CatchManager.putData2Cache(mCatchKe,polyStr);
                }
            }.start();
        }
    }


    private void analysisPolylineOptions(String[] polyStr){
        List<PolylineOptions> PolylineOptions=new ArrayList<>();
        if (polyStr == null || polyStr.length == 0) {
            return;
        }
        for (String str : polyStr) {
            String[] lat = str.split(";");
            PolylineOptions polylineOption = new PolylineOptions();
            boolean isFirst = true;
            LatLng firstLatLng = null;
            for (String latstr : lat) {
                String[] lats = latstr.split(",");
                if (isFirst) {
                    isFirst = false;
                    firstLatLng = new LatLng(Double
                            .parseDouble(lats[1]), Double
                            .parseDouble(lats[0]));
                }
                polylineOption.add(new LatLng(Double
                        .parseDouble(lats[1]), Double
                        .parseDouble(lats[0])));


            }
            if (firstLatLng != null) {
                polylineOption.add(firstLatLng);
            }
            PolylineOptions.add(polylineOption);
        }
//        new XMLSaveTool(PolylineOptions);
        mIView.getDistrictResult(PolylineOptions);
    }

//    public void getDistrictData( final String CatchKey){
//        Object obiect=CatchManager.getCatchData(CatchKey);
//        if ( obiect!=null){
//            mapdata((DistrictResult)obiect);
//
//        }else{
//            DistrictSearch  mSearch = new DistrictSearch(MyApplication.getMyApplicationContext());
//            DistrictSearchQuery mQuery = new DistrictSearchQuery();
//            mQuery.setKeywords("江西省");//传入关键字
//            mQuery.setShowBoundary(true);//是否返回边界值
//            mSearch.setQuery(mQuery);
//            mSearch.searchDistrictAnsy();//开始搜索
//            mSearch.setOnDistrictSearchListener(new DistrictSearch.OnDistrictSearchListener() {
//                @Override
//                public void onDistrictSearched(DistrictResult districtResult) {
//                    mapdata(districtResult);
//                    CatchManager.putData2Cache(CatchKey,districtResult);
//                }
//            });
//        }
//    }
//
//    private void mapdata(final DistrictResult districtResult){
//        final List<PolylineOptions> PolylineOptions=new ArrayList<>();
//
//        if (districtResult == null || districtResult.getDistrict()==null) {
//            return;
//        }
//        //通过ErrorCode判断是否成功
//        if(districtResult.getAMapException() != null && districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
//            final DistrictItem item = districtResult.getDistrict().get(0);
//            if (item == null) {
//                return;
//            }
//            new Thread() {
//                public void run() {
//                    String[] polyStr = item.districtBoundary();
//                    if (polyStr == null || polyStr.length == 0) {
//                        return;
//                    }
//                    for (String str : polyStr) {
//                        String[] lat = str.split(";");
//                        PolylineOptions polylineOption = new PolylineOptions();
//                        boolean isFirst = true;
//                        LatLng firstLatLng = null;
//                        for (String latstr : lat) {
//                            String[] lats = latstr.split(",");
//                            if (isFirst) {
//                                isFirst = false;
//                                firstLatLng = new LatLng(Double
//                                        .parseDouble(lats[1]), Double
//                                        .parseDouble(lats[0]));
//                            }
//                            polylineOption.add(new LatLng(Double
//                                    .parseDouble(lats[1]), Double
//                                    .parseDouble(lats[0])));
//                        }
//                        if (firstLatLng != null) {
//                            polylineOption.add(firstLatLng);
//                        }
//                        PolylineOptions.add(polylineOption);
//                    }
//                    mIView.getDistrictResult(PolylineOptions);
//                }
//            }.start();
//
//        }
//    }

}


