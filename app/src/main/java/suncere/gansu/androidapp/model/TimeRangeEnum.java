package suncere.gansu.androidapp.model;

public enum TimeRangeEnum {
	Week(1),
	Month(2),
	Season(3),
	Year(4),
	Cumulative(5);
	
	TimeRangeEnum(int value)
	{
		this.value=value;
	}	
	
	public int Value()
	{
		return value;
	}
	
	private int value;
	
	public static TimeRangeEnum ValueOf(int value)
	{
		switch(value)
		{
		case 1:return Week;
		case 2:return Month;
		case 3:return Season;
		case 4:return Year;
		case 5:return Cumulative;

		}
		return null;
	}
}
