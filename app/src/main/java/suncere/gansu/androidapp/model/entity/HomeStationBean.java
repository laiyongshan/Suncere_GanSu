package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/5/12.
 */

public class HomeStationBean extends BaseBean{


    /**
     * TimePoint : 2017-05-12T11:00:00
     * CityName : 东湖区
     * AreaCode : 360102
     * AQI : 85
     * Measure : 极少数异常敏感人群应减少户外活动
     * Unheathful : 空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响
     * PrimaryPollutant : 细颗粒物(PM2.5)
     * Quality : 良
     * SO2 : 31
     * NO2 : 34
     * O3_8h : —
     * O3 : 47
     * CO : 0.0
     * PM10 : 40
     * PM2_5 : 63
     */

//    "StationCode": "1114A",
//    "PositionName": "东湖区环保局",
//    "UniqueCode": "360102051",


    private String StationCode;
    private String PositionName;
    private String UniqueCode;

    private String TimePoint;
    private String CityName;
    private String AreaCode;
    private String AQI;
    private String Measure;
    private String Unheathful;
    private String PrimaryPollutant;
    private String Quality;
    private String SO2;
    private String NO2;
    private String O3_8h;
    private String O3;
    private String CO;
    private String PM10;
    private String PM2_5;


    @Override
    public String toString() {
        return "HomeStationBean{" +
                "StationCode='" + StationCode + '\'' +
                ", PositionName='" + PositionName + '\'' +
                ", UniqueCode='" + UniqueCode + '\'' +
                ", TimePoint='" + TimePoint + '\'' +
                ", CityName='" + CityName + '\'' +
                ", AreaCode='" + AreaCode + '\'' +
                ", AQI='" + AQI + '\'' +
                ", Measure='" + Measure + '\'' +
                ", Unheathful='" + Unheathful + '\'' +
                ", PrimaryPollutant='" + PrimaryPollutant + '\'' +
                ", Quality='" + Quality + '\'' +
                ", SO2='" + SO2 + '\'' +
                ", NO2='" + NO2 + '\'' +
                ", O3_8h='" + O3_8h + '\'' +
                ", O3='" + O3 + '\'' +
                ", CO='" + CO + '\'' +
                ", PM10='" + PM10 + '\'' +
                ", PM2_5='" + PM2_5 + '\'' +
                '}';
    }

    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String stationCode) {
        StationCode = stationCode;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public String getUniqueCode() {
        return UniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        UniqueCode = uniqueCode;
    }

    public String getTimePoint() {
        return TimePoint;
    }

    public void setTimePoint(String TimePoint) {
        this.TimePoint = TimePoint;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
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

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String Quality) {
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

    public String getO3_8h() {
        return O3_8h;
    }

    public void setO3_8h(String O3_8h) {
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
}
