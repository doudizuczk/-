package com.great.aoplog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ԫע�⣺Ԫע����ָע���ע��,����  @Retention @Target @Document @Inherited���֡�
 * @Retention: ����ע��ı�������
 * @Retention(RetentionPolicy.SOURCE)    //ע���������Դ���У���class�ֽ����ļ��в�����
 * @Retention(RetentionPolicy.CLASS)     // Ĭ�ϵı������ԣ�ע�����class�ֽ����ļ��д��ڣ�������ʱ�޷���ã�
 * @Retention(RetentionPolicy.RUNTIME)   // ע�����class�ֽ����ļ��д��ڣ�������ʱ����ͨ�������ȡ��
 * @Target������ע�������Ŀ��
 * @Target(ElementType.TYPE)             //�ӿڡ��ࡢö��
	@Target(ElementType.FIELD)           //������ݳ�Ա
	@Target(ElementType.METHOD)          //����
	@Target(ElementType.PARAMETER)       //��������
	@Target(ElementType.CONSTRUCTOR)     //���캯��
	@Target(ElementType.LOCAL_VARIABLE)  //�����ڲ��ľֲ�����
	@Target(ElementType.ANNOTATION_TYPE) //ע��
	@Target(ElementType.PACKAGE)         //��   
	�����ϵ�Դ�����֪��������elementType�����ж����һ��ע�����Ϊ��ģ������ģ��ֶεĵȵ�
 * @Document��˵����ע�⽫��������javadoc��
 * @Inherited��˵��������Լ̳и����еĸ�ע��
 */


@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface Log {

    /** Ҫִ�еĲ�������  ���磺���Ӳ��� **/  
    public String operationType() default "";  
     
    /** Ҫִ�еľ������  ���磺����û� **/  
    public String operationName() default "";
    
    public String abc() default "";
}
