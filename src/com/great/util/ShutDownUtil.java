package com.great.util;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class ShutDownUtil {
	private static ThreadPoolTaskScheduler thread;
	 @Resource(name = "thread")
	 public static void shutDown() {
		 thread=new ThreadPoolTaskScheduler();
		 thread.shutdown();
		 while(thread.getActiveCount()>0) {
			 try {
				Thread.sleep(2000); 
			 }catch(InterruptedException e) {
				 e.printStackTrace();
			 }
		 }
		 System.out.println("定时任务执行完毕");
	 }
}
