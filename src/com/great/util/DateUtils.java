package com.great.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	//获取本周开始的日期
	 @SuppressWarnings("unused")
	    public static Date getBeginDayOfWeek() {
	         Date date = new Date();
	         if (date == null) {
	             return null;
	         }
	         Calendar cal = Calendar.getInstance();
	         cal.setTime(date);
	         int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
	         if (dayofweek == 1) {
	             dayofweek += 7;
	         }
	         cal.add(Calendar.DATE, 2 - dayofweek);
	         return cal.getTime();
	     }
	 
	 //获取本周结束的日期
	 public static Date getEndDayOfWeek(){
         Calendar cal = Calendar.getInstance();
         cal.setTime(getBeginDayOfWeek());
         cal.add(Calendar.DAY_OF_WEEK, 6);
         Date weekEndSta = cal.getTime();
         return weekEndSta;
     }
	 
	//获取今年是哪一年
     public static Integer getNowYear() {
             Date date = new Date();
            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
            gc.setTime(date);
            return Integer.valueOf(gc.get(1));
        }
     //获取本月是哪一月
     public static int getNowMonth() {
             Date date = new Date();
            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
            gc.setTime(date);
            return gc.get(2) + 1;
        }
	 
	//获取本月的开始时间
     public static Date getBeginDayOfMonth() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(getNowYear(), getNowMonth() - 1, 1);
            return calendar.getTime();
        }
    //获取本月的结束时间
     public static Date getEndDayOfMonth() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(getNowYear(), getNowMonth() - 1, 1);
            int day = calendar.getActualMaximum(5);
            calendar.set(getNowYear(), getNowMonth() - 1, day);
            return calendar.getTime();
        }
     
   //获取本月的开始时间
     public static Date getBeginDayOfSixMonth() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(getNowYear(), getNowMonth() - 6, 1);
            return calendar.getTime();
        }
     
}
