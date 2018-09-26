package suncere.gansu.androidapp.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import suncere.gansu.androidapp.customview.DateTimeTool;

public class Tool {

	public static CharSequence[] getCitys222() {
		//"甘肃12城市",
		CharSequence[] citys = new CharSequence[] {"甘肃14城市","兰州市", "嘉峪关市", "金昌市",
				"武威市", "酒泉市", "张掖市", "定西市", "天水市", "白银市", "平凉市", "临夏回族自治州", "庆阳市",
				"陇南市", "甘南藏族自治州" };

		return citys;
	}

	public static CharSequence[] GetTimeRange(int index) {

		if (index == 0) {
			return GetWeekItems();
		} else if (index == 1) {
			return GetMonthItems();

		} else if (index == 2) {
			return GetSeasonItems();

		} else if (index == 3) {
			return GetYearItems();
		} else {
			return null;

		}
	}

	public static CharSequence[] GetSimpleTimeRange(int index,String baseYearString,String compareYearString) {

		if (index == 1) {
			return GetSimpleWeekItems(baseYearString,compareYearString);
		} else if (index == 2) {
			return GetSimpleMonthItems(baseYearString,compareYearString);

		} else if (index == 3) {
			return GetSimpleSeasonItems(baseYearString,compareYearString);//获取季度

		} else if (index == 4) {
			return GetYearItems();
		}
		return null;
	}

	public static CharSequence[] GetWeekItems() {
		CharSequence[] result = new CharSequence[8];

		Date date = DateTimeTool.GetNow();
		for (int i = 0; i < result.length; i++) {
			int year = DateTimeTool.GetYear(date);
			int week = DateTimeTool.GetWeekOfYear(date);
			result[i] = String.format("%d年第%d周", year, week);
			date = DateTimeTool.AddDays(date, -7);
		}

		return result;
	}

	public static CharSequence[] GetSimpleWeekItems(String baseYearString,String compareYearString) {


		String bString = baseYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符
		String cString = compareYearString.replaceAll("[\u4e00-\u9fa5]", "");//去掉中文字符

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

	public static CharSequence[] GetMonthItems() {
		CharSequence[] result = new CharSequence[12];

		Date date = DateTimeTool.GetNow();
		for (int i = 0; i < result.length; i++) {
			int year = DateTimeTool.GetYear(date);
			int month = DateTimeTool.GetMonth(date);
			result[i] = String.format("%d年%d月", year, month);
			date = DateTimeTool.AddMonths(date, -1);
		}

		return result;
	}

	public static CharSequence[] GetSimpleMonthItems(String baseYearString,String compareYearString) {

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

	public static CharSequence[] GetSeasonItems() {

		CharSequence[] result = new CharSequence[4];
		Date date = DateTimeTool.GetNow();
		for (int i = 0; i < result.length; i++) {
			int year = DateTimeTool.GetYear(date);
			int season = DateTimeTool.GetSeason(date);
			result[i] = String.format("%d年第%d季度", year, season);
			date = DateTimeTool.AddMonths(date, -3);
		}

		return result;
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
			int season = getSeasonHJO(date);
			CharSequence[] result = new CharSequence[season];

			for (int i = 0; i < result.length; i++) {
				result[i] = String.format("第%d季度", season);
				season--;
			}
			return result;
		}
	}


	/**
	 * hjo 修改bug于2017-01-01 16:41
	 * @param date
	 * @return
	 */
	public static int getSeasonHJO(Date date){
		int month=date.getMonth();  //这里的date.getMonth() 返回0-11
		if (month==0) {
			return 1;
		}else {
			return month/3+1;
		}

	}

	public static CharSequence[] GetYearItems() {//456461


		Date date = DateTimeTool.GetNow();
		int year = DateTimeTool.GetYear(date);
		int count=year-2013+1;
		CharSequence[] result = new CharSequence[count];
		for (int i = 0; i < result.length; i++) {
			result[i] = String.format("%d年", year - i);
		}
		// new AsyncLoad().c
		return result;
	}

//	public static CharSequence[] GetCumulativeItems() {
//
//		Date date = DateTimeTool.GetNow();
//
//		CharSequence[] result = new CharSequence[date.getDay() - 1];
//		int month = DateTimeTool.GetMonth(date);
//		int day = DateTimeTool.GetDay(date);
//		for (int i = 0; i < day; i++) {
//			result[i] = String.format("%d月%d日", month, day - i);
//
//		}
//		return result;
//	}

	public static Map<String, List<String>> GetBig() {

		Map<String, List<String>> mapBig = new HashMap<String, List<String>>();
		List<String> listBig = new ArrayList<String>();

		Date date = DateTimeTool.GetNow();
		int year = DateTimeTool.GetYear(date);
		int month = DateTimeTool.GetMonth(date);

		listBig.add("" + month);
		listBig.add("" + 1);
		mapBig.put("" + year, listBig);

		return mapBig;
	}

	public static Map<String, List<String>> GetSmall() {

		Map<String, List<String>> mapSmall = new HashMap<String, List<String>>();
		List<String> listSmall = new ArrayList<String>();

		Date date = DateTimeTool.GetNow();
		int year = DateTimeTool.GetYear(date);
		int month = DateTimeTool.GetMonth(date);

		if (month != 12) {
			listSmall.add("" + 12);
			listSmall.add("" + (month + 1));
			mapSmall.put("" + (year - 1), listSmall);
		}else {
			listSmall.add("" + month);
			listSmall.add("" + month);
			mapSmall.put("" + year, listSmall);
		}
		return mapSmall;
	}

	public static int getNowYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
		String yearString = sdf.format(new Date());
		return Integer.valueOf(yearString);
	}

	public static int getNowMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("M", Locale.getDefault());
		String yearString = sdf.format(new Date());
		return Integer.valueOf(yearString);
	}

	public static int getNowHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("H", Locale.getDefault());
		String yearString = sdf.format(new Date());
		return Integer.valueOf(yearString);
	}
	public static int getNowDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
		String yearString = sdf.format(new Date());
		return Integer.valueOf(yearString);
	}

	public static String getNowDateTimer() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月  H时",
				Locale.getDefault());
		String yearString = sdf.format(new Date());
		return yearString;
	}


	/**
	 * 当前时间 yyyy-M-d
	 */
	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d",
				Locale.getDefault());
		String nowDate = sdf.format(new Date());
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


	/**
	 * 将字符串转为时间  再将时间转为固定格式   返回特定字符串
	 * @param str            需要转化的字符串
	 * @param dateFormatStr  当前字符串的时间格式  如：yyyy-MM-dd HH:mm:ss
	 * @param formatStr      需要转化成的时间格式  如：MM_dd
	 * @return
	 */
	public static String stringToData(String str,String dateFormatStr,String formatStr){
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

	public static int   GetWeekOfYear2(Date date){
		Calendar cal =  Calendar.getInstance();
		//cal.setFirstDayOfWeek(Calendar.MONDAY);


//		Log.e("hjo", "一周的开始是 ："+Calendar.DAY_OF_WEEK);
		int day_of_week = cal.get(Calendar. DAY_OF_WEEK) - 1;
		if (day_of_week == 0 ) {
			day_of_week = 7 ;
		}
		cal.add(Calendar.DATE , -day_of_week + 1 );


		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取年份列表  不包含当前年份
	 * @return
	 */
	public static CharSequence[] getYearItems() {

		Date date = DateTimeTool.GetNow();
		int year = DateTimeTool.GetYear(date)-1;
		Log.e("hjo", "year"+year);
		int count=year-2013+1;
		CharSequence[] result = new CharSequence[count];
		for (int i = 0; i < result.length; i++) {
			result[i] = String.format("%d年", year - i);
		}
		// new AsyncLoad().c
		return result;
	}

	//将含有中文的年份处理 返回一个年份
	public String getIntYear(String year){

		return year.replaceAll("[\u4e00-\u9fa5]", "");
	}

	public CharSequence[] getMonthForYear(String year){

		Calendar calendar=Calendar.getInstance();
		int nowyear=calendar.get(Calendar.YEAR);
		int month;
		if (nowyear==Integer.valueOf(year)) {
			month=calendar.get(Calendar.MONTH)+1;
		}else {
			month=12;
		}
		CharSequence[] result=new CharSequence[month];
		for (int i = 0; i < result.length; i++) {
			result[i] = String.format("%d月", month);
			month--;
		}
		return result;
	}

	public CharSequence[] getYear_Month_Day(String date){
		CharSequence[] result=new CharSequence[3];
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		Date date2=new Date();
		try {
			date2=sdf.parse(date);
			result[0]=new SimpleDateFormat("yyyy").format(date2);
			result[1]=new SimpleDateFormat("M").format(date2);
			result[2]=new SimpleDateFormat("d").format(date2);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result ;
	}



}
