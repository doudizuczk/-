package com.great.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
//登录拦截类
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("loggingAdmin")!=null) {
			return true;	//执行目标action的方法
		}
		
		PrintWriter out = response.getWriter();
	    out.println("<html>");    
	    out.println("<script>");    
	    out.println("window.open ('"+request.getContextPath()+"/backstage/login.jsp','_top')");    
	    out.println("</script>");    
	    out.println("</html>");  
		return false;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("afterCompletion");
	}
	
}
