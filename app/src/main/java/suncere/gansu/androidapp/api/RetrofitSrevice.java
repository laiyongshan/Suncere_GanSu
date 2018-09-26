package suncere.gansu.androidapp.api;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import suncere.gansu.androidapp.model.AQDayCountModel;
import suncere.gansu.androidapp.model.HomeDataChart24Model;
import suncere.gansu.androidapp.model.HomeDataModel;
import suncere.gansu.androidapp.model.entity.AQIDayInfoEty;
import suncere.gansu.androidapp.model.entity.AllCityBean;
import suncere.gansu.androidapp.model.entity.CompareBean;
import suncere.gansu.androidapp.model.entity.CompareBean2;
import suncere.gansu.androidapp.model.entity.CompareBean3;
import suncere.gansu.androidapp.model.entity.DeleteFileBean;
import suncere.gansu.androidapp.model.entity.FileBean;
import suncere.gansu.androidapp.model.entity.HomeAllCitiesBean;
import suncere.gansu.androidapp.model.entity.ListBean;
import suncere.gansu.androidapp.model.entity.MapBean;
import suncere.gansu.androidapp.model.entity.UploadFileBean;
import suncere.gansu.androidapp.model.entity.UserBean;
import suncere.gansu.androidapp.model.entity.WarnBean;
import suncere.gansu.androidapp.model.entity.WarnBean2;


/**
 * Created by Hjo on 2017/4/8.
 */
public interface RetrofitSrevice {
//http://111.75.227.199:8081/AppServer

    //  获取所有城市和区县
//  http://10.10.10.18:8022/api/CityData/GetAllCity?level=2  默认返回城市 2为城市 3为区县
    @GET("CityData/GetAllCity")
    Observable<List<AllCityBean>> getAllCity(@Query("level") String level);//@Query("type") String type

    //  首页数据
//  http://10.10.10.18:8022/api/CityData/GetCityHourData?cityCode=360100
//  http://10.10.10.18:8022/api/CityData/GetDistrictHourData?cityCode=360102
//    @GET("CityData/{path}")//GetCityHourData  GetDistrictHourData
//    Observable<HomeDataModel> getHomeData(@Path("path")String path,@Query("cityCode")String cityCode);

    //   首页子页面  72小时数据
    //    http://111.75.227.199:8081/AppServer/api/StationData/GetStationHourDatasByUniquecode?uniqueCode=360104&pollutantCode=104&Type=0&count=24
    //    http://10.10.10.18:8022/api/stationdata/GetStationHourDatasByUniquecode?uniqueCode=360102051&pollutantCode=100
    @GET("stationdata/GetStationHourDatasByUniquecode")
    Observable<HomeDataChart24Model> getHomeStationChartData(@Query("uniqueCode") String uniqueCode, @Query("pollutantCode") String pollutantCode,
                                                             @Query("count") String count, @Query("Type") String Type);

    //    http://111.75.227.199:8081/AppServerTest/api/StationData/GetStationDayDatasByUniquecode?uniqueCode=360100062&pollutantCode=103&Type=2&count=30
    //   30天数据
    @GET("StationData/GetStationDayDatasByUniquecode")
    Observable<HomeDataChart24Model> getDayStationChartData(@Query("uniqueCode") String uniqueCode, @Query("pollutantCode") String pollutantCode,
                                                            @Query("count") String count, @Query("Type") String Type);

    //    http://111.75.227.199:8081/AppServerTest/api/StationData/GetStationMonthDatasByUniquecode?uniqueCode=360102&pollutantCode=104&Type=0&count=12
//    过去12个月数据
    @GET("StationData/GetStationMonthDatasByUniquecode")
    Observable<HomeDataChart24Model> getMonthStationChartData(@Query("uniqueCode") String uniqueCode, @Query("pollutantCode") String pollutantCode,
                                                              @Query("count") String count, @Query("Type") String Type);


    //  地图
    //   http://111.75.227.199:8081/AppServerTest/api/CityData/GetGeoInfoData?dataType=0&pollutantType=99&StationTypeId=1
    @GET("CityData/GetGeoInfoData")
    Observable<List<MapBean>> getMapData(@Query("dataType") String dataType,
                                         @Query("pollutantType") String pollutantType,
                                         @Query("StationTypeId") String StationTypeId);

    //  登录
    //  http://10.10.10.18:8022/api/AppUser/Login
    @FormUrlEncoded
    @POST("AppUser/LoginD")
    Observable<UserBean> getLoginReust(@Field("LoginName") String LoginName, @Field("PassWord") String PassWord);

    //  城市排名
    //  http://10.10.10.18:8022/api/CityData/GetPollutantSort?dataType=0&countType=0&countIndex=countIndex&pollutantType=AQI
//    @GET("CityData/GetPollutantSort")
//    Observable<List<ListBean>> getListData(@Query("dataType")String dataType , @Query("countType")String countType,
//                                           @Query("countIndex")String countIndex , @Query("pollutantType")String pollutantType);

