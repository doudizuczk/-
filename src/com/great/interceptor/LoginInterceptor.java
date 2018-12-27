package com.great.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
//��¼������
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("loggingAdmin")!=null&&session.getAttribute("loginOwer")==null) {
			return true;	//ִ��Ŀ��action�ķ���
		}else if(session.getAttribute("loginOwer")!=null&&session.getAttribute("loggingAdmin")==null){
			return true;
		}else if(session.getAttribute("loginOwer")==null){
			PrintWriter out = response.getWriter();
			out.println("<html>");    
			out.println("<script>");    
			out.println("window.open ('"+request.getContextPath()+"/frontstage/user_login.jsp','_top')");    
			out.println("</script>");    
			out.println("</html>");  
			return false;
		}else if(session.getAttribute("loggingAdmin")==null) {
			PrintWriter out = response.getWriter();
			out.println("<html>");    
			out.println("<script>");    
			out.println("window.open ('"+request.getContextPath()+"/backstage/backlogin.jsp','_top')");    
			out.println("</script>");    
			out.println("</html>");  
			return false;
		}else {
			return false;
		}
		

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("afterCompletion");
	}
	
}
