package com.great.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
     //根据身份证号码算出年龄
     public static Map<String, Object> getCarInfo15W(String card)
 			throws Exception {
 		Map<String, Object> map = new HashMap<String, Object>();
 		String uyear = "19" + card.substring(8, 10);// 年份
 		//System.out.println("年份="+uyear);
 		String uyue = card.substring(10, 12);// 月份
 		//System.out.println("月份="+uyue);
 		String uday=card.substring(12, 14);//日
 		//System.out.println("日="+uday);
 		String usex = card.substring(16, 17);// 用户的性别
 		String sex;
 		if (Integer.parseInt(usex) % 2 == 0) {
 			sex = "女";
 		} else {
 			sex = "男";
 		}
 		Date date = new Date();// 得到当前的系统时间
 		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
 		String fyear = format.format(date).substring(0, 4);// 当前年份
 		String fyue = format.format(date).substring(5, 7);// 月份
 		String fri=format.format(date).substring(8, 10);
 		int age = 0;
 		if (Integer.parseInt(uyue) <= Integer.parseInt(fyue)) { 
 			// 当前月份大于用户出身的月份表示已过生
 			if(Integer.parseInt(uday)<=Integer.parseInt(fri)) {
 				age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
 			}else {
 				age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
 			}
 		} else {// 当前用户还没过生
 			age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
 		}
 		map.put("sex", sex);
 		map.put("age", age);
 		return map;
 	}
}