    //    http://111.75.227.199:8081/AppServerTest/api/StationData/GetStationExecptionData?type=1&CityCode=360100
    @GET("StationData/GetStationExecptionData")
    Observable<List<WarnBean>> getWarnData(@Query("type") String type, @Query("CityCode") String CityCode);

    //預警
    @GET("StationData/GetStationHourExecptionData")
    Observable<List<WarnBean2>> getStationExecptionData(@Query("type") String type, @Query("pollutantCode") String pollutantCode);

    /*修改功能后使用 首页、排名 使用的接口*/
    //    http://111.75.227.199:8081/AppServerTest/api/CityData/GetHourData?Type=0&cityCode=360000&StationTypeID=1
//    http://111.75.227.199:8081/AppServerTest/api/CityData/GetHourData?cityCode=360100&StationTypeID=3
    @GET("CityData/GetHourData")
    //首页
    Observable<HomeDataModel> gethomeData2(@Query("cityCode") String cityCode, @Query("StationTypeID") String StationTypeID);


    //    http://111.75.227.199:8081/AppServerTest/api/CityData/GetDataSort?dataType=0&countType=0&pollutantType=99&StationTypeID=2
    //    http://localhost:2457/api/CityData/GetDataSort?dataType=3&countType=0&pollutantType=99&StationTypeID=1
    @GET("CityData/GetDataSort")
    //排名
    Observable<List<ListBean>> getListData2(@Query("dataType") String dataType, @Query("countType") String countType,
                                            @Query("pollutantType") String pollutantType, @Query("StationTypeID") String StationTypeID, @Query("Hourdate") String Hourdate);

    //  http://111.75.227.199:8081/AppServerTest/api/Update/GetAndroidAPKUpdate?version=1.0.0
    // APP更新
    @GET("Update/GetAndroidAPKUpdate")
    Observable<String> updataAPP(@Query("version") String version);


    //    http://111.75.227.199:8081/AppServerTest/api/CityData/GetAllCityHourData
    @GET("CityData/GetAllCityHourData")
    Observable<List<HomeAllCitiesBean>> homeAllCitiesData();

    //http://111.75.227.199:8081/AppServerTest/api/CityData/GetSumContrast?Type=0&BeginTime=2017-5-1&EndTime=2017-7-1
    //城市对比
    @GET("CityData/GetSumContrast")
    Observable<List<CompareBean2>> getCompareBean2Date(@Query("Type") String Type, @Query("BeginTime") String BeginTime, @Query("EndTime") String EndTime);


    //对比页面
    @GET("CityData/GetProvinceAirContrast")
    Observable<List<CompareBean>> getGetCompareData(@Query("cityName") String cityName, @Query("baseYear") String baseYear, @Query("compareYear") String compareYear, @Query("countType") String countType, @Query("countIndex") String countIndex);

    @GET("CityData/GetProvinceAirContrast")
    Observable<List<CompareBean>> getGetCompareData2(@Query("cityName") String cityName, @Query("Stime") String Stime, @Query("ETime") String ETime, @Query("Pre_Stime") String Pre_Stime, @Query("Pre_Etime") String Pre_Etime, @Query("IsRemoveSandDust") String IsRemoveSandDust);


    @GET("CityData/GetYearTargetCompare")
    Observable<List<CompareBean3>> getYearTargetCompare(@Query("cityName")String cityName, @Query("Stime") String Stime, @Query("ETime") String ETime);


    //日历图
    @GET("CityData/GetCityDayLevelInfo")
    Observable<List<AQIDayInfoEty>> getCityDayLevelInfo(@Query("cityName") String cityName, @Query("pollutantName") String pollutantName, @Query("year") String year, @Query("month") String month);

    //饼图
    @GET("CityData/GetCityDayCount")
    Observable<AQDayCountModel> getCityDayCount(@Query("cityName") String cityName, @Query("countType") String countType, @Query("year") String year, @Query("countIndex") String countIndex);

    //获取已上传的文件列表
    @GET("FileUp/GetFileInfo")
    Observable<List<FileBean>> getFileInfo(@Query("FileName") String FileName, @Query("Stime") String Stime, @Query("Etime") String Etime);

    //上传文件
    @Multipart
    @POST("FileUp/UpFile")
    Observable<UploadFileBean> uploadFile(@PartMap Map<String, RequestBody> params);

    @Streaming //大文件时要加不然会OOM  下载文件
    @GET
    Observable<ResponseBody> downFile(@Url String fileUrl);

    //批量删除文件
    @GET("FileUp/DeleteByID")
    Observable<DeleteFileBean> deleteById(@Query("ID") String IDs);
}
