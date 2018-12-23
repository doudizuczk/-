package com.great.handler;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ucpaas.restDemo.client.AbsRestClient;
import com.ucpaas.restDemo.client.JsonReqClient;

@Controller
@RequestMapping("/CheckCodeHandler")
public class CheckCodeHandler {
	static AbsRestClient InstantiationRestAPI() {
		return new JsonReqClient();
	}
	
	public static void testSendSms(String sid, String token, String appid, String templateid, String param, String mobile, String uid){
		try {
			String result=InstantiationRestAPI().sendSms(sid, token, appid, templateid, param, mobile, uid);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/getMessageCode.action")
	public void getMessageCode(HttpServletRequest request, String owerPhone) {
		HttpSession session=request.getSession();
		Random ran=new Random();
		String result="";
		for (int i=0;i<6;i++){
			result+=ran.nextInt(10);
		}
		session.setAttribute("Code", result);
		/*短信接口参数*/
		String sid = "5a606526894a6685d89975e57ec4cf82";
		String token = "1dd51e9ac9caa8b220716d1106e85af5";
		String appid = "17865251165145129d887d080d8e8336";
		String templateid = "413570";
		String param = result;
		String mobile = owerPhone;
		String uid = "";
		testSendSms(sid, token, appid, templateid, param, mobile, uid);
	}
	//获取到用户填写的验证码,进行验证
	public String checkPhoneCode(HttpServletRequest requset,String owerCode) {
		HttpSession session=requset.getSession();
		String code=(String)session.getAttribute("Code");
		if(code.equals(owerCode)) {
			return "1";
		}else {
			return "0";
		}
	}
}
