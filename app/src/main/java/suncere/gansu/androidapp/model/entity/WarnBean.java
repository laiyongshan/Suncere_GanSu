package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/7/4.
 */

public class WarnBean extends BaseBean {


    /**
     * TimePoint : 2017-07-04T10:00:00+08:00
     * StationName : 京东镇政府
     * StationCode : 1152A
     * Longitude : 115.9713889
     * Latitude : 28.69722222
     * AreaCode : 360151
     * CityName : 南昌高新开发区
     * MonValue : 20
     * PreMonValue : 16
     * PollutantCode : 105
     * Level : 0
     * ExecptionInfo : 突变报警！2017年07月04日 10时京东镇政府PM2.5相比上小时提升25.00%
     * Mark : —
     */

    private String TimePoint;
    private String StationName;
    private String StationCode;
    private String Longitude;
    private String Latitude;
    private String AreaCode;
    private String CityName;
    private String MonValue;
    private String PreMonValue;
    private String PollutantCode;
    private String Level;
    private String ExecptionInfo;
    private String Mark;
    private boolean isheadView;

    @Override
    public String toString() {
        return "WarnBean{" +
                "TimePoint='" + TimePoint + '\'' +
                ", StationName='" + StationName + '\'' +
                ", StationCode='" + StationCode + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", AreaCode='" + AreaCode + '\'' +
                ", CityName='" + CityName + '\'' +
                ", MonValue='" + MonValue + '\'' +
                ", PreMonValue='" + PreMonValue + '\'' +
                ", PollutantCode='" + PollutantCode + '\'' +
                ", Level='" + Level + '\'' +
                ", ExecptionInfo='" + ExecptionInfo + '\'' +
                ", Mark='" + Mark + '\'' +
                ", isheadView=" + isheadView +
                '}';
    }

    public boolean isheadView() {
        return isheadView;
    }

    public void setIsheadView(boolean isheadView) {
        this.isheadView = isheadView;
    }


    public String getTimePoint() {
        return TimePoint;
    }

    public void setTimePoint(String TimePoint) {
        this.TimePoint = TimePoint;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String StationCode) {
        this.StationCode = StationCode;
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

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getMonValue() {
        return MonValue;
    }

    public void setMonValue(String MonValue) {
        this.MonValue = MonValue;
    }

    public String getPreMonValue() {
        return PreMonValue;
    }

    public void setPreMonValue(String PreMonValue) {
        this.PreMonValue = PreMonValue;
    }

    public String getPollutantCode() {
        return PollutantCode;
    }

    public void setPollutantCode(String PollutantCode) {
        this.PollutantCode = PollutantCode;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getExecptionInfo() {
        return ExecptionInfo;
    }

    public void setExecptionInfo(String ExecptionInfo) {
        this.ExecptionInfo = ExecptionInfo;
    }

    public String getMark() {
        return Mark;
    }

    public void setMark(String Mark) {
        this.Mark = Mark;
    }
}
