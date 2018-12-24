package com.great.handler;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.great.util.Sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

/** SSE 服务器发送事件
 */
@Controller
public class SSEController {
	volatile public static String gCarId = null;
	volatile public static String Name = null;
	//入口显示屏反向
	@RequestMapping(value="/goIn.action",method=RequestMethod.GET,produces = "text/event-stream;charset=UTF-8")
	public void  push(HttpServletResponse response){
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("utf-8");
		while (true) {
	        Random r = new Random();
	        try {
	        Thread.sleep(3000);
	        PrintWriter pw=response.getWriter();
	        System.out.println("name1"+Name);
	        if(Name!=null) {
	        	pw.write("data:" + Name + "\n\n");
	        	
	        }
	        pw.flush();
	        if(Name!=null) {
	        	Name=null;
	        }
	        if(pw.checkError()){
	            System.out.println("客户端断开连接");
	            return;
	        }
	        } catch (Exception e) {
	         e.printStackTrace();
	       }
	     }
	}
	
//-------------------------------------------------------------------------------
//	@RequestMapping(value="/push.action",method=RequestMethod.GET,produces = "text/event-stream;charset=UTF-8")
//	public void  push(HttpServletResponse response){
//		response.setContentType("text/event-stream");
//		response.setCharacterEncoding("utf-8");
//		while (true) {
//	        Random r = new Random();
//	        try {
//	        Thread.sleep(3000);
//	        PrintWriter pw=response.getWriter();
//	        System.out.println("name1"+Name);
//	        if(Name!=null) {
//	        	pw.write("data:" + Name+r.nextInt() + "\n\n");
//	        }
//	        pw.flush();
//	        if(Name!=null) {
//	        	Name=null;
//	        }
//	        if(pw.checkError()){
//	            System.out.println("客户端断开连接");
//	            return;
//	        }
//	        } catch (Exception e) {
//	         e.printStackTrace();
//	       }
//	     }
//	}
//-------------------------------------------------------------------------------	
//	    public @ResponseBody String push(HttpServletResponse response){
//	         Random r = new Random();
//	         try {
//	                 Thread.sleep(5000);
//	         } catch (InterruptedException e) {
//	                 e.printStackTrace();
//	         }
//	         System.out.println("111111");
//	         return "data:Testing 1,2,3" + "wwwwwwwwwwww"+"\n\n";
//	         return "data:Testing 1,2,3" + r.nextInt() +"\n\n";
//	    }
//-------------------------------------------------------------------------------
	   //入口显示动态
//	    @RequestMapping(value="/push2.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
//	    public @ResponseBody String push2(@RequestParam String name){
//		   Name=name;
//		  return "1";
//	    }
	  //入口显示动态：识别图片
		@RequestMapping(value ="/updatePhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody String updatePhoto(MultipartFile img){ 
		      System.out.println("开始上传1");
		      String name =null;
		      try {
				JSONObject res =Sample.getCarId(img.getBytes());
				if(res!=null) {
					name = res.getJSONObject("words_result").getString("number");
					Name=name;
				}else {
					System.out.println("无法识别");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      System.out.println(name);
		      
		      return name;
		} 
	
}