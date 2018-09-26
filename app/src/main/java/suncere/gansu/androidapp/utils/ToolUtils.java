package suncere.gansu.androidapp.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.customview.DateTimeTool;
import suncere.gansu.androidapp.ui.MyApplication;

/**
 * Created by Hjo on 2017/5/17.
 */

public class ToolUtils {

    public static  Animation getRefreshAnimation( ){
        Animation operatingAnim;//动画
        operatingAnim = AnimationUtils.loadAnimation(MyApplication.getMyApplicationContext(), R.anim.tip);
        operatingAnim.setDuration(1000);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        return  operatingAnim;
    }

    public static CharSequence[] GetBaseYearItems() {
        Date date = DateTimeTool.GetNow();
        int year = DateTimeTool.GetYear(date);
        int count=year-2013+1;
        CharSequence[] result = new CharSequence[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = String.format("%d年", year - i);
        }
        return result;
    }

    //獲取年
    public static String getYear(String date){
        return stringToData(date,"yyyy-MM-dd HH:mm:ss","yyyy");
    }

    //獲取月
    public static String getMonth(String date){
        return stringToData(date,"yyyy-MM-dd HH:mm:ss","MM");
    }

    //獲取日
    public static String getDay(String date){
        return stringToData(date,"yyyy-MM-dd HH:mm:ss","dd");
    }


    /**
     * 将字符串转为时间  再将时间转为固定格式   返回特定字符串
     * @param str            需要转化的字符串
     * @param dateFormatStr  当前字符串的时间格式  如：yyyy-MM-dd HH:mm:ss
     * @param formatStr      需要转化成的时间格式  如：MM_dd
     * @return
     */
    public static String stringToData(String str,String dateFormatStr,String formatStr){
        String time="更新于---- -- -- --时";
        if (str!=null) {
            str=str.replace("T"," ");
            int index=str.indexOf(".");
            if (index!=-1)
                str=str.substring(0,index);
            DateFormat sdf=new SimpleDateFormat(dateFormatStr);
            Date date = null;
            try {
                date=sdf.parse(str);
            } catch (Exception e) {
                // TODO: handle exception
            }
            SimpleDateFormat s = new SimpleDateFormat(formatStr);
            time=s.format(date).toString();
        }
        return time;
    }

    public  static String getToyDay() { // 今天
        Calendar cal =   Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }

    public  static String getLastDay() { // 昨天
        Calendar cal =   Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }

