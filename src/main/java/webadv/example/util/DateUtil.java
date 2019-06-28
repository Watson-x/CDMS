package webadv.example.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	private static final String thisdatatime = "2019-5-1";  //计算周次的起始时间
	static String[] weakdays={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	
	//获取现在的相对周次和星期几
	public  String getRelativeWeakAndWeakday() {
		java.util.Date date  = new java.util.Date();
		String da = dateToString(date);
		int weak = getDifferWeak(thisdatatime,da);
		int weakday = getWeakday(da);
		return "第"+weak+"周"+ "\t"+weakdays[weakday];
	}
	//获取这一周的周次
	public int getThisWeak() {
		java.util.Date date  = new java.util.Date();
		String da = dateToString(date);
		int weak = getDifferWeak(thisdatatime,da);
		return weak;
	}
	//获取现在的相对周次
	public int getRelativeWeak() {
		java.util.Date date  = new java.util.Date();
		String da = dateToString(date);
		int weak = getDifferWeak(thisdatatime,da);
		return weak;
	}
	//获取现在的相对周次
	public int getRelativeWeak(String date) {
		int weak = getDifferWeak(thisdatatime,date);
		return weak;
	}
	//日期转换成字符串
	public static String dateToString(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		return s;
	}
	
	//获取日期在这一年的周次
	public static int getWeakInThisYear(String date) {
		java.sql.Date dates = java.sql.Date.valueOf(date+""); 
		Calendar c = Calendar.getInstance();
		c.setTime(dates);
		int WEEK_OF_YEAR = c.get(Calendar.WEEK_OF_YEAR); // 一年中的第几周, 减去前面N周
		return WEEK_OF_YEAR;
	}
	//获取日期在这一周星期几
	public static int getWeakday(String date) {
		java.sql.Date dates = java.sql.Date.valueOf(date+""); 
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(dates);
		int DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK)-1; // 本周的星期几
		return DAY_OF_WEEK;
	}
	
	//获取相对周次
	public static int getDifferWeak(String date1,String date2) {
		int a = getWeakInThisYear(date2);
		int b = getWeakInThisYear(date1);
		//System.out.println("a:"+a+"---b:"+b);
		return a - b + 1;
	}
	
	/*
	 * public static void main(String[] args) { String s = "2019-12-31";
	 * System.out.println(getWeakday(s)); }
	 */
}