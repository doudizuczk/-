package com.great.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.Pack;
import com.great.bean.TranSact;
import com.great.service.IPackService;
import com.great.service.IParmService;
import com.great.service.ITransactService;

/**
 * �ײͰ���
 * @author Administrator
 */

@Controller// ��ע�͵ĺ����ǽ��������ó�Ϊ������ύ����������
@RequestMapping("/transact")
public class TransactHandler {
	
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	@Autowired
	@Qualifier("packServiceImpl")
	private IPackService packService;
	@Autowired
	@Qualifier("transactServiceImpl")
	private ITransactService transactService;
	
	//CZK-�˵�����ײͰ���ҳ����ת
	@RequestMapping(value = "/pack_transact.action")
	public ModelAndView JumpPackList(HttpServletRequest request ) {
		List<Map<String,Object>> typePack= parmService.IdQueryParmName(9);//�ײ������б�
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("TypePack", typePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/pack_Transact.jsp");
		return mav;
	}
	//czk-����ҳ�ײ����͸ı��¼������ײ��б�
	@RequestMapping(value = "/SelectTypeChange.action")
	public@ResponseBody Map<String,Object> SelectTypeChange(HttpServletRequest request ) {//pageCurr����Ϊ�գ�����ʼ��
		Integer typeId = Integer.parseInt(request.getParameter("packType"));
		Pack pack = new Pack();
		pack.setPackType(typeId);
		List<Map<String,Object>> packList = packService.queryPackList(pack);
		
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packList", packList);
		return dates;
	}
	//�˵�����ײ��˷���ת
	@RequestMapping(value = "/pack_Refund.action")
	public ModelAndView JumpPack_Refund(HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/backstage/pack_Refund.jsp");
		return mav;
	}
	//czk-���복�Ʋ鿴�����ײͰ��������
	@RequestMapping(value = "/CarIdSelectTransact.action")
	public@ResponseBody Map<String,Object> CarIdSelectTransact(HttpServletRequest request ) {
		String carId = request.getParameter("carId");
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//���ݳ��ƺŲ��ײ�
		String money = transactService.refund(carId);
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("tran", tranList.get(0));
		dates.put("money", money);
		return dates;
	}
	
	//czk-����˷Ѱ���
	@RequestMapping(value = "/RefunndTransact.action")
	public@ResponseBody Map<String,Object> RefunndTransact(HttpServletRequest request ) {
		
		String carId = request.getParameter("carId");
		String money = request.getParameter("money");
		Map<String,Object> map = new HashMap<>();
		map.put("carId", carId);
		map.put("money", money);
		Map<String,Object> refundMap = transactService.refundMoney(map);
		
		TranSact tran = new TranSact();
		
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//���ݳ��ƺŲ��ײ�
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("tran", tranList.get(0));
		dates.put("money", money);
		return dates;
	}
	

}
