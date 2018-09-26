package suncere.gansu.androidapp.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.ui.MyApplication;

/**
 * Created by Hjo on 2017/5/15.
 */

public  class ColorUtils {



    public static int[] Colors ={
//            Color.rgb(22,189,62),
//            Color.rgb(218,195,0),
//            Color.rgb(237,143,40),
//            Color.rgb(218,38,38),
//            Color.rgb(156,38,155),
//            Color.rgb(140,27,62),
//            Color.rgb(109,105,105),

            Color.rgb(64,203,4),
            Color.rgb(228,217,8),
            Color.rgb(255,177,9),
            Color.rgb(255,73,9),
            Color.rgb(255,9,188),
            Color.rgb(134,7,40),
            Color.rgb(136,136,136)
    };

    private static int[]Bg=new int []{
                    R.drawable.round_rect_aqi_1,
                    R.drawable.round_rect_aqi_2,
                    R.drawable.round_rect_aqi_3,
                    R.drawable.round_rect_aqi_4,
                    R.drawable.round_rect_aqi_5,
                    R.drawable.round_rect_aqi_6,
                    R.drawable.round_rect_aqi_na
            };

    private  static String[] Healths = new String[]{
            "空气质量令人满意，基本无空气污染",
            "空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响",
            "易感人群症状有轻度加剧，健康人群出现刺激症状",
            "进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响",
            "心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状",
            "健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病",
            "—"
    };
    private static String[] Sugges = new String[]{
            "各类人群可正常活动",
            "极少数异常敏感人群应减少户外活动",
            "儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼",
            "儿童、老年人及心脏病、呼吸系统疾病患者应避免长时间、高强度的户外锻炼，一般人群适量减少户外活动",
            "儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动",
            "儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动",
            "—"
    };
    private static String[] Qualitys = new String[]{"优","良","轻度污染","中度污染","重度污染","严重污染","—"};
    private static String[] AbbreviationQualitys = new String[]{"优","良","轻度","中度","重度","严重","—"};


    public  static int getAQILevel(String AQI){
        float i = 0;
        try {
            i = Float.valueOf(AQI);
        }catch (Exception e){
            return 6;
        }
        if (0<=i && i<=50) {
            return 0;
        }else if (50<i && i<=100){
            return 1;
        }else if (100<i && i<=150){
            return 2;
        }else if (150<i && i<=200){
            return 3;
        }else if (200<i && i<=300){
            return 4;
        }else if (i>300){
            return 5;
        }else{
            return 6;
        }

    }
    public static  int getQualityLevel(String Quality) {

        if (Quality.equals("优")) {
            return 0;
        } else if (Quality.equals("良")) {
            return 1;
        } else if (Quality.equals("轻度污染")) {
            return 2;

        } else if (Quality.equals("中度污染")) {
            return 3;

        } else if (Quality.equals("重度污染")) {
            return 4;
        } else if (Quality.equals("严重污染")) {
            return 5;
        } else {
            return 6;
        }

    }


    /***AQI***/
    public static  String  getAbbreviationQualitysWithLevel(String level){
        int i;
        try {
            i = Integer.valueOf(level);
        }catch (Exception e){
            return AbbreviationQualitys[6];
        }
        return AbbreviationQualitys[i-1==-1 ? 6 : (i-1)];
    }
    public static  int  getColorWithLevel(String level){
        int i;
        try {
            i = Integer.valueOf(level);
        }catch (Exception e){
            return Colors[6];
        }
        return Colors[i-1==-1 ? 6 : (i-1)];
    }

    public  static int getColorWithAQI(String AQI){
        int level = getAQILevel(AQI);
        return Colors[level];
    }
    public static  String getHealthWithAQI(String AQI){
        int level = getAQILevel(AQI);
        return Healths[level];
    }
    public  static String getSuggeWithAQI(String AQI){
        int level = getAQILevel(AQI);
        return Sugges[level];
    }
    public   static String getQualityWithAQI(String AQI){
        int level = getAQILevel(AQI);
        return Qualitys[level];
    }
    public static  String getQualityWithAQI2(String AQI){
        int level = getAQILevel(AQI);
        return AbbreviationQualitys[level];
    }

    /***Quality***/
    public  static int getColorFromQuality(String Quality){
        int level = getQualityLevel(Quality);
        return Colors[level];
    }
    public  static int getColorFromQualityNumber(String Quality){
        int level;
        try{
            level=Integer.valueOf(Quality);
        }catch (Exception e){
            return Colors[6];
        }
        return Colors[level-1==-1 ? 6 : (level-1)];
    }
    public  static String getHealthfromQuality(String Quality){
        int level = getQualityLevel(Quality);
        return Healths[level];
    }
    public   static String getSuggefromQuality(String Quality){
        int level = getQualityLevel(Quality);
        return Sugges[level];
    }

    public static  String getAbbreviationQualitys(String Quality){
        int level = getQualityLevel(Quality);
        return AbbreviationQualitys[level];
    }

    public   static Drawable getBgFromAQI(String AQIVaule){
        int level = getAQILevel(AQIVaule);
        Drawable drawable = MyApplication.getMyApplicationContext().getResources().getDrawable(Bg[level]);
        return drawable;
    }

    public   static Drawable getBgFromQualitys(String Quality){
        int level = getQualityLevel(Quality);
        Drawable drawable = MyApplication.getMyApplicationContext().getResources().getDrawable(Bg[level]);
        return drawable;
    }

