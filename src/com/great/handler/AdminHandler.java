package com.great.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.great.aoplog.Log;
import com.great.bean.Admin;
import com.great.service.IAdminService;

@Controller // 此注释的含义是将该类设置成为浏览器提交的上来的类
@RequestMapping("/admin")
public class AdminHandler {
	@Autowired
	@Qualifier("adminServiceImpl")
	private IAdminService adminService;
	
	@RequestMapping(value = "/login.action")
	public ModelAndView adminLogin(HttpServletRequest request, Admin admin, String code) {
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		String keyCode = (String) session.getAttribute("keyCode");
		Admin temp = adminService.queryAdmin(admin);
		//if (keyCode.toLowerCase().equals(code.toLowerCase())) {
			if (temp != null) {
				session.setAttribute("loggingAdmin", temp);
				mav.setViewName("forward:/backstage/backmain.jsp");
			} else {
				mav.setViewName("forward:/backstage/backlogin.jsp");
			}
		//}else {
			//mav.setViewName("forward:/backstage/backlogin.jsp");
		//}
		return mav;
	}
	

}
