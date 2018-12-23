package com.great.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
	
	//��ȡ���ܿ�ʼ������
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
	 
	 //��ȡ���ܽ���������
	 public static Date getEndDayOfWeek(){
         Calendar cal = Calendar.getInstance();
         cal.setTime(getBeginDayOfWeek());
         cal.add(Calendar.DAY_OF_WEEK, 6);
         Date weekEndSta = cal.getTime();
         return weekEndSta;
     }
	 
	//��ȡ��������һ��
     public static Integer getNowYear() {
             Date date = new Date();
            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
            gc.setTime(date);
            return Integer.valueOf(gc.get(1));
        }
     //��ȡ��������һ��
     public static int getNowMonth() {
             Date date = new Date();
            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
            gc.setTime(date);
            return gc.get(2) + 1;
        }
	 
	//��ȡ���µĿ�ʼʱ��
     public static Date getBeginDayOfMonth() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(getNowYear(), getNowMonth() - 1, 1);
            return calendar.getTime();
        }
    //��ȡ���µĽ���ʱ��
     public static Date getEndDayOfMonth() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(getNowYear(), getNowMonth() - 1, 1);
            int day = calendar.getActualMaximum(5);
            calendar.set(getNowYear(), getNowMonth() - 1, day);
            return calendar.getTime();
        }
     
   //��ȡ���µĿ�ʼʱ��
     public static Date getBeginDayOfSixMonth() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(getNowYear(), getNowMonth() - 6, 1);
            return calendar.getTime();
        }
     //�������֤�����������
     public static Map<String, Object> getCarInfo15W(String card)
 			throws Exception {
 		Map<String, Object> map = new HashMap<String, Object>();
 		String uyear = "19" + card.substring(8, 10);// ���
 		//System.out.println("���="+uyear);
 		String uyue = card.substring(10, 12);// �·�
 		//System.out.println("�·�="+uyue);
 		String uday=card.substring(12, 14);//��
 		//System.out.println("��="+uday);
 		String usex = card.substring(16, 17);// �û����Ա�
 		String sex;
 		if (Integer.parseInt(usex) % 2 == 0) {
 			sex = "Ů";
 		} else {
 			sex = "��";
 		}
 		Date date = new Date();// �õ���ǰ��ϵͳʱ��
 		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
 		String fyear = format.format(date).substring(0, 4);// ��ǰ���
 		String fyue = format.format(date).substring(5, 7);// �·�
 		String fri=format.format(date).substring(8, 10);
 		int age = 0;
 		if (Integer.parseInt(uyue) <= Integer.parseInt(fyue)) { 
 			// ��ǰ�·ݴ����û�������·ݱ�ʾ�ѹ���
 			if(Integer.parseInt(uday)<=Integer.parseInt(fri)) {
 				age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
 			}else {
 				age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
 			}
 		} else {// ��ǰ�û���û����
 			age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
 		}
 		map.put("sex", sex);
 		map.put("age", age);
 		return map;
 	}
}
