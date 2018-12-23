package com.great.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Admin;
import com.great.bean.Charge;
import com.great.bean.Dock;
import com.great.bean.Order;
import com.great.service.IChargeService;
import com.great.service.IDockService;
import com.great.util.DateUtils;

/*������@lian shengwei
 * �������ڣ�20181216
 * ��֧��ϸ
 * */
@Controller // ��ע�͵ĺ����ǽ��������ó�Ϊ������ύ����������
@RequestMapping("/chargeHander")
public class ChargeHander {
	@Autowired
	@Qualifier("chargeServiceImpl")
	private IChargeService chargeService;
	@Autowired
	@Qualifier("dockServiceImpl")
	private IDockService dockService;
	
	private DateUtils dateUtils;
	
	@RequestMapping("/exportCharge.action")
	public ModelAndView exportCharge(HttpServletRequest request, HttpServletResponse response,int chargeId) throws IOException {
		//1.��ȡexcelģ��
		String path=request.getServletContext().getRealPath("/storage/invoice.xls");
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//2.��ȡ���ݿ�����/����״̬Ϊ�ѿ�Ʊ
		Charge charge=chargeService.queryChargeById(chargeId);
		
		Charge temp=new Charge();
		temp.setChargeId(chargeId);
		temp.setInvoice(1);
		int count=chargeService.updateCharge(temp);
		if (count<=0) {
			return null;
		}
		
		//3.��ȡ��һ��sheet
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		
		if (sheet != null) {
			//��ȡ��3��
			HSSFRow row2=sheet.getRow(2);
			if (row2==null) {
				row2=sheet.createRow(2);
			}
			//������䵥Ԫ��
			HSSFCell cell0=row2.getCell(0);
			HSSFCell cell1=row2.getCell(1);
			HSSFCell cell2=row2.getCell(2);
			HSSFCell cell3=row2.getCell(3);
			HSSFCell cell4=row2.getCell(4);
			
			cell0.setCellValue(charge.getCarId());
			cell1.setCellValue(charge.getCost());
			cell2.setCellValue(charge.getTypeName());
			cell3.setCellValue(charge.getCreateTime());
			cell4.setCellValue(charge.getAdminName());
			
			//��ӱ�ע
			HSSFRow row3=sheet.createRow(3);
			cell3=row3.createCell(3);
			cell3.setCellValue("Ʊ�ݱ�ţ�ZNTC"+charge.getChargeId());
			sheet.addMergedRegion(new CellRangeAddress(3,3,3,4));//�ϲ���Ԫ��
			
			// ���Excel�ļ�
			try {
				OutputStream output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; filename=invoice.xls");
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
	
	//���ͣ���ɷ���Ϣ
	@RequestMapping("/addCharge.action")
	public @ResponseBody String addCharge(HttpServletRequest request,Charge charge) {
		if (charge.getAdminId()==0) {//�˹��ɷ�
			HttpSession session=request.getSession();
			int adminId=((Admin)session.getAttribute("loggingAdmin")).getAdminId();
			charge.setAdminId(adminId);
		}
		
		int seq=chargeService.addCharge(charge);
		if (seq>0) {
			return ""+seq;
		}else {
			return "0";
		}
	}
	
	//��ȡͣ���ɷ���Ϣ
	@RequestMapping("/payment.action")
	public @ResponseBody Dock payment(HttpServletRequest request,String carId) {
		Dock temp=new Dock();
		temp.setCarId(carId);
		temp.setState(1);
		List<Dock> list=dockService.queryDock(temp);
		if (list.size()==1) {
			Dock dock=list.get(0);
			double cost=chargeService.getParkingCost(carId);
			dock.setCost(cost);
			return dock;
			
		}else {
			return null;
		}
	}
	
	// ��ȡ��֧��ϸ�б�
	@RequestMapping("/statisticalChart.action")
	public ModelAndView statisticalChart() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat mot = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat mm = new SimpleDateFormat("MM");
		String halfyearmouths=mm.format(dateUtils.getBeginDayOfMonth());
		String weekBegin = df.format(dateUtils.getBeginDayOfWeek());
		String weekEnd = df.format(dateUtils.getEndDayOfWeek());
		String mouth=mot.format(dateUtils.getBeginDayOfMonth());
		ModelAndView model=new ModelAndView();
		//��ͳ��
		 Map<String, Object> map = new HashMap<String, Object>();  
         map.put("weekBegin",weekBegin);  
         map.put("weekEnd",weekEnd);
		List<Map<String,Object>> mouthList=chargeService.mouthChart(mouth);
		List<Map<String,Object>> selfHelpmouthList=chargeService.querySelfHelpMouthChart(mouth);
		List<Map<String,Object>> labormouthList=chargeService.queryLaborMouthChart(mouth);
		List<Map<String,Object>> rechargemouthList=chargeService.queryRechargeMouthChart(mouth);
		
		List<Map<String,Object>> halfyearList=chargeService.queryAllHalfyearChart();
		List<Map<String,Object>> rechargeHalfyearList=chargeService.queryRechargeHalfyearChart();
		List<Map<String,Object>> laborHalfyearList=chargeService.queryLaborHalfyearChart();
		List<Map<String,Object>> selfHelpHalfyearList=chargeService.querySelfHelpHalfyearChart();
		
		
		List<Map<String,Object>> weekList=chargeService.weekChart(map);
		List<Map<String,Object>> selfHelpWeekList=chargeService.querySelfHelpWeekChart();
		List<Map<String,Object>> laborWeekList=chargeService.queryLaborWeekChart();
		List<Map<String,Object>> rechargeWeekList=chargeService.queryRechargeWeekChart();
		
		model.addObject("rechargeHalfyearList",rechargeHalfyearList);
		model.addObject("laborHalfyearList",laborHalfyearList);
		model.addObject("selfHelpHalfyearList",selfHelpHalfyearList);
		
		model.addObject("selfHelpmouthList",selfHelpmouthList);
		model.addObject("labormouthList",labormouthList);
		model.addObject("rechargemouthList",rechargemouthList);
		
		model.addObject("rechargeWeekList",rechargeWeekList);
		model.addObject("laborWeekList",laborWeekList);
		model.addObject("selfHelpWeekList",selfHelpWeekList);
		model.addObject("weekList",weekList);
		
		model.addObject("mouthList",mouthList);
		model.addObject("halfyearmouths",halfyearmouths);
		model.addObject("halfyearList",halfyearList);
		//��ͳ��
		//����ͳ��
		model.setViewName("forward:/backstage/charge_count.jsp");
		return model;		
	}
	// ��ȡ��֧��ϸ�б�
	@RequestMapping("/chargeList.action")
	public ModelAndView queryAllChargeList(HttpServletRequest request,Integer pageNum) {
		if(pageNum==null) {
			pageNum=1;
		}
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 10);
		List<Map<String,Object>> chargeList=chargeService.queryAllChargeList();
		if(page.getPages()==0) {
			model.addObject("pageNum",0);//��ǰҳ��
		}else {
			model.addObject("pageNum",page.getPageNum());//��ǰҳ��
		}
		model.addObject("pages",page.getPages());//��ҳ����
		model.addObject("chargeList",chargeList);
		model.setViewName("forward:/backstage/charge_list.jsp");
		return model;		
	}
	  //֧��ϸ�б�ҳ�Ͳ�ѯ
		@RequestMapping(value ="/turnPageChargeList.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody List<Map<String,Object>> turnPageChargeList(@RequestParam String carId,String endTime,String starTime,int pageNums,int adminId,int chargeId) {		
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("chargeId",chargeId);
	        map.put("adminId",adminId);
	        map.put("carId",carId);
	        map.put("endTime",endTime);
	        map.put("starTime",starTime);
	        Page<Object> page=PageHelper.startPage(pageNums, 10);
	        List<Map<String,Object>> chargeList=chargeService.turnPageChargeList(map);
	        Map<String, Object> map2 = new HashMap<String, Object>();  
            map2.put("pageNum",page.getPageNum());  
            map2.put("pages", page.getPages());  
            chargeList.add(0, map2);
			return chargeList;
		}
	
	
}
