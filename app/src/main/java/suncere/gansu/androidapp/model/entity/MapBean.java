package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/5/18.
 */

public class MapBean extends  BaseBean{


    /**
     * PollutantType : AQI
     * TimePoint : 2017-05-18T10:00:00
     * DataType : 1
     * GeoName : 东湖区
     * Longitude : 115.9086111
     * Latitude : 28.71027778
     * Level : 3
     * Value : 83
     */

    private String PollutantType;
    private String TimePoint;
    private String DataType;
    private String GeoName;
    private String Longitude;
    private String Latitude;
    private String Level;
    private String Value;

    @Override
    public String toString() {
        return "MapBean{" +
                "PollutantType='" + PollutantType + '\'' +
                ", TimePoint='" + TimePoint + '\'' +
                ", DataType='" + DataType + '\'' +
                ", GeoName='" + GeoName + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", Level='" + Level + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }

    public String getPollutantType() {
        return PollutantType;
    }
    public void setPollutantType(String PollutantType) {
        this.PollutantType = PollutantType;
    }
    public String getTimePoint() {
        if (TimePoint!=null) {
            TimePoint=TimePoint.replace("T"," ");
//            TimePoint=TimePoint.substring(0,TimePoint.indexOf("+"));
        }
        return TimePoint;
    }

    public void setTimePoint(String TimePoint) {
        if (TimePoint!=null) {
            TimePoint=TimePoint.replace("T","");
//            TimePoint=TimePoint.substring(0,TimePoint.indexOf("+"));
        }
        this.TimePoint = TimePoint;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String DataType) {
        this.DataType = DataType;
    }

    public String getGeoName() {
        return GeoName;
    }

    public void setGeoName(String GeoName) {
        this.GeoName = GeoName;
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

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }
}
