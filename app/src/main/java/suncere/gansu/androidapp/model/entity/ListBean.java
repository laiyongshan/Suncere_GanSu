package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/6/6.
 */

public class ListBean extends BaseBean {


    /**
     * SortValue : —
     * CountType : 0
     * SortID : 1
     * StationName : —
     * StationCode : —
     * Level : -1
     * CityName : 吉安县
     * TimePoint : 2017-06-06T09:47:44.0617398+08:00
     * PollutantCode : AQI
     * PrimaryPollutant : —
     */

    private String SortValue;
    private String CountType;
    private String SortID;
    private String StationName;
    private String StationCode;
    private String Level;
    private String CityName;
    private String TimePoint;
    private String PollutantCode;
    private String PrimaryPollutant;
    private String DataType;

    @Override
    public String toString() {
        return "ListBean{" +
                "SortValue='" + SortValue + '\'' +
                ", CountType='" + CountType + '\'' +
                ", SortID='" + SortID + '\'' +
                ", StationName='" + StationName + '\'' +
                ", StationCode='" + StationCode + '\'' +
                ", Level='" + Level + '\'' +
                ", CityName='" + CityName + '\'' +
                ", TimePoint='" + TimePoint + '\'' +
                ", PollutantCode='" + PollutantCode + '\'' +
                ", PrimaryPollutant='" + PrimaryPollutant + '\'' +
                ", DataType='" + DataType + '\'' +
                '}';
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getSortValue() {
        return SortValue;
    }

    public void setSortValue(String SortValue) {
        this.SortValue = SortValue;
    }

    public String getCountType() {
        return CountType;
    }

    public void setCountType(String CountType) {
        this.CountType = CountType;
    }

    public String getSortID() {
        return SortID;
    }

    public void setSortID(String SortID) {
        this.SortID = SortID;
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

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getTimePoint() {
        if (TimePoint!=null)TimePoint=TimePoint.replace("T"," ");
        return TimePoint;
    }

    public void setTimePoint(String TimePoint) {
        if (TimePoint!=null)TimePoint=TimePoint.replace("T"," ");
        this.TimePoint = TimePoint;
    }

    public String getPollutantCode() {
        return PollutantCode;
    }

    public void setPollutantCode(String PollutantCode) {
        this.PollutantCode = PollutantCode;
    }

    public String getPrimaryPollutant() {
        return PrimaryPollutant;
    }

    public void setPrimaryPollutant(String PrimaryPollutant) {
        this.PrimaryPollutant = PrimaryPollutant;
    }
}
