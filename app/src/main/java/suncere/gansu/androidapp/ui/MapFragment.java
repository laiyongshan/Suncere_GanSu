package suncere.gansu.androidapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.MyViewHolder;
import suncere.gansu.androidapp.customview.PollutantsView;
import suncere.gansu.androidapp.customview.SegmentControl;
import suncere.gansu.androidapp.model.entity.MapBean;
import suncere.gansu.androidapp.presenter.MapPresenter;
import suncere.gansu.androidapp.ui.iview.IMapView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.ToolUtils;


/**
 * Created by Hjo on 2017/5/12.
 */
public class MapFragment extends MvpFragment<MapPresenter> implements IMapView,AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,AMap.OnInfoWindowClickListener {

    MapPresenter mMapPresenter;
    AMap maMap;
    MapView mMapView = null;
    UiSettings mUiSettings;
    LatLng mLatLng;
    BitmapDescriptor mbitmapDescriptor;
    Marker mMarker;

    //网络请求参数
    String mdataType="2";  //0为站点、1为区县、2为城市
    String mpollutantType="99";
    String mpollutantName="AQI";

    @BindView(R.id.map_title_refresh_image)
    ImageView refresh_image;
    List<MapBean> mMapBean;

    @BindView(R.id.map_PollutantsView)
    PollutantsView map_PollutantsView;

    @BindView(R.id.map_time)
    TextView map_time;

    @BindView(R.id.map_city_select)
    SegmentControl map_segmentControl;

    @BindView(R.id.map_segmentTabLayout)
    SegmentTabLayout map_segmentTabLayout;

    private String[] mTitles = {"城市", "国控","区县","省控"};

//    @BindView(R.id.map_cityorcounty_select)
//    SegmentControl map_cityorcounty_segmentControl;

    View mMarkView,mMarkViewWin;