    public static int getBgFromLevel(String level){
        int i;
        try{
            i=Integer.valueOf(level);
        }catch (Exception e){
            return Bg[6];
        }
        return Bg[i-1==-1 ? 6 : (i-1)] ;
    }

    public static Drawable getDrawableBgFromLevel(String level){
        int i;
        try{
            i=Integer.valueOf(level);
        }catch (Exception e){
            return MyApplication.getMyApplicationContext().getResources().getDrawable(Bg[6]);
        }
        return MyApplication.getMyApplicationContext().getResources().getDrawable(Bg[i-1==-1 ? 6 : (i-1)]) ;
    }

    public  static Drawable  CompareUpandDown(String ItemName,String DataChangePercent){

//      boolean i="—%".equals(DataChangePercent);
//      boolean c="—".equals(DataChangePercent);
        if("—%".equals(DataChangePercent)  || "—".equals(DataChangePercent)) return MyApplication.getMyApplicationContext().getResources().getDrawable(R.drawable.arrow_empty );
        else {
            if (ItemName.startsWith("优良")){  // 优良率和优良天数的处理
                if(DataChangePercent.startsWith("-"))return  MyApplication.getMyApplicationContext().getResources().getDrawable(R.drawable.data_up_0);
                else return  MyApplication.getMyApplicationContext().getResources().getDrawable(R.drawable.data_down_1);
            }else{
                if(DataChangePercent.startsWith("-"))return  MyApplication.getMyApplicationContext().getResources().getDrawable(R.drawable.data_down_0);
                else if (Double.valueOf(DataChangePercent)>0)return  MyApplication.getMyApplicationContext().getResources().getDrawable(R.drawable.data_up_1);
                else return MyApplication.getMyApplicationContext().getResources().getDrawable(R.drawable.arrow_empty );
            }
        }
    }

    public static boolean isCityOrStationCode(String DataType){
        String str= ListType.CityStationg+"";
        if (DataType.equals(ListType.CityStationg.getIndex()+"") || DataType.equals(ListType.CountyStationg.getIndex()+"")  || DataType.equals(ListType.CountryStationg.getIndex()+"") ){ //区县 城市  全国
            return true;
        }
        return false;
    }

    public static boolean isZongHeZhiShu(String pollutantType){
        if (pollutantType.equals("98")||(pollutantType.equals("97"))){
            return true;
        }
        return false;
    }

    public static Drawable transparentDrawable(){
        return  MyApplication.getMyApplicationContext().getResources().getDrawable(R.drawable.transparent_bg );
    }


    public static String  getPutTime(String sdf,String time){
        if (time!=null) {
            time=time.replace("T"," ");
            int index=time.indexOf("+");
            if (index!=-1)
                time=time.substring(0,index);
        }
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try {
            date=sDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat s=new SimpleDateFormat(sdf);
         String str=s.format(date);
        return  s.format(date);
    }



    /**
     * 将字符串转为时间  再将时间转为固定格式   返回特定字符串
     * @param str            需要转化的字符串
     * @param dateFormatStr  当前字符串的时间格式  如：yyyy-MM-dd HH:mm:ss
     * @param formatStr      需要转化成的时间格式  如：MM_dd
     * @return
     */
    public static String stringToData(String str,String dateFormatStr,String formatStr){

        if (str!=null) {
            str=str.replace("T"," ");
           int index=str.indexOf("+");
            if (index!=-1)
                str=str.substring(0,index);
        }

        DateFormat sdf=new SimpleDateFormat(dateFormatStr);
        Date date = null;
        try {
            date=sdf.parse(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        SimpleDateFormat s = new SimpleDateFormat(formatStr);

        return s.format(date);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String PollutantChiness2Eglish(String pollutant){
        StringBuffer buffer=new StringBuffer();
        String str[]=pollutant.split(",");
        for ( int i=0;i< str.length ;i++){
            String PollutantName=str[i];
            if (PollutantName.equals("二氧化硫"))PollutantName=  "SO2";
            else if (PollutantName.equals("一氧化碳"))PollutantName=  "CO";
            else if (PollutantName.equals("二氧化氮"))PollutantName=  "NO2";
            else if (PollutantName.equals("臭氧"))PollutantName=  "O3";
            else if (PollutantName.equals("臭氧1小时"))PollutantName=  "O3";
            else if (PollutantName.equals("臭氧8小时"))PollutantName=  "O3_8h";
            else if (PollutantName.equals("颗粒物(PM2.5)"))PollutantName=  "PM2.5";
            else if (PollutantName.equals("细颗粒物(PM2.5)"))PollutantName=  "PM2.5";
            else if (PollutantName.equals("颗粒物(PM2_5)"))PollutantName=  "PM2.5";
            else if (PollutantName.equals("颗粒物(PM10)"))PollutantName=  "PM10";
            else if (PollutantName.equals("细颗粒物(PM10)"))PollutantName=  "PM10";
            if (str.length>1 && i!=str.length-1){
                buffer.append(PollutantName+",");
            }else{
                buffer.append(PollutantName);
            }
        }
        return buffer.toString();
    }

    // 数 据 修 约
    public static String PM2_5DataChange(String value){
        int Datavalue;
        try{
            Datavalue=Integer.valueOf(value);
            if (-5<=Datavalue && Datavalue<=5) return "<3";
            else return value;
        }catch (Exception e){
            return "—";
        }
    }

    // 数 据 修 约
    public static String PM10DataChange(String value){
        int Datavalue;
        try{
            Datavalue=Integer.valueOf(value);
            if (-5<=Datavalue && Datavalue<=5) return "<6";
            else return value;
        }catch (Exception e){
            return "—";
        }
    }
}
