package com.suncere.lib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.suncere.calendarlibrary.R;
import com.suncere.lib.MaterialCalendarView.ShowOtherDates;
import com.suncere.lib.format.DayFormatter;
import com.suncere.lib.format.WeekDayFormatter;
import com.suncere.lib.v4.NonNull;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
abstract class CalendarPagerView extends ViewGroup implements View.OnClickListener {

    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 6;
    protected static final int DAY_NAMES_ROW = 1;
    private static final Calendar tempWorkingCalendar = CalendarUtils.getInstance();
    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<WeekDayView>();
    private final ArrayList<DecoratorResult> decoratorResults = new ArrayList<DecoratorResult>();
    @ShowOtherDates
    protected int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;
    private MaterialCalendarView mcv;
    private CalendarDay firstViewDay;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;
    private int firstDayOfWeek;
    List<Integer>mlistColors=new ArrayList<Integer>();

    private final Collection<DayView> dayViews = new ArrayList<DayView>();

    public CalendarPagerView(@NonNull MaterialCalendarView view,
                             CalendarDay firstViewDay,
                             int firstDayOfWeek) {
        super(view.getContext());
        this.mcv = view;
        this.firstViewDay = firstViewDay;
        this.firstDayOfWeek = firstDayOfWeek;

        setClipChildren(false);
        setClipToPadding(false);

        buildWeekDays(resetAndGetWorkingCalendar());
        buildDayViews(dayViews, resetAndGetWorkingCalendar());
    }

