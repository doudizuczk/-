package com.great.handler;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucpaas.restDemo.client.AbsRestClient;
import com.ucpaas.restDemo.client.JsonReqClient;

@Controller
@RequestMapping("/CheckCodeHandler")
public class CheckCodeHandler {
	//静态返回方法
	static AbsRestClient InstantiationRestAPI() {
		return new JsonReqClient();
	}
	//发送短信的方法
	public static void testSendSms(String sid, String token, String appid, String templateid, String param, String mobile, String uid){
		try {
			String result=InstantiationRestAPI().sendSms(sid, token, appid, templateid, param, mobile, uid);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取短信的action
	@RequestMapping(value="/getMessageCode.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String getMessageCode(HttpServletRequest request, @RequestParam(value = "owerPhone", required = true, defaultValue = "")String owerPhone) {
		HttpSession session=request.getSession();
		Random ran=new Random();
		String result="";
		for (int i=0;i<6;i++){
			result+=ran.nextInt(10);
		}
		session.setAttribute("Code", result);
		/*短信接口参数
		 * 注册平台获取
		 * */
		String sid = "5a606526894a6685d89975e57ec4cf82";
		String token = "1dd51e9ac9caa8b220716d1106e85af5";
		String appid = "17865251165145129d887d080d8e8336";
		String templateid = "413570";//自定义信息模板ID，在平台上申请
		String param = result;
		String mobile = owerPhone;//用户的电话号码
		String uid = "";
		testSendSms(sid, token, appid, templateid, param, mobile, uid);//调用发送方法
		return "1";
	}
}