    String mStationTypeId="1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.map_fragment,container,false);
        ButterKnife.bind(this,view);
        initView(savedInstanceState,view);
        return view;
    }
    @Override
    protected MapPresenter createPresenter() {
        mMapPresenter=new MapPresenter(this);
        return mMapPresenter;
    }

    @Override
    public void onStart() {
        getData();
        mMapPresenter.getDistrictData();
        super.onStart();
    }

    public void  initView(Bundle savedInstanceState, View view){
        mMapBean=new ArrayList<>();
        mMapView=(MapView)view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (maMap==null){
            maMap=mMapView.getMap();
        }
        mLatLng=new LatLng(36.0725,103.841);
        maMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,5));
        mUiSettings = maMap.getUiSettings();//实例化UiSettings类
        mUiSettings.setZoomControlsEnabled(false);//不显示放大缩小控件

        maMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        maMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        maMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

        map_PollutantsView.setmSelceTextListener(new PollutantsView.SelceTextListener() {
            @Override
            public void onSelect(String pollutantName, String pollutantCode) {
                mpollutantName=pollutantName;
                mpollutantType= pollutantCode;
                getData();
            }
        });

        map_segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {

                switch (index){
                    case 0: //城市 mdataType="2"
                        mdataType="2";
                        break;
                    case 1: //国控 mStationTypeId="1" ，mdataType="0";
                        mStationTypeId="1";
                        mdataType="0";
                        break;
                    case 2: //区县 mdataType="1"
                        mdataType="1";
                        break;
                    case 3: //省控 mStationTypeId="2"， mdataType="0";
                        mStationTypeId="2";
                        mdataType="0";
                        break;
                }
                getData();
            }
        });

        map_segmentTabLayout.setTabData(mTitles);
        map_segmentTabLayout.setCurrentTab(0);
        map_segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position){
                    case 0: //城市 mdataType="2"
                        mdataType="2";
                        break;
                    case 1: //国控 mStationTypeId="1" ，mdataType="0";
                        mStationTypeId="1";
                        mdataType="0";
                        break;
                    case 2: //区县 mdataType="1"
                        mdataType="1";
                        break;
                    case 3: //省控 mStationTypeId="2"， mdataType="0";
                        mStationTypeId="2";
                        mdataType="0";
                        break;
                }
                getData();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    private void getData(){
        mMapPresenter.getMapData(mdataType,mpollutantType,mStationTypeId);
    }

    @Override
    public void getDataSuccess(Object response) {
        mMapBean.clear();
        if (response!=null)
        mMapBean.addAll(  (List<MapBean>)response  );
        bindMapData( mMapBean );
    }

    @Override
    public void getDistrictResult(Object response) {
        for(PolylineOptions polylineOption :(List<PolylineOptions>)response ){
            polylineOption.width(6).color(Color.BLUE);
            maMap.addPolyline(polylineOption);
        }
    }

    private void bindMapData(List<MapBean> listData){
        for (Marker marker : maMap.getMapScreenMarkers()){
            marker.remove();
        }
        mMapView.invalidate();
//        maMap.clear();
        if (listData!=null && listData.size()>0){
            String str=listData.get(0).getTimePoint();
            map_time.setText( ColorUtils.stringToData( str,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH")+"时更新");
        }
        for(int i=0;i<listData.size();i++){
            MapBean bean =listData.get(i);
                if (bean.getLatitude().equals("—")  ||  bean.getLongitude().equals("—") || bean.getLatitude().length()==0 ||  bean.getLongitude().length()==0){
                    continue;
                }else{
                    View view = creatMarkView(bean);
                    mbitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                    LatLng latLng= new LatLng(Double.valueOf(  bean.getLatitude() ), Double.valueOf( bean.getLongitude() )  );
                    mMarker = maMap.addMarker(new MarkerOptions().position( latLng).icon(mbitmapDescriptor).zIndex(i).title("mapfragment"));
                }
            }
    }


    private View creatMarkView(MapBean bean){
        View view=null;
        view = LayoutInflater.from(getActivity()).inflate(R.layout.map_marker_itme,null);
        TextView value_tv=(TextView)view.findViewById(R.id.map_marker_Vaule);
       // 站点数据修约
            if(mdataType.equals("0") && mpollutantName.equals("PM2.5"))
            value_tv.setText(ColorUtils.PM2_5DataChange(bean.getValue()));
            else if (mdataType.equals("0") && mpollutantName.equals("PM10")){
                value_tv.setText(ColorUtils.PM10DataChange(bean.getValue()));
            }else{
                value_tv.setText(bean.getValue());
            }

        value_tv.setBackgroundResource(ColorUtils.getBgFromLevel(bean.getLevel()));
        return view;
    }

    private View creatMarkViewWin(MapBean mapBean){

        if (mMarkViewWin==null){
            mMarkViewWin=LayoutInflater.from(getActivity()).inflate(R.layout.map_mark_itme_win,null);
        }
        ((TextView) MyViewHolder.getView(mMarkViewWin,R.id.map_stationName) ).setText(mapBean.getGeoName());
        if (mpollutantName.equals("AQI")){
            ((TextView)MyViewHolder.getView(mMarkViewWin,R.id.map_AQItext)) .setText(mpollutantName+"指数：");
        }else{
            ((TextView)MyViewHolder.getView(mMarkViewWin,R.id.map_AQItext)).setText(mpollutantName+"浓度：");
        }
        int color=ColorUtils.getColorWithLevel(mapBean.getLevel());
        TextView AQIValue=MyViewHolder.getView(mMarkViewWin,R.id.map_AQIValue);


      // 站点数据修约
            if(mdataType.equals("0") && mpollutantName.equals("PM2.5"))
                AQIValue.setText(ColorUtils.PM2_5DataChange(mapBean.getValue()));
            else if (mdataType.equals("0") && mpollutantName.equals("PM10")){
                AQIValue.setText(ColorUtils.PM10DataChange(mapBean.getValue()));
            }else{
                AQIValue.setText(mapBean.getValue());
            }


        AQIValue.setTextColor(color);
        TextView  Quality=MyViewHolder.getView(mMarkViewWin,R.id.map_Quality);
        Quality.setText(ColorUtils.getAbbreviationQualitysWithLevel(mapBean.getLevel()));
        Quality.setTextColor(color);

        return mMarkViewWin;
    }

    @OnClick(R.id.map_title_refresh_rela)
    public void OnClick_listener(View view){
        getData();
    }
    @Override
    public void getDataFail(String msg) {}

    @Override
    public void showRefresh() {
        refresh_image.startAnimation(ToolUtils.getRefreshAnimation());
    }

    @Override
    public void finishRefresh() {
        refresh_image.clearAnimation();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        int index= (int) marker.getZIndex();
        return creatMarkViewWin(mMapBean.get(index));
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker!=null) {
            marker.hideInfoWindow();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

}
