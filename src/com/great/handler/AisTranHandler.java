package com.great.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.great.bean.TranSact;
import com.great.service.IAisTranService;
import com.great.util.DateUtils;
import com.great.util.ShutDownUtil;

@Controller
@EnableScheduling
@Lazy(false) 
@RequestMapping("/aisTran")
public class AisTranHandler {
	@Autowired
	@Qualifier("aisServiceImpl")
	private IAisTranService aisTranService;
	
	
	 @Scheduled(cron ="0 0/1 * ? * *" )//"0 53 21 * * ? *"     "0 15 10 ? * *"   "0 0 0 * * ?"  0 15 * ? * *  "0 5 23 * * ?"
	public void beginAisTranSact() {
		 SimpleDateFormat simpl=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 Date date=new Date();
		 Calendar currentTime = Calendar.getInstance();//前一个时间
		 Calendar compareTime = Calendar.getInstance();//后一个时间
		System.out.println(date+" 任务执行开始");
		try {
			List<TranSact> tranList=aisTranService.queryAllTran();
			String today=simpl.format(date);
			currentTime.setTime(simpl.parse(today));//当前时间
			for(TranSact tranSact:tranList) {
				compareTime.setTime(simpl.parse(tranSact.getTranEtime()));//套餐到期时间
				if(currentTime.compareTo(compareTime)>0) {
					boolean reslut=aisTranService.updateTranSactState(tranSact);
					if(reslut) {	
						System.out.println("过期用户"+tranSact.getTranId()+"状态修改成功");
					}else {
						System.out.println("未查询到过期用户");
					}
				}
			}
			System.out.println(date+" 月缴用户到期判定任务执行完毕");
		}catch(Exception e) {
			e.printStackTrace();
		}
	} 
}
