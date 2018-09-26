package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/5/23.
 */

public class HomeStationChartBean extends BaseBean {

    /**
     * StationCode : 1114A
     * StationName : 东湖区环保局
     * LabelXValue : 2017-05-20T16:00:00
     * CreateTime : 2017-05-20T16:00:00
     * YValue : 59
     * PollutantCode : 99
     * Level : 2
     * Month : 5
     */

    private String StationCode;
    private String StationName;
    private String LabelXValue;
    private String CreateTime;
    private String YValue;
    private String PollutantCode;
    private String Level;
    private String Month;

    @Override
    public String toString() {
        return "HomeStationChartBean{" +
                "StationCode='" + StationCode + '\'' +
                ", StationName='" + StationName + '\'' +
                ", LabelXValue='" + LabelXValue + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", YValue='" + YValue + '\'' +
                ", PollutantCode='" + PollutantCode + '\'' +
                ", Level='" + Level + '\'' +
                ", Month='" + Month + '\'' +
                '}';
    }

    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String StationCode) {
        this.StationCode = StationCode;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public String getLabelXValue() {
        if (LabelXValue!=null)LabelXValue=LabelXValue.replace("T"," ");
        return LabelXValue;
    }

    public void setLabelXValue(String LabelXValue) {
        if (LabelXValue!=null)LabelXValue=LabelXValue.replace("T"," ");
        this.LabelXValue = LabelXValue;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getYValue() {
        return YValue;
    }

    public void setYValue(String YValue) {
        this.YValue = YValue;
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

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }
}
