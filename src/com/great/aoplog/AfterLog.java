package com.great.aoplog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface AfterLog {
	 /** Ҫִ�еĲ�������  ���磺���Ӳ��� **/  
    public String operationType() default "";  
     
    /** Ҫִ�еľ������  ���磺����û� **/  
    public String operationName() default "";
    
    public String abc() default "";
}
