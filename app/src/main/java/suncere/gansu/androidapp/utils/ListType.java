package suncere.gansu.androidapp.utils;

/**
 * Created by Hjo on 2017/6/27.
 */

public enum ListType {

    LiveTime("LiveTime",0),  //实时
    DateTime("DateTime",1),  //日排
    MonthTime("MonthTime",2),//  月排
    YearTime("YearTime",3),//年排

    Country("Country",1), //国控
    Province("Province",2),// 省控

    Stationg("Stationg",0), //站点
    CityStationg("CityStationg",1), // 城市
    CountyStationg("CountyStationg",2),//区县
    CountryStationg("CountryStationg",3),//全国
    City169("City169",4),//169城市
    Provice31("Provice31",5),//31省


    /***  污染物代码  ***/
    GoodDay("GoodDay",97),
    Zonghezhishu("Zonghezhishu",98),
    AQI("AQI",99),
    SO2("SO2",100),
    NO2("NO2",101),
    O3("O3",102),
    CO("CO",103),
    PM10("PM10",104),
    PM25("PM25",105),

    CompareMonthTime("CompareMonthTime",0),
    CompareStartTime("CompareStartTime",1),
    CompareEndTime("CompareEndTime",2);

    private String name ;
    private int index ;

    private ListType(String name , int index ){
        this.name = name ;
        this.index = index ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}
