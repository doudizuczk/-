package com.great.handler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/** SSE 服务器发送事件
 */
@Controller
public class SSEController {
	@RequestMapping(value="/push.action",method=RequestMethod.GET,produces = "text/event-stream;charset=UTF-8")
	    public @ResponseBody String push(){
	         Random r = new Random();
//	         try {
//	                 Thread.sleep(5000);
//	         } catch (InterruptedException e) {
//	                 e.printStackTrace();
//	         }
//	         System.out.println("111111");
//	         return "data:Testing 1,2,3" + "wwwwwwwwwwww"+"\n\n";
	         return "data:Testing 1,2,3" + r.nextInt() +"\n\n";
	    }
	@RequestMapping(value="/push.action",method=RequestMethod.GET)
	public @ResponseBody String push2(){
		
		return "success";
	}
	
}