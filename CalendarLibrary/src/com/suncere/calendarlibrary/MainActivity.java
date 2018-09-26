package com.suncere.calendarlibrary;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.suncere.lib.CalendarDay;
import com.suncere.lib.CalendarMode;
import com.suncere.lib.MaterialCalendarView;
import com.suncere.lib.OnDateSelectedListener;
import com.suncere.lib.OnMonthChangedListener;
import com.suncere.lib.format.TitleFormatter;
import com.suncere.lib.v4.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {

	MaterialCalendarView materialCalendarView;
	TitleFormatter mFormatter;
	Calendar mCalendar;
	int Year;
	int Month;
	int Day;
	private CharSequence[] mWeekDay={"日","一","二","三","四","五","六"};
	List<CalendarDay> mlist;
	List<Integer> mlistclors;
	protected int[] colors;//条带颜色数组
	Button button;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCalendar=Calendar.getInstance();

		Year=mCalendar.get(Calendar.YEAR);
		Month=mCalendar.get(Calendar.MONTH);
		Day=mCalendar.get(Calendar.DAY_OF_MONTH);
		//	        Log.e("hjo", "dangqian日期为："+Year+"年"+(Month+1)+"月"+Day);

		materialCalendarView=(MaterialCalendarView)findViewById(R.id.calendarView);
		materialCalendarView.state().edit()
				.setFirstDayOfWeek(Calendar.SUNDAY)
				.setMinimumDate(CalendarDay.from(Year-1, Month, 1))
				.setMaximumDate(CalendarDay.from(Year, Month, Day))
				.setCalendarDisplayMode(CalendarMode.MONTHS)
				.commit();
		materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);//可选多个日期

		//	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//
		mlist=new ArrayList<CalendarDay>();
		mlistclors=new ArrayList<Integer>();

		colors=new int[]{Color.rgb(97,179,16),
				Color.rgb(223,209,6),
				Color.rgb(237,158,5),
				Color.rgb(212,10,26),
				Color.rgb(173,11,174),
				Color.rgb(99,6,17),
				Color.rgb(109,105,105)};

		for (int i = 1; i < 10; i++) {
			mlistclors.add(colors[i%5]);
			mlist.add(CalendarDay.from(2016, 11, i+1)) ;
		}
		//
		materialCalendarView.setSelectDatas(mlist,mlistclors);
		materialCalendarView.setSelectedDate(mCalendar);

		//	        Log.e("hjo", "日期有："+mlist.toString());
		//	        materialCalendarView.setSelectedDate(date);

		materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_DECORATED_DISABLED);//SHOW_DECORATED_DISABLED 在当前月份不显示其他月份的日期
		materialCalendarView.setSelectionColor(Color.parseColor("#678abc"));//设置选中的日期背景色
		materialCalendarView.setArrowColor(Color.parseColor("#678abc"));//设置头部左和右箭头的颜色
		materialCalendarView.setWeekDayLabels(mWeekDay);
		materialCalendarView.setTitleTextColor(Color.parseColor("#678abc"));
		mFormatter=new TitleFormatter() {

			@Override
			public CharSequence format(CalendarDay day) {
				// TODO Auto-generated method stub
				return day.getYear()+"年"+(day.getMonth()+1)+"月";

			}
		};

		materialCalendarView.setTitleFormatter(mFormatter);
		materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
			@Override
			public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
				Log.e("hjo", "hhhhhh选择的日期为："+date.getYear()+"年"+(date.getMonth()+1)+"月"+date.getDay());
			}
		});

		materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {

			@Override
			public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
				// TODO Auto-generated method stub
				if (date.getMonth()==10) {
					mlistclors.clear();
					mlist.clear();
					for (int i = 10; i < 20; i++) {

						mlistclors.add(colors[i%5]);
						mlist.add(CalendarDay.from(2016, 11, i+1)) ;

					}

					materialCalendarView.setSelectDatas(mlist,mlistclors);
					materialCalendarView.setSelectedDate(mCalendar);
				}
			}
		});



		button=(Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				materialCalendarView.setSelectMonthPage(8);
			}
		});
	}


}
