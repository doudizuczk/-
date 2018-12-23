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
		 Calendar currentTime = Calendar.getInstance();//ǰһ��ʱ��
		 Calendar compareTime = Calendar.getInstance();//��һ��ʱ��
		System.out.println(date+" ����ִ�п�ʼ");
		try {
			List<TranSact> tranList=aisTranService.queryAllTran();
			String today=simpl.format(date);
			currentTime.setTime(simpl.parse(today));//��ǰʱ��
			for(TranSact tranSact:tranList) {
				compareTime.setTime(simpl.parse(tranSact.getTranEtime()));//�ײ͵���ʱ��
				if(currentTime.compareTo(compareTime)>0) {
					boolean reslut=aisTranService.updateTranSactState(tranSact);
					if(reslut) {	
						System.out.println("�����û�"+tranSact.getTranId()+"״̬�޸ĳɹ�");
					}else {
						System.out.println("δ��ѯ�������û�");
					}
				}
			}
			System.out.println(date+" �½��û������ж�����ִ�����");
		}catch(Exception e) {
			e.printStackTrace();
		}
	} 
}
