package suncere.gansu.androidapp.model.entity;

/**
 * @author lys
 * @time 2018/8/29 18:20
 * @desc:
 */

public class AQIDayInfoEty extends BaseBean {

    /**
     * Date : 2018-02-01T00:00:00
     * CityName : 兰州市
     * Value : 89
     * Level : 2
     */
    private String Date;
    private String CityName;
    private String Value;
    private String Level;

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getDate() {
        return Date;
    }

    public String getCityName() {
        return CityName;
    }

    public String getValue() {
        return Value;
    }

    public String getLevel() {
        return Level;
    }
}
