package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/7/27.
 */

public class CompareBean2 extends  BaseBean {

    /**
     * CityName : 南昌市
     * PM10 : 96
     * PM2_5 : 47
     * PrePM10 : 0
     * PrePM2_5 : 0
     * GoodDay : 10
     */

    private String CityName;
    private String PM10;
    private String PM2_5;
    private String PrePM10;
    private String PrePM2_5;
    private String GoodDay;

    @Override
    public String toString() {
        return "CompareBean2{" +
                "CityName='" + CityName + '\'' +
                ", PM10='" + PM10 + '\'' +
                ", PM2_5='" + PM2_5 + '\'' +
                ", PrePM10='" + PrePM10 + '\'' +
                ", PrePM2_5='" + PrePM2_5 + '\'' +
                ", GoodDay='" + GoodDay + '\'' +
                '}';
    }



    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
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

    public String getPrePM10() {
        return PrePM10;
    }

    public void setPrePM10(String PrePM10) {
        this.PrePM10 = PrePM10;
    }

    public String getPrePM2_5() {
        return PrePM2_5;
    }

    public void setPrePM2_5(String PrePM2_5) {
        this.PrePM2_5 = PrePM2_5;
    }

    public String getGoodDay() {
        return GoodDay;
    }

    public void setGoodDay(String GoodDay) {
        this.GoodDay = GoodDay;
    }
}
