package com.great.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.TranSact;
import com.great.service.IAisTranService;
import com.great.service.IPayMentService;
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
	
	@Autowired
	@Qualifier("payMentServiceImpl")
	private IPayMentService payMentService; 
	
	// @Scheduled(cron ="0 0/1 * ? * *" )//"0 53 21 * * ? *"     "0 15 10 ? * *"   "0 0 0 * * ?"  0 15 * ? * *  "0 5 23 * * ?"
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
	//����ͳ�ƽ��excel(��ͳ��)
	@RequestMapping(value="/getCountExcel.action")
	public ModelAndView getCountExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model =new ModelAndView();
		//��ȡ��excel�ļ�
		String path=request.getSession().getServletContext().getRealPath("/storage/weekcount.xls");
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//��ȡ���ݿ�����
		List<Map<String,Object>> list1=payMentService.queryWeekCount();
		List<Map<String,Object>> list2=payMentService.queryWeekPayCount();
		List<Map<String,Object>> list3=payMentService.queryWeekMouthPay();
		//��ȡ��Ԫ��
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		if(sheet!=null) {
			HSSFRow row1=sheet.getRow(1);
			HSSFRow row2=sheet.getRow(2);
			HSSFRow row3=sheet.getRow(3);
			if(row1==null) {
				row1=sheet.createRow(1);
			}
			if(row2==null) {
				row2=sheet.createRow(2);
			}
			if(row3==null) {
				row3=sheet.createRow(3);
			}
			//
			for(int i=0;i<list1.size();i++) {
				HSSFCell cell=row1.getCell(i+1);
				Map map=new HashMap();
				map=list1.get(i);
				System.out.println(map.get("counts"));
				cell.setCellValue(""+map.get("VALUE"));
			}
			for(int i=0;i<list2.size();i++ ) {
				HSSFCell cell=row2.getCell(i+1);
				Map map=new HashMap();
				map=list2.get(i);
				cell.setCellValue(""+map.get("VALUE"));
			}
			for(int i=0;i<list3.size();i++) {
				HSSFCell cell=row3.getCell(i+1);
				Map map=new HashMap();
				map=list3.get(i);
				cell.setCellValue(""+map.get("VALUE"));
			}
			try {
				OutputStream output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; filename=weekcount.xls");
				response.setContentType("application/msexcel");
				hssfWorkbook.write(output);
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	//��ͳ��
	@RequestMapping(value="/getCountmouthExcel.action")
	public ModelAndView getCountmouthExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model =new ModelAndView();
		SimpleDateFormat simpl=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat simplmouth=new SimpleDateFormat("MM");
		DateUtils date=new DateUtils();
		String time=simpl.format(date.getBeginDayOfWeek());
		Map maps=new HashMap();
		maps.put("time", time);
		//��ȡ��excel�ļ�
		String path=request.getSession().getServletContext().getRealPath("/storage/mouthcount.xls");
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//��ȡ���ݿ�����
		List<Map<String,Object>> list1=payMentService.queryMouthCountOne(maps);
		List<Map<String,Object>> list2=payMentService.queryMouthCountTwo(maps);
		List<Map<String,Object>> list3=payMentService.queryMouthCountThree(maps);
		//��ȡ��Ԫ��
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		if(sheet!=null) {
			HSSFRow row1=sheet.getRow(1);
			HSSFRow row2=sheet.getRow(2);
			HSSFRow row3=sheet.getRow(3);
			if(row1==null) {
				row1=sheet.createRow(1);
			}
			if(row2==null) {
				row2=sheet.createRow(2);
			}
			if(row3==null) {
				row3=sheet.createRow(3);
			}
			//
			for(int i=0;i<list1.size();i++) {
				HSSFCell cell=row1.getCell(i+1);
				Map map=new HashMap();
				map=list1.get(i);
				cell.setCellValue(""+map.get("COUNT"));
			}
			for(int i=0;i<list2.size();i++ ) {
				HSSFCell cell=row2.getCell(i+1);
				Map map=new HashMap();
				map=list2.get(i);
				cell.setCellValue(""+map.get("COUNT"));
			}
			for(int i=0;i<list3.size();i++) {
				HSSFCell cell=row3.getCell(i+1);
				Map map=new HashMap();
				map=list3.get(i);
				cell.setCellValue(""+map.get("COUNT"));
			}
			try {
				OutputStream output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; filename=mouthcount.xls");
				response.setContentType("application/msexcel");
				hssfWorkbook.write(output);
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	//������ͳ��
	@RequestMapping(value="/getsixMouthExcel.action")
	public ModelAndView getsixMouthExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model =new ModelAndView();
		SimpleDateFormat simpl=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat simplmouth=new SimpleDateFormat("MM");
		DateUtils date=new DateUtils();
		String mouth=simplmouth.format(date.getBeginDayOfWeek());
		//��ȡ��excel�ļ�
		String path=request.getSession().getServletContext().getRealPath("/storage/sixmouthcount.xls");
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//��ȡ���ݿ�����
		List<Map<String,Object>> list1=payMentService.querySixMouthOne();
		List<Map<String,Object>> list2=payMentService.querySixMouthTwo();
		List<Map<String,Object>> list3=payMentService.querySixMouthThree();
		//��ȡ��Ԫ��
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		if(sheet!=null) {
			HSSFRow row=sheet.getRow(0);
			HSSFRow row1=sheet.getRow(1);
			HSSFRow row2=sheet.getRow(2);
			HSSFRow row3=sheet.getRow(3);
			if(row==null) {
				row=sheet.createRow(0);
			}
			if(row1==null) {
				row1=sheet.createRow(1);
			}
			if(row2==null) {
				row2=sheet.createRow(2);
			}
			if(row3==null) {
				row3=sheet.createRow(3);
			}
			//
			for(int i=0;i<list1.size();i++) {
				HSSFCell cell=row1.getCell(i+1);
				HSSFCell cell1=row.getCell(i+1);
				Map map=new HashMap();
				map=list1.get(i);
				int time2=Integer.valueOf(mouth);
				int times=time2-i;
				cell1.setCellValue(times+"��");
				cell.setCellValue(""+map.get("COUNT"));
			}
			for(int i=0;i<list2.size();i++ ) {
				HSSFCell cell=row2.getCell(i+1);
				HSSFCell cell1=row.getCell(i+1);
				Map map=new HashMap();
				map=list2.get(i);
				cell.setCellValue(""+map.get("COUNT"));
			}
			for(int i=0;i<list3.size();i++) {
				HSSFCell cell=row3.getCell(i+1);
				HSSFCell cell1=row.getCell(i+1);
				Map map=new HashMap();
				map=list3.get(i);
				cell.setCellValue(""+map.get("COUNT"));
			}
			try {
				OutputStream output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; filename=sixmouthcount.xls");
				response.setContentType("application/msexcel");
				hssfWorkbook.write(output);
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
