package com.great.handler;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.great.bean.TranSact;
import com.great.service.IAisTranService;
import com.great.util.DateUtils;

@Controller
@RequestMapping("/aisTran")
public class AisTranHandler {
	@Autowired
	@Qualifier("aisServiceImpl")
	private IAisTranService aisTranService;
	
	@Scheduled(cron = "0 0 0 * * ?")//"0 15 16 * * ? *"
	public void beginAisTranSact() {
		//List<TranSact> tranList=aisTranService.queryAllTran();
		//SimpleDateFormat simpl=new SimpleDateFormat("YYYY-MM-DD");
		//DateUtils date=new DateUtils();
		//String today=simpl.format(date.getBeginDayOfWeek());
		System.out.println("时间到了");
	}
}
