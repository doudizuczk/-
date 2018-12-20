package com.great.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Car;
import com.great.bean.Menu;
import com.great.bean.Order;
import com.great.bean.PageInfo;
import com.great.bean.Parm;
import com.great.bean.Role;
import com.great.bean.Rule;
import com.great.service.ICarService;
import com.great.service.IMenuService;
import com.great.service.IOrderService;
import com.great.service.IParmService;
import com.great.service.IRuleService;
import com.great.service.impl.CarLocationServiceImpl;
import com.great.service.impl.CarServiceImpl;
import com.mysql.fabric.xmlrpc.base.Data;

/*
 * ����������� @linanping
 * */
@Controller()
@RequestMapping("/orderHandler")
public class OrderHandler {
	@Autowired
	@Qualifier("orderServiceImpl")
	private IOrderService orderService;
	
	@RequestMapping("/exportOrderExcel.action")
	public ModelAndView exportOrderExcel(HttpServletRequest request, HttpServletResponse response,Order temp) throws IOException {
		//1.��ȡexcelģ��
		String path=request.getServletContext().getRealPath("/storage/order.xls");
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//2.��ȡ���ݿ�����
		Order od=new Order();
		od.setShift(1);
		od.setCreateTime("2018-12-20");
		Order order=orderService.queryOrder(od);
		//3.��ȡ��һ��sheet
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		
		if (sheet != null) {
			//��ȡ��3��
			HSSFRow row3=sheet.getRow(3);
			if (row3==null) {
				row3=sheet.createRow(3);
			}
			//���ð��/����ʱ��
			HSSFCell cell0=row3.getCell(0);
			HSSFCell cell3=row3.getCell(3);
			int shift=order.getShift();
			if (shift==1) {
				cell0.setCellValue("���");
				cell3.setCellValue("8:00--16:00");
			}else if (shift==2) {
				cell0.setCellValue("�а�");
				cell3.setCellValue("16:00-24:00");
			}else {
				cell0.setCellValue("���");
				cell3.setCellValue("00:00-8:00");
			}
			
			//��ȡ��7��
			HSSFRow row7=sheet.getRow(7);
			if (row7==null) {
				row7=sheet.createRow(7);
			}
			//���ý��׽�� ���ֽ𡢿�Ʊ��������Ʊ����������
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
			
			
			// ���Excel�ļ�
			try {
				OutputStream output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; filename=abc.xls");
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
	
	/**
     *���ݵ�ǰrow�У�������index��ǵ�����,����ֵ����
     */
    private void createRowAndCell(Object obj, HSSFRow row, HSSFCell cell, int index) {
        cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }

        if (obj != null)
            cell.setCellValue(obj.toString());
        else 
            cell.setCellValue("");
    }


}
