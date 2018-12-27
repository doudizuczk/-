package com.great.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.aoplog.AfterLog;
import com.great.bean.Car;
import com.great.bean.Charge;
import com.great.bean.Menu;
import com.great.bean.Order;
import com.great.bean.PageInfo;
import com.great.bean.Parm;
import com.great.bean.Role;
import com.great.bean.Rule;
import com.great.service.ICarService;
import com.great.service.IChargeService;
import com.great.service.IMenuService;
import com.great.service.IOrderService;
import com.great.service.IParmService;
import com.great.service.IRuleService;
import com.great.service.impl.CarLocationServiceImpl;
import com.great.service.impl.CarServiceImpl;
import com.mysql.fabric.xmlrpc.base.Data;

/*
 * 车辆处理对象 @linanping
 * */
@Controller()
@EnableScheduling
@Lazy(false)  
@RequestMapping("/orderHandler")
public class OrderHandler {
	@Autowired
	@Qualifier("orderServiceImpl")
	private IOrderService orderService;
	@Autowired
	@Qualifier("chargeServiceImpl")
	private IChargeService chargeService;
	
	//导出结算单
	@AfterLog(operationType="管理员操作",operationName="导出结算")
	@RequestMapping("/exportOrderExcel.action")
	public ModelAndView exportOrderExcel(HttpServletRequest request, HttpServletResponse response,Order temp) throws IOException {
		//1.读取excel模板
//		String path=request.getServletContext().getRealPath("/storage/order.xls");
		String path=request.getSession().getServletContext().getRealPath("/storage/order.xls");
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//2.读取数据库数据
		Order order=orderService.queryOrder(temp);
		if (order==null) {
			ModelAndView mav=new ModelAndView();
			mav.setViewName("redirect:/backstage/export_statement.jsp");
			return mav;
		}
		//3.获取第一个sheet
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		
		if (sheet != null) {
			//获取第3行
			HSSFRow row3=sheet.getRow(3);
			if (row3==null) {
				row3=sheet.createRow(3);
			}
			//设置班次/交接时间
			HSSFCell cell0=row3.getCell(0);
			HSSFCell cell3=row3.getCell(3);
			int shift=order.getShift();
			if (shift==1) {
				cell0.setCellValue("早班");
				cell3.setCellValue("8:00--16:00");
			}else if (shift==2) {
				cell0.setCellValue("中班");
				cell3.setCellValue("16:00-24:00");
			}else {
				cell0.setCellValue("晚班");
				cell3.setCellValue("00:00-8:00");
			}
			
			//获取第7行
			HSSFRow row7=sheet.getRow(7);
			if (row7==null) {
				row7=sheet.createRow(7);
			}
			//设置交易金额 、现金、开票数量、开票金额、交易数量
			cell0=row7.getCell(0);
			HSSFCell cell1=row7.getCell(1);
			HSSFCell cell2=row7.getCell(2);
			cell3=row7.getCell(3);
			HSSFCell cell4=row7.getCell(4);
			
			cell0.setCellValue(order.getAmount());
			cell1.setCellValue(order.getCash());
			cell2.setCellValue(order.getInvCount());
			cell3.setCellValue(order.getInvAmount());
			cell4.setCellValue(order.getCount());
			
			// 输出Excel文件
			try {
				OutputStream output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; filename=order.xls");
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
	
	//自动生成结算单
	@Scheduled(cron = "0 0 0,8,16 * * ?")//每天上午0点，8点，下午16点
//	@Scheduled(fixedRate = 5000)
	public void autoSettlement() {
		Date now=new Date();
		System.out.println(new Date()+" 自动生成结算单任务执行...");
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String hour = new SimpleDateFormat("HH").format(now);
		
		//获取8小时前
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);  
		calendar.add(Calendar.HOUR_OF_DAY, -8);
		Date sDate=calendar.getTime();
		
		Charge charge=new Charge();
		charge.setsTime(sdf.format(sDate));
		charge.seteTime(sdf.format(now));
		
		//查询开票金额 
		charge.setChargeId(1);
		double invAmount=chargeService.queryChargeOrder(charge).doubleValue();
		//查询开票数量
		charge.setChargeId(2);
		int invCount= chargeService.queryChargeOrder(charge).intValue();
		//查询交易金额
		charge.setChargeId(3);
		double amount=chargeService.queryChargeOrder(charge).doubleValue();
		//查询交易数量
		charge.setChargeId(4);
		int count= chargeService.queryChargeOrder(charge).intValue();
		//查询现金金额
		charge.setChargeId(5);
		double cash=chargeService.queryChargeOrder(charge).doubleValue();
		//查询班次
		int shift=0;
		if (hour.equals("16")) {//早班 08:00-16:00
			shift=1;
		}else if (hour.equals("00")){//中班 16:00-24:00
			shift=2;
		}else {//晚班 00:00-08:00
			shift=3;
		}
		Order order=new Order(0, amount, cash, count, invAmount, invCount, shift, "");
		orderService.addOrder(order);
		
		System.out.println(new Date()+" 自动生成结算单任务执行完毕");
	}
	
	//查询结算单列表
	@RequestMapping("/orderList.action")
	public ModelAndView orderList(HttpServletRequest request, Order order,
			@RequestParam(value = "curPage", required = true, defaultValue = "1") int curPage) {
		ModelAndView model = new ModelAndView();
		Page<Object> page = PageHelper.startPage(curPage, 5);// 第curPage页，包含5条
		
		List<Order> orderList=orderService.queryAllOrder(order);
		int totalPage = page.getPages();// 总页数
		int totalNum = (int) page.getTotal();
		Map<String, Object> dates = new HashMap<String, Object>();
		dates.put("orderList", orderList);
		PageInfo pageInfo = new PageInfo(curPage, totalPage, totalNum, dates);

		model.addObject("pageInfo", pageInfo);
		model.setViewName("forward:/backstage/export_statement.jsp");
		return model;
	}
	
}
