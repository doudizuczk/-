package com.great.aoplog;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
	private static Logger logger = Logger.getLogger("zxtest");

	// Controller���е�
//	@Pointcut("execution(* org.great.aoplog..*.*(..))")
	@Pointcut("@annotation(com.aoplog.Log)")
	public void controllerAspect() {
	}

	/**
	 * ǰ��֪ͨ ��������Controller���¼�û��Ĳ���
	 * 
	 * @param joinPoint
	 *            �е�
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("==========ִ��controllerǰ��֪ͨ===============");

		logger.info("before " + joinPoint);
	}

	// ����controller����֪ͨ,ʹ���ڷ���aspect()��ע��������
	@Around("controllerAspect()")
	public void around(JoinPoint joinPoint) {
		System.out.println("==========��ʼִ��controller����֪ͨ===============");
		long start = System.currentTimeMillis();

		String methodName = joinPoint.getSignature().getName();

		try {
			((ProceedingJoinPoint) joinPoint).proceed();
			long end = System.currentTimeMillis();
			logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
			System.out.println("==========����ִ��controller����֪ͨ===============");
		} catch (Throwable e) {
			System.out.println("����֪ͨ�е��쳣--------------------------------" + methodName + "-------" + e.getMessage());
			long end = System.currentTimeMillis();
			logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
		}
	}

	/**
	 * ����֪ͨ ��������Controller���¼�û��Ĳ���
	 * 
	 * @param joinPoint
	 *            �е�
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) throws Throwable {

		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest();
		 * HttpSession session = request.getSession();
		 */
		// ��ȡsession�е��û�
		// User user = (User) session.getAttribute("user");
		// �����IP
		// String ip = request.getRemoteAddr();
		Admin user = new Admin();
		user.setAdminId(1);
		user.setName("����");
		String ip = "127.0.0.1";
		try {

			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operationType = "";
			String operationName = "";
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
			// *========����̨���=========*//
			System.out.println("=====controller����֪ͨ��ʼ=====");
			System.out.println("���󷽷�:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")
					+ "." + operationType);
			System.out.println("��������:" + operationName);
			System.out.println("������:" + user.getName());
			System.out.println("����IP:" + ip);
			// *========���ݿ���־=========*//
			SystemLog log = new SystemLog();
			log.setId(UUID.randomUUID().toString());
			log.setDescription(operationName);
			log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")
					+ "." + operationType);
			log.setLogType((long) 0);
			log.setRequestIp(ip);
			log.setExceptioncode(null);
			log.setExceptionDetail(null);
			log.setParams(null);
			log.setCreateBy(user.getName());
			log.setCreateDate(new Date());
			// �������ݿ�
			// systemLogService.insert(log);
			System.out.println("=====controller����֪ͨ����=====");
		} catch (Exception e) {
			// ��¼�����쳣��־
			logger.warning("==����֪ͨ�쳣==");
			logger.warning("�쳣��Ϣ:{}------" + e.getMessage());

			throw e;
		}
	}

	// ���ú��÷���֪ͨ,ʹ���ڷ���aspect()��ע��������
	// @AfterReturning("controllerAspect()")
	// public void afterReturn(JoinPoint joinPoint){
	// System.out.println("=====ִ��controller���÷���֪ͨ=====----");
	// if(logger.isInfoEnabled()){
	// logger.info("afterReturn " + joinPoint);
	// }
	// }

	/**
	 * �쳣֪ͨ �������ؼ�¼�쳣��־
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest();
		 * HttpSession session = request.getSession(); //��ȡsession�е��û� User user
		 * = (User) session.getAttribute(WebConstants.CURRENT_USER); //��ȡ����ip
		 * String ip = request.getRemoteAddr();
		 */
		// ��ȡ�û����󷽷��Ĳ��������л�ΪJSON��ʽ�ַ���
		System.out.println("�쳣֪ͨ��ʼ------------------------------------------");
		Admin user = new Admin();
		user.setAdminId(1);
		user.setName("����");
		String ip = "127.0.0.1";

		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				// params += JsonUtil.getJsonStr(joinPoint.getArgs()[i]) + ";";
				params += joinPoint.getArgs()[i] + ";";
			}
		}
		try {

			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operationType = "";
			String operationName = "";
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
			/* ========����̨���========= */
			System.out.println("=====�쳣֪ͨ��ʼ=====");
			System.out.println("�쳣����:" + e.getClass().getName());
			System.out.println("�쳣��Ϣ:" + e.getMessage());
			System.out.println("�쳣����:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")
					+ "." + operationType);
			System.out.println("��������:" + operationName);
			System.out.println("������:" + user.getName());
			System.out.println("����IP:" + ip);
			System.out.println("�������:" + params);
			/* ==========���ݿ���־========= */
			SystemLog log = new SystemLog();
			log.setId(UUID.randomUUID().toString());
			log.setDescription(operationName);
			log.setExceptioncode(e.getClass().getName());
			log.setLogType((long) 1);
			log.setExceptionDetail(e.getMessage());
			log.setMethod(
					(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			log.setParams(params);
			log.setCreateBy(user.getName());
			log.setCreateDate(new Date());
			log.setRequestIp(ip);
			// �������ݿ�
			// systemLogService.insert(log);
			System.out.println("=====�쳣֪ͨ����=====");
		} catch (Exception ex) {
			// ��¼�����쳣��־
			logger.warning("==�쳣֪ͨ�쳣==");
			logger.warning("�쳣��Ϣ:{}------" + ex.getMessage());
		}
		/* ==========��¼�����쳣��־========== */
		// logger.error("�쳣����:{}�쳣����:{}�쳣��Ϣ:{}����:{}-----"+joinPoint.getTarget().getClass().getName()
		// + joinPoint.getSignature().getName(), e.getClass().getName(),
		// e.getMessage(), params);

	}

}