package suncere.gansu.androidapp.model;

import java.util.List;

import suncere.gansu.androidapp.model.entity.BaseBean;
import suncere.gansu.androidapp.model.entity.HomeStationBean;

/**
 * Created by Hjo on 2017/5/12.
 */

public class HomeDataModel extends BaseBean{

    /**
     * CityCode : 360100
     * Unheathful : 空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响
     * DistrictList
     * TimePoint : 2017-05-12T11:00:00
     * CO : 0.0
     * NO2 : 47
     * O3 : 47
     * SO2 : 49
     * PM10 : 53
     * PM2_5 : 47
     * AQI : 65
     * PrimaryPollutant : 细颗粒物(PM2.5)
     * Quality : 良
     */



    private String CityCode;
    private String Unheathful;
    private String TimePoint;
    private String CO;
    private String NO2;
    private String O3;
    private String SO2;
    private String PM10;
    private String PM2_5;
    private String AQI;
    private String PrimaryPollutant;
    private String Quality;
    private List<HomeStationBean> DistrictList;
    private  List <HomeStationBean>StationList;
    /**
     * CoLevel : 1
     * No2Level : 1
     * So2Level : 1
     * O3Level : 1
     * Pm10Level : 1
     * Pm2_5Level : 1
     * StationList : [{"TimePoint":"2017-07-08T14:00:00","StationCode":"1146A","PositionName":"省外办","Longitude":"","Latitude":"","AreaCode":"360102","UniqueCode":"360100053","CityName":"东湖区","AQI":"23","Measure":"各类人群可正常活动","Unheathful":"空气质量令人满意，基本无空气污染","PrimaryPollutant":"\u2014","Quality":"优","SO2":"9","NO2":"10","O3_8h":"49","O3":"73","CO":"1.131","PM10":"14","PM2_5":"5","SO2_Mark":"\u2014","NO2_Mark":"\u2014","O3_Mark":"\u2014","O3_8h_Mark":"\u2014","CO_Mark":"\u2014","PM10_Mark":"\u2014","PM2_5_Mark":"\u2014","SumIndex":null},{"TimePoint":"2017-07-08T14:00:00","StationCode":"1147A","PositionName":"省林业公司","Longitude":"","Latitude":"","AreaCode":"360102","UniqueCode":"360100054","CityName":"东湖区","AQI":"24","Measure":"各类人群可正常活动","Unheathful":"空气质量令人满意，基本无空气污染","PrimaryPollutant":"\u2014","Quality":"优","SO2":"12","NO2":"10","O3_8h":"43","O3":"72","CO":"0.772","PM10":"24","PM2_5":"4","SO2_Mark":"\u2014","NO2_Mark":"\u2014","O3_Mark":"\u2014","O3_8h_Mark":"\u2014","CO_Mark":"\u2014","PM10_Mark":"\u2014","PM2_5_Mark":"\u2014","SumIndex":null}]
     */

    private String CityName;
    private String CoLevel;
    private String No2Level;
    private String So2Level;
    private String O3Level;
    private String Pm10Level;
    private String Pm2_5Level;

    @Override
    public String toString() {
        return "HomeDataModel{" +
                "CityCode='" + CityCode + '\'' +
                ", Unheathful='" + Unheathful + '\'' +
                ", TimePoint='" + TimePoint + '\'' +
                ", CO='" + CO + '\'' +
                ", NO2='" + NO2 + '\'' +
                ", O3='" + O3 + '\'' +
                ", SO2='" + SO2 + '\'' +
                ", PM10='" + PM10 + '\'' +
                ", PM2_5='" + PM2_5 + '\'' +
                ", AQI='" + AQI + '\'' +
                ", PrimaryPollutant='" + PrimaryPollutant + '\'' +
                ", Quality='" + Quality + '\'' +
                ", DistrictList=" + DistrictList +
                ", StationList=" + StationList +
                ", CityName='" + CityName + '\'' +
                ", CoLevel='" + CoLevel + '\'' +
                ", No2Level='" + No2Level + '\'' +
                ", So2Level='" + So2Level + '\'' +
                ", O3Level='" + O3Level + '\'' +
                ", Pm10Level='" + Pm10Level + '\'' +
                ", Pm2_5Level='" + Pm2_5Level + '\'' +
                '}';
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCoLevel() {
        return CoLevel;
    }

    public void setCoLevel(String coLevel) {
        CoLevel = coLevel;
    }

    public String getNo2Level() {
        return No2Level;
    }

    public void setNo2Level(String no2Level) {
        No2Level = no2Level;
    }

    public String getSo2Level() {
        return So2Level;
    }

    public void setSo2Level(String so2Level) {
        So2Level = so2Level;
    }

    public String getO3Level() {
        return O3Level;
    }

    public void setO3Level(String o3Level) {
        O3Level = o3Level;
    }

    public String getPm10Level() {
        return Pm10Level;
    }

    public void setPm10Level(String pm10Level) {
        Pm10Level = pm10Level;
    }

    public String getPm2_5Level() {
        return Pm2_5Level;
    }

    public void setPm2_5Level(String pm2_5Level) {
        Pm2_5Level = pm2_5Level;
    }

    public List<HomeStationBean> getStationList() {
        return StationList;
    }

    public void setStationList(List<HomeStationBean> stationList) {
        StationList = stationList;
    }

    public List<HomeStationBean> getDistrictList() {
        return DistrictList;
    }

    public void setDistrictList(List<HomeStationBean> districtList) {
        DistrictList = districtList;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getUnheathful() {
        return Unheathful;
    }

    public void setUnheathful(String Unheathful) {
        this.Unheathful = Unheathful;
    }

    public String getTimePoint() {
        if (TimePoint!=null)TimePoint=TimePoint.replace("T"," ");
        return TimePoint;
    }

    public void setTimePoint(String TimePoint) {
        if(TimePoint!=null)TimePoint=TimePoint.replace("T"," ");
        this.TimePoint = TimePoint;
    }

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getNO2() {
        return NO2;
    }

    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }

    public String getO3() {
        return O3;
    }

    public void setO3(String O3) {
        this.O3 = O3;
    }

    public String getSO2() {
        return SO2;
    }

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }

    public String getPM10() {
        return PM10;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public String getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(String PM2_5) {
        this.PM2_5 = PM2_5;
    }

    public String getAQI() {
        return AQI;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    public String getPrimaryPollutant() {
        return PrimaryPollutant;
    }

    public void setPrimaryPollutant(String PrimaryPollutant) {
        this.PrimaryPollutant = PrimaryPollutant;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String Quality) {
        this.Quality = Quality;
    }




}
