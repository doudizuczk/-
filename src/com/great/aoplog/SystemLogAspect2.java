package com.great.aoplog;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.great.bean.Admin;
import com.great.bean.Log;
import com.great.service.ILogService;

/**
 * @author zx
 * @desc 切点类
 */

@Aspect
@Component
public class SystemLogAspect2 {
	@Autowired
	@Qualifier("logServiceImpl")
	private ILogService logService;
	


	@Pointcut("@annotation(com.great.aoplog.AfterLog)")
	public void controllerAspect() {
	}

	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint 切点
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("loggingAdmin");
		String name = admin.getName();
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = null;
		String operationType = "";
		String operationName = "";
		try {
			targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						operationType = method.getAnnotation(AfterLog.class).operationType();
						operationName = method.getAnnotation(AfterLog.class).operationName();
						break;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(operationType + operationName + name);
		String logInfo=operationType+":"+operationName;
		Date logTime=new Date();
		Log log=new Log();
		log.setLogInfo(logInfo);
		log.setadminId(admin.getAdminId());
		//logService.addLog(log);
	}

}
