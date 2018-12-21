package com.great.handler;

import java.text.SimpleDateFormat;
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
	
	@Scheduled(cron = "0 5 23 * * ?")//"0 53 21 * * ? *"     "0 15 10 ? * *"   "0 0 0 * * ?"
	public void beginAisTranSact() {
		Date date=new Date();
		System.out.println(date+" �½��û������ж�����ִ��...");
		try {
			List<TranSact> tranList=aisTranService.queryAllTran();
			SimpleDateFormat simpl=new SimpleDateFormat("YYYY-MM-dd");
			String today=simpl.format(date);
			for(TranSact tranSact:tranList) {
				if(today.equals(tranSact.getTranEtime())) {
					int reslut=aisTranService.updateTranSactState(tranSact.getTranId());
					System.out.println("�����û�"+tranSact.getTranId()+"״̬�޸ĳɹ�");
				}
			}
			System.out.println(date+" �½��û������ж�����ִ�����");
		}catch(Exception e) {
			e.printStackTrace();
		}
	} 
}
