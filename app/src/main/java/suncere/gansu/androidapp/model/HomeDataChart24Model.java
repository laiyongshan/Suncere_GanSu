package suncere.gansu.androidapp.model;

import java.util.List;

import suncere.gansu.androidapp.model.entity.BaseBean;
import suncere.gansu.androidapp.model.entity.HomeStationChartBean;

/**
 * Created by Hjo on 2017/5/31.
 */

public class HomeDataChart24Model extends BaseBean {


    /**
     * ChartDatas : [{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-30T17:00:00","CreateTime":"2017-05-30T17:00:00","YValue":"17","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-30T18:00:00","CreateTime":"2017-05-30T18:00:00","YValue":"7","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-30T20:00:00","CreateTime":"2017-05-30T20:00:00","YValue":"11","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-30T21:00:00","CreateTime":"2017-05-30T21:00:00","YValue":"60","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-30T22:00:00","CreateTime":"2017-05-30T22:00:00","YValue":"38","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-30T23:00:00","CreateTime":"2017-05-30T23:00:00","YValue":"87","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T00:00:00","CreateTime":"2017-05-31T00:00:00","YValue":"18","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T01:00:00","CreateTime":"2017-05-31T01:00:00","YValue":"24","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T02:00:00","CreateTime":"2017-05-31T02:00:00","YValue":"87","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T03:00:00","CreateTime":"2017-05-31T03:00:00","YValue":"85","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T06:00:00","CreateTime":"2017-05-31T06:00:00","YValue":"30","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T07:00:00","CreateTime":"2017-05-31T07:00:00","YValue":"24","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T08:00:00","CreateTime":"2017-05-31T08:00:00","YValue":"96","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T09:00:00","CreateTime":"2017-05-31T09:00:00","YValue":"71","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T10:00:00","CreateTime":"2017-05-31T10:00:00","YValue":"9","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T11:00:00","CreateTime":"2017-05-31T11:00:00","YValue":"73","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T12:00:00","CreateTime":"2017-05-31T12:00:00","YValue":"74","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T13:00:00","CreateTime":"2017-05-31T13:00:00","YValue":"95","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T14:00:00","CreateTime":"2017-05-31T14:00:00","YValue":"24","PollutantCode":"100","Level":"1","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T15:00:00","CreateTime":"2017-05-31T15:00:00","YValue":"61","PollutantCode":"100","Level":"2","Month":"5"},{"StationCode":"1114A","StationName":"东湖区环保局","LabelXValue":"2017-05-31T16:00:00","CreateTime":"2017-05-31T16:00:00","YValue":"27","PollutantCode":"100","Level":"1","Month":"5"}]
     * TimePoint : 2017-05-31T16:00:00
     * StationCode : 1114A
     * PositionName : 东湖区环保局
     * Longitude : 115.9086111
     * Latitude : 28.71027778
     * AreaCode : 360102
     * UniqueCode : 360102051
     * CityName : 东湖区
     * AQI : 109
     * Measure : 儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼
     * Unheathful : 易感人群症状有轻度加剧，健康人群出现刺激症状
     * PrimaryPollutant : 细颗粒物(PM2.5)
     * Quality : null
     * SO2 : 27
     * NO2 : 39
     * O3_8h : null
     * O3 : 33
     * CO : 0.1
     * PM10 : 84
     * PM2_5 : 82
     */

    private String TimePoint;
    private String StationCode;
    private String PositionName;
    private String Longitude;
    private String Latitude;
    private String AreaCode;
    private String UniqueCode;
    private String CityName;
    private String AQI;
    private String Measure;
    private String Unheathful;
    private String PrimaryPollutant;
    private Object Quality;
    private String SO2;
    private String NO2;
    private Object O3_8h;
    private String O3;
    private String CO;
    private String PM10;
    private String PM2_5;

    private List<HomeStationChartBean> ChartDatas;

    @Override
    public String toString() {
        return "HomeDataChart24Model{" +
                "TimePoint='" + TimePoint + '\'' +
                ", StationCode='" + StationCode + '\'' +
                ", PositionName='" + PositionName + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", AreaCode='" + AreaCode + '\'' +
                ", UniqueCode='" + UniqueCode + '\'' +
                ", CityName='" + CityName + '\'' +
                ", AQI='" + AQI + '\'' +
                ", Measure='" + Measure + '\'' +
                ", Unheathful='" + Unheathful + '\'' +
                ", PrimaryPollutant='" + PrimaryPollutant + '\'' +
                ", Quality=" + Quality +
                ", SO2='" + SO2 + '\'' +
                ", NO2='" + NO2 + '\'' +
                ", O3_8h=" + O3_8h +
                ", O3='" + O3 + '\'' +
                ", CO='" + CO + '\'' +
                ", PM10='" + PM10 + '\'' +
                ", PM2_5='" + PM2_5 + '\'' +
                ", ChartDatas=" + ChartDatas +
                '}';
    }

    public String getTimePoint() {
        return TimePoint;
    }

    public void setTimePoint(String TimePoint) {
        this.TimePoint = TimePoint;
    }

    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String StationCode) {
        this.StationCode = StationCode;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String PositionName) {
        this.PositionName = PositionName;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public String getUniqueCode() {
        return UniqueCode;
    }

    public void setUniqueCode(String UniqueCode) {
        this.UniqueCode = UniqueCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getAQI() {
        return AQI;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String Measure) {
        this.Measure = Measure;
    }

    public String getUnheathful() {
        return Unheathful;
    }

    public void setUnheathful(String Unheathful) {
        this.Unheathful = Unheathful;
    }

    public String getPrimaryPollutant() {
        return PrimaryPollutant;
    }

    public void setPrimaryPollutant(String PrimaryPollutant) {
        this.PrimaryPollutant = PrimaryPollutant;
    }

    public Object getQuality() {
        return Quality;
    }

    public void setQuality(Object Quality) {
        this.Quality = Quality;
    }

    public String getSO2() {
        return SO2;
    }

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }

    public String getNO2() {
        return NO2;
    }

    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }

    public Object getO3_8h() {
        return O3_8h;
    }

    public void setO3_8h(Object O3_8h) {
        this.O3_8h = O3_8h;
    }

    public String getO3() {
        return O3;
    }

    public void setO3(String O3) {
        this.O3 = O3;
    }

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
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

    public List<HomeStationChartBean> getChartDatas() {
        return ChartDatas;
    }

    public void setChartDatas(List<HomeStationChartBean> ChartDatas) {
        this.ChartDatas = ChartDatas;
    }



}
