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
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.great.bean.Admin;

/**
 * @author zx
 * @desc �е���
 */

@Aspect
@Component
public class SystemLogAspect {

	// ע��Service���ڰ���־�������ݿ�
	// @Resource //��������resourceע��
	// private SystemLogService systemLogService;

	// �����zxtestҪ��log4j.properties�������һ�£�����д�����ļ���
	// private static Logger logger = Logger.getLogger("zxtest");

	// Controller���е�
//	@Pointcut("execution(* org.great.aoplog..*.*(..))")
	@Pointcut("@annotation(com.great.aoplog.Log)")
	public void controllerAspect() {
	}

	/**
	 * ǰ��֪ͨ ��������Controller���¼�û��Ĳ���
	 * 
	 * @param joinPoint �е�
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("loggingAdmin");
		String name = admin.getAccount();
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
						operationType = method.getAnnotation(Log.class).operationType();
						operationName = method.getAnnotation(Log.class).operationName();
						break;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(operationType + operationName + name);
	}

	/**
	 * ����֪ͨ ��������Controller���¼�û��Ĳ���
	 * 
	 * @param joinPoint �е�
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("loggingAdmin");
		String name = admin.getAccount();
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
	}

}