    private void buildWeekDays(Calendar calendar) {
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            WeekDayView weekDayView = new WeekDayView(getContext(), CalendarUtils.getDayOfWeek(calendar));
//            weekDayView.setTextColor(Color.parseColor("#678abc"));
            weekDayViews.add(weekDayView);
            addView(weekDayView);
            calendar.add(DATE, 1);
        }
    }

    @SuppressLint("NewApi")
	protected void addDayView(Collection<DayView> dayViews, Calendar calendar) {
        CalendarDay day = CalendarDay.from(calendar);
        DayView dayView = new DayView(getContext(), day);
//        dayView.setBackgroundColor(Color.parseColor("#678abc"));
        dayView.setOnClickListener(this);
        dayViews.add(dayView);
        addView(dayView, new LayoutParams());

        calendar.add(DATE, 1);
    }

    protected Calendar resetAndGetWorkingCalendar() {
        getFirstViewDay().copyTo(tempWorkingCalendar);
        //noinspection ResourceType
        tempWorkingCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int delta = getFirstDayOfWeek() - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = MaterialCalendarView.showOtherMonths(showOtherDates) ? delta >= 0 : delta > 0;
        if (removeRow) {
            delta -= DEFAULT_DAYS_IN_WEEK;
        }
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    protected int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    protected abstract void buildDayViews(Collection<DayView> dayViews, Calendar calendar);

    protected abstract boolean isDayEnabled(CalendarDay day);

    void setDayViewDecorators(List<DecoratorResult> results) {
        this.decoratorResults.clear();
        if (results != null) {
            this.decoratorResults.addAll(results);
        }
        invalidateDecorators();
    }

    public void setWeekDayTextAppearance(int taId ) {//设置星期的显示样式
        for (WeekDayView weekDayView : weekDayViews) {
            weekDayView.setTextAppearance(getContext(), taId);

        }
    }

    public void setDateTextAppearance(int taId) {//设置日期的显示颜色
    	int a=0;
        for (DayView dayView : dayViews) {
            dayView.setTextAppearance(getContext(), taId);
//            if (a%2==0) {
//            	 dayView.setBackgroundColor(Color.RED);
//			}else{
//				dayView.setBackgroundColor(Color.parseColor("#678abc"));
//			}
           
           a++;
        }
//        Log.e("hjo", "总共画了几个Date"+a);
    
    }

    public void setShowOtherDates(@ShowOtherDates int showFlags) {
        this.showOtherDates = showFlags;
        updateUi();
    }

    public void setSelectionEnabled(boolean selectionEnabled) {
        for (DayView dayView : dayViews) {
            dayView.setOnClickListener(selectionEnabled ? this : null);
            dayView.setClickable(selectionEnabled);
        }
    }

//    //自己写的拓展函数
//   
//    public void setSelectionColors(List<Integer>listColors){
//    	int a=0;
//    	for (DayView dayView : dayViews) {
//    		 dayView.setSelectionColor(listColors.get(a));
//    		 a++;
//		}
//    }
    protected int[] colors;//条带颜色数组
    public void setSelectionColor(int color) {//,List<Integer>listColors
//    	Log.e("hjo", "执行了  + setSelectionColor：");
    	colors=new int[]{Color.rgb(97,179,16),
    	        Color.rgb(223,209,6),
    	        Color.rgb(237,158,5),
    	        Color.rgb(212,10,26),
    	        Color.rgb(173,11,174),
    	        Color.rgb(99,6,17),
    	        Color.rgb(109,105,105)};
    	int a=0;
        for (DayView dayView : dayViews) {
        	
            dayView.setSelectionColor(color);
        	
//        	Log.e("hjo", "这些带颜色值的dayTet都有哪些："+dayView.getText()+"是否被选择了"+dayView.isChecked());
//        	 dayView.setSelectionColor(colors[a%5]);
        	 
        	 if (dayView.isChecked()) {//这里没有进入
//        		 Log.e("hjo", "这些带颜色值的dayTet都有哪些："+dayView.getText()+"是否被选择了"+dayView.isChecked());
        		 dayView.setSelectionColor(Color.RED);
			}
        	a++;
            
        }
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        for (WeekDayView dayView : weekDayViews) {
            dayView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        for (DayView dayView : dayViews) {
            dayView.setDayFormatter(formatter);
        }
    }

    public void setMinimumDate(CalendarDay minDate) {
        this.minDate = minDate;
        updateUi();
    }

    public void setMaximumDate(CalendarDay maxDate) {
        this.maxDate = maxDate;
        updateUi();
    }

    public void setSelectDateColors( List<Integer>listColors){
    	mlistColors.clear();
    	mlistColors.addAll(listColors);
    }
    
    public void setSelectedDates(Collection<CalendarDay> dates) {//找到被选中的日期 并设置背景色

//    	int i=0;
//    	 for (DayView dayView : dayViews) {
//    		 CalendarDay day = dayView.getDate();
//             dayView.setChecked(dates != null && dates.contains(day));
//             if (dayView.isChecked()) {
//				i++;
//			}
//    	 }
//    	 
//    	 Log.e("hjo", "i的值为："+i);
//    	 Log.e("hjo", "mlistColors.size()的值为："+mlistColors.size());
    	int a=0;
        for (DayView dayView : dayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setChecked(dates != null && dates.contains(day));

            if (dayView.isChecked()) {
            	if(mlistColors!=null && mlistColors.size()>0){//2017.03.29 在修改城市摄影时使用了  添加了mlistColors为null的判断   目的是为了能单个选择
            		dayView.setSelectionColor(mlistColors.get(a)); 
            	}else{
            		dayView.setSelectionColor(Color.parseColor("#678abc"));
            	
            	}           	
            	a++;
			}
        }
        
        
//        for (DayView dayView : dayViews) {
//        	 Log.e("hjo", "在dayViews里有几个是被选中状态的dayView="+dayView.getText()+"   状态+"+dayView.isChecked());
//		}
        postInvalidate();
    }

    protected void updateUi() {
        for (DayView dayView : dayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setupSelection(
                    showOtherDates, day.isInRange(minDate, maxDate), isDayEnabled(day));
        }
        postInvalidate();
    }

    protected void invalidateDecorators() {
        final DayViewFacade facadeAccumulator = new DayViewFacade();
        for (DayView dayView : dayViews) {
            facadeAccumulator.reset();
            for (DecoratorResult result : decoratorResults) {
                if (result.decorator.shouldDecorate(dayView.getDate())) {
                    result.result.applyTo(facadeAccumulator);
                }
            }
            dayView.applyFacade(facadeAccumulator);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof DayView) {
            final DayView dayView = (DayView) v;
            mcv.onDateClicked(dayView);
        }
    }

    /*
     * Custom ViewGroup Code
     */

    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //We expect to be somewhere inside a MaterialCalendarView, which should measure EXACTLY
        if (specHeightMode == MeasureSpec.UNSPECIFIED || specWidthMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalStateException("CalendarPagerView should never be left to decide it's size");
        }

        //The spec width should be a correct multiple
        final int measureTileWidth = specWidthSize / DEFAULT_DAYS_IN_WEEK;
        final int measureTileHeight = specHeightSize / getRows();

        //Just use the spec sizes
        setMeasuredDimension(specWidthSize, specHeightSize);

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileWidth,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileHeight,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    /**
     * Return the number of rows to display per page
     * @return
     */
    protected abstract int getRows();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        final int parentLeft = 0;

        int childTop = 0;
        int childLeft = parentLeft;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childLeft += width;

            //We should warp every so many children
            if (i % DEFAULT_DAYS_IN_WEEK == (DEFAULT_DAYS_IN_WEEK - 1)) {
                childLeft = parentLeft;
                childTop += height;
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams();
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams();
    }


    @Override
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(CalendarPagerView.class.getName());
    }

    
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressLint("NewApi")
	@Override
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CalendarPagerView.class.getName());
    }

    protected CalendarDay getFirstViewDay() {
        return firstViewDay;
    }

    /**
     * Simple layout params class for MonthView, since every child is the same size
     */
    protected static class LayoutParams extends MarginLayoutParams {

        /**
         * {@inheritDoc}
         */
        public LayoutParams() {
            super(WRAP_CONTENT, WRAP_CONTENT);
        }
    }
}
