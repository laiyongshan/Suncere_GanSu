package suncere.gansu.androidapp.model.entity;

/**
 * @author lys
 * @time 2018/9/3 16:48
 * @desc:
 */

public class WarnBean2 extends BaseBean {

    /**
     * TimePoint : 2018-09-03T16:00:00
     * StationName : 动力公司
     * StationCode : 1043A
     * Longitude : 104.1730556
     * Latitude : 36.54805556
     * AreaCode : 620400
     * CityName : 白银市
     * MonValue : 163
     * PreMonValue : 40
     * PollutantCode : 104
     * Level : 0
     * ExecptionInfo : 2018年09月03日 16时动力公司浓度值超上限，预警！
     * Mark : H
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

    public void setTimePoint(String TimePoint) {
        this.TimePoint = TimePoint;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public void setStationCode(String StationCode) {
        this.StationCode = StationCode;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public void setMonValue(String MonValue) {
        this.MonValue = MonValue;
    }

    public void setPreMonValue(String PreMonValue) {
        this.PreMonValue = PreMonValue;
    }

    public void setPollutantCode(String PollutantCode) {
        this.PollutantCode = PollutantCode;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public void setExecptionInfo(String ExecptionInfo) {
        this.ExecptionInfo = ExecptionInfo;
    }

    public void setMark(String Mark) {
        this.Mark = Mark;
    }

    public String getTimePoint() {
        return TimePoint;
    }

    public String getStationName() {
        return StationName;
    }

    public String getStationCode() {
        return StationCode;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public String getCityName() {
        return CityName;
    }

    public String getMonValue() {
        return MonValue;
    }

    public String getPreMonValue() {
        return PreMonValue;
    }

    public String getPollutantCode() {
        return PollutantCode;
    }

    public String getLevel() {
        return Level;
    }

    public String getExecptionInfo() {
        return ExecptionInfo;
    }

    public String getMark() {
        return Mark;
    }
}