    //获取今年的1月1号
    public static String getThisYear1day(){
        Calendar cal =   Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-01-01");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }

    //获取上一年的今天
    public static String getLastYearLastday(){
        Calendar cal =   Calendar.getInstance();
        cal.add(Calendar.YEAR,   -1);
        cal.add(Calendar.DAY_OF_YEAR,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }

    //获取上一年这个月的一号
    public static String getLastYearThisMonth1day(){
        Calendar cal =   Calendar.getInstance();
        cal.add(Calendar.YEAR,   -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-01-01");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }


    /**
     * 获取昨天时间 yyyy-M-d
     */
    public static String getYesterday() {
        Calendar cal =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }

    public static CharSequence[] GetSimpleTimeRange(int index,String baseYearString,String compareYearString) {

        if (index == 1) {
            return GetSimpleWeekItems(baseYearString,compareYearString);
        } else if (index == 2) {
            return GetSimpleMonthItems(baseYearString,compareYearString);

        } else if (index == 3) {
            return GetSimpleSeasonItems(baseYearString,compareYearString);//获取季度

        }
//        else if (index == 4) {
//            return GetYearItems();
//        }
        return null;
    }

    //修正时间选择的日期  如当前时间为2017年5月  则周数 季度 月份
    public static String changeTimePeriodTextView(int countType,String countIndex,String baseYearString,String compareYearString) {

        String bString =exceptChinese(baseYearString);// baseYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        String cString =exceptChinese(compareYearString) ;//compareYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        String countString =exceptChinese(countIndex); //countIndex.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

        int baseYear = 0;
        int compareYear = 0;
        int count = 0;
        try {
            baseYear =	Integer.valueOf(bString);
            compareYear = Integer.valueOf(cString);
            count = Integer.valueOf(countString);
        } catch (Exception e) {
            // TODO: handle exception
        }

        Date date = DateTimeTool.GetNow();
        int year = DateTimeTool.GetYear(date);


        if (year != baseYear && year != compareYear) {

        }else {
            if (countType == 1) {
                int week2 = DateTimeTool.GetWeekOfYear(date);
                if(count > week2){
                    countIndex = GetSimpleWeekItems(baseYearString, compareYearString)[0]+"";
//                    TimePeriodTextView.setText(countIndex);
                }
            }else if (countType == 2) {
                int week2 = DateTimeTool.GetMonth(date);
                if(count > week2){
                    countIndex =GetSimpleMonthItems(baseYearString, compareYearString)[0]+"";
//                    TimePeriodTextView.setText(countIndex);
                }
            }else if (countType == 3) {
                int week2 = DateTimeTool.GetSeason(date);
                if(count > week2){
                    countIndex = GetSimpleSeasonItems(baseYearString, compareYearString)[0]+"";
//                    TimePeriodTextView.setText(countIndex);
                }
            }
        }
        return  countIndex;
    }



    public static CharSequence[] GetSimpleMonthItems(String baseYearString,String compareYearString) {


        String bString =exceptChinese(baseYearString);// baseYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        String cString =exceptChinese(compareYearString); //compareYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

        int baseYear = 0;
        int compareYear = 0;
        try {
            baseYear =	Integer.valueOf(bString);
            compareYear = Integer.valueOf(cString);

        } catch (Exception e) {
            // TODO: handle exception
        }

        Date date = DateTimeTool.GetNow();
        int year = DateTimeTool.GetYear(date);

        if (year != baseYear && year != compareYear) {
            int month = 12;
            CharSequence[] result = new CharSequence[month];

            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("%d月", month);
                month--;
            }

            return result;

        }else {
            int month = DateTimeTool.GetMonth(date);
            CharSequence[] result = new CharSequence[month];

            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("%d月", month);
                month--;
            }

            return result;
        }

    }

    public static CharSequence[] GetSimpleWeekItems(String baseYearString,String compareYearString) {


        String bString =exceptChinese(baseYearString);// baseYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        String cString =exceptChinese(compareYearString); //compareYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

        int baseYear = 0;
        int compareYear = 0;
        try {
            baseYear =	Integer.valueOf(bString);
            compareYear = Integer.valueOf(cString);

        } catch (Exception e) {
        }

        Date date = DateTimeTool.GetNow();
        int year = DateTimeTool.GetYear(date);

        if (year != baseYear && year != compareYear) {//不是当前年份时  如果基准年和对比年的周数  比如2016年有53周  而2015年只有52周 则统计使用52周

            int baseWeek=getAllWeeks(""+baseYear);
            int compareWeek=getAllWeeks(""+compareYear);
//			int week = 52;
            int week ;
            if (baseWeek==compareWeek) {
                week=baseWeek;
            }else {
                week=52;
            }
            CharSequence[] result = new CharSequence[week];
            int count=week;
            for (int i = 0; i <count; i++) {
                result[i] = String.format("第%d周", week);
                week--;
            }
            return result;

        }else {
            int week2 = DateTimeTool.GetWeekOfYear(date);
            CharSequence[] result = new CharSequence[week2];

            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("第%d周", week2);
                week2--;
            }
            return result;
        }
    }

    public static CharSequence[] GetSimpleSeasonItems(String baseYearString,String compareYearString) {

        String bString = baseYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        String cString = compareYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

        int baseYear = 0;
        int compareYear = 0;
        try {
            baseYear =	Integer.valueOf(bString);
            compareYear = Integer.valueOf(cString);

        } catch (Exception e) {
            // TODO: handle exception
        }

        Date date = DateTimeTool.GetNow();
        int year = DateTimeTool.GetYear(date);

        if (year != baseYear && year != compareYear) {
            int season = 4;

            CharSequence[] result = new CharSequence[season];

            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("第%d季度", season);
                season--;
            }

            return result;
        }else {

//			int season = DateTimeTool.GetSeason(date);// 2017-01-01 16:41 分修复bug 用getSeasonHJO(date)替换DateTimeTool.GetSeason(date)
            // 原因为：DateTimeTool.GetSeason(date)的方法中返回的月份是从0-11的 造成季度不正确
            int season = getSeason(date);
            CharSequence[] result = new CharSequence[season];

            for (int i = 0; i < result.length; i++) {
                result[i] = String.format("第%d季度", season);
                season--;
            }
            return result;
        }

    }


    public static int getSeason(Date date){
        int month=date.getMonth();  //这里的date.getMonth() 返回0-11
//		if(month%3==0)return month/3;
        if (month==0) {
            return 1;
        }else {
            return month/3+1;
        }

    }

    /**
     * 根据年份获取周的个数
     * @param year
     * @return
     */
    public static int getAllWeeks(String year){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(year+"-12-31"));

        } catch (ParseException e) {

        }
        int week =  calendar.get(Calendar.WEEK_OF_YEAR);
        if(week != 53){
            week = 52;
        }
        return week;
    }

    //去除中文字段
    public static String exceptChinese(String str){
        str=str.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
        return str;
    }

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "";
        }
        if (strEndIndex < 0) {
            return "";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }


    /**
     * 全透状态栏
     */
    public static void setStatusBarFullTransparent(Activity context) {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    public static boolean isNum(String str){
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        //可以判断正负、整数小数
        return str.matches("-?[0-9]+.*[0-9]*");
    }

}
