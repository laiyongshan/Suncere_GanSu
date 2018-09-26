package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/7/20.
 */

public class HomeAllCitiesBean extends BaseBean {


    /**
     * CityCode : 360100
     * CityName : 南昌市
     * Unheathful : 空气质量令人满意，基本无空气污染
     * CoLevel : 1
     * No2Level : 1
     * So2Level : 1
     * O3Level : 1
     * Pm10Level : 1
     * Pm2_5Level : 1
     * StationList : null
     * TimePoint : 2017-07-20T09:00:00
     * CO : 0.833
     * NO2 : 15
     * O3 : 37
     * SO2 : 19
     * PM10 : 28
     * PM2_5 : 8
     * AQI : 28
     * PrimaryPollutant : —
     * Quality : 优
     */

    private String CityCode;
    private String CityName;
    private String Unheathful;
    private String CoLevel;
    private String No2Level;
    private String So2Level;
    private String O3Level;
    private String Pm10Level;
    private String Pm2_5Level;
    private Object StationList;
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

    @Override
    public String toString() {
        return "HomeAllCitiesBean{" +
                "CityCode='" + CityCode + '\'' +
                ", CityName='" + CityName + '\'' +
                ", Unheathful='" + Unheathful + '\'' +
                ", CoLevel='" + CoLevel + '\'' +
                ", No2Level='" + No2Level + '\'' +
                ", So2Level='" + So2Level + '\'' +
                ", O3Level='" + O3Level + '\'' +
                ", Pm10Level='" + Pm10Level + '\'' +
                ", Pm2_5Level='" + Pm2_5Level + '\'' +
                ", StationList=" + StationList +
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
                '}';
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getUnheathful() {
        return Unheathful;
    }

    public void setUnheathful(String Unheathful) {
        this.Unheathful = Unheathful;
    }

    public String getCoLevel() {
        return CoLevel;
    }

    public void setCoLevel(String CoLevel) {
        this.CoLevel = CoLevel;
    }

    public String getNo2Level() {
        return No2Level;
    }

    public void setNo2Level(String No2Level) {
        this.No2Level = No2Level;
    }

    public String getSo2Level() {
        return So2Level;
    }

    public void setSo2Level(String So2Level) {
        this.So2Level = So2Level;
    }

    public String getO3Level() {
        return O3Level;
    }

    public void setO3Level(String O3Level) {
        this.O3Level = O3Level;
    }

    public String getPm10Level() {
        return Pm10Level;
    }

    public void setPm10Level(String Pm10Level) {
        this.Pm10Level = Pm10Level;
    }

    public String getPm2_5Level() {
        return Pm2_5Level;
    }

    public void setPm2_5Level(String Pm2_5Level) {
        this.Pm2_5Level = Pm2_5Level;
    }

    public Object getStationList() {
        return StationList;
    }

    public void setStationList(Object StationList) {
        this.StationList = StationList;
    }

    public String getTimePoint() {
        return TimePoint;
    }

    public void setTimePoint(String TimePoint) {
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
