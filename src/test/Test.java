package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//获取前一小时的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:00:00");  
		//这里设置你想要的时间
		Date date;
//		date = sdf.parse("08:00");
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());  
		//前面设置的时间-1
		calendar.add(Calendar.HOUR_OF_DAY, -8);  
		date = calendar.getTime();  
		System.out.println(sdf.format(date));
			
		System.out.println("======================================");
		
		
		/*//获取前一天的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		Date date11=new Date();  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date11);  
		calendar.add(Calendar.DAY_OF_MONTH, -1);  
		System.out.println(sdf.format(calendar.getTime()));  
		
		System.out.println("======================================");
		
		SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd");
		String str=fm.format(new Date());
		System.out.println(str);
		
		System.out.println("==========================================");
		Date date =new Date(); 
		//主要是将格式设置为HH:00
		SimpleDateFormat formatter = new SimpleDateFormat( "HH:00:00"); 
		String time = formatter.format(date);
		System.out.println(str+" "+time);
		
		System.out.println("==========================================");
		
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar1=new GregorianCalendar(2018, 12, 17,23,10,0);
		Date date1 = calendar1.getTime();
		System.out.println("date1:"+format.format(date1));
		
		Calendar calendar2=new GregorianCalendar(2018, 12, 17,0,11,0);
		Date date2 = calendar2.getTime();
		System.out.println("date2:"+format.format(date2));
		
		Date date3=new Date();
		System.out.println("date3:"+format.format(date3));
		
		System.out.println();
		
		Long time1=date2.getTime()-date1.getTime();
		System.out.println("date2-date1相隔："+time1);
		
		Long time2=date3.getTime()-date1.getTime();
		System.out.println("now-date1相隔："+time2);
		
		System.out.println("================================");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try
	    {
	      Date d1 = df.parse("2018-12-17 23:27:00");
	      Date d2 = df.parse("2018-12-17 23:23:00");
	      Date d3=new Date();
	      
	      long diff = d1.getTime() - d2.getTime();
	      long diff2 = d3.getTime() - d1.getTime();
	      long days = diff / (1000 * 60 * 60 * 24);
	 
	      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
	      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
	      
	      System.out.println(diff2/1000);
	      
	    }catch (Exception e)
	    {
	    }*/

		
	}

}
