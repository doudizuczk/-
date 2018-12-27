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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.Pack;
import com.great.bean.TranSact;
import com.great.mapper.CarLocationMapper;
import com.great.mapper.CarMapper;
import com.great.mapper.OwerMapper;
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
	@Autowired
	private OwerMapper owerMapper;
	@Autowired
	private CarMapper carMapper;
	@Autowired
	private CarLocationMapper carLocationMapper;
	
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
	public@ResponseBody Map<String,Object> SelectTypeChange(HttpServletRequest request ) {
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
		int rstState = 0;
		String money = "";
		String carId = request.getParameter("carId");
		TranSact tran = new TranSact();
		tran.setTranState(1);
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//���ݳ��ƺŲ��ײ�
		Map<String, Object> dates=new HashMap<String, Object>();
		if(tranList.size()>0) {
			 money = transactService.refund(carId);
			rstState=1;
			dates.put("tran", tranList.get(0));//�����ײͰ���� ������
		}
		dates.put("rstState", rstState);
		dates.put("money", money);//���˽��
		return dates;
	}
	
	//czk-����˷Ѱ���
	@RequestMapping(value = "/RefunndTransact.action")
	public@ResponseBody Map<String,Object> RefunndTransact(HttpServletRequest request ) {
		String carId = request.getParameter("carId");
		String CNY = request.getParameter("money");
		Double money = Double.parseDouble(CNY);
		int refundState = transactService.refundMoney(carId,money);//�����˷��˷ѵķ���������1=����˿�  2=�ֽ��˿�
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//���ݳ��ƺŲ��ײ�
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("tran", tranList.get(0));//���ײ�
		dates.put("money", money);//Ӧ�˽��
		dates.put("refundState", refundState);//�˿ʽ
		return dates;
	}
	//czk-����ײͰ���
	@RequestMapping(value = "/carIdPackTransact.action")
	public@ResponseBody Map<String,Object> packTransact(HttpServletRequest request ) {
		int packId = Integer.parseInt(request.getParameter("packId"));
		String carID = request.getParameter("carAccount");
		
		Map<String,Object> add = transactService.carIdTransactPack(carID, packId);
		
		return add;
	}
	//==========================================================================================================================
	//czk-2-�ײͰ������복�Ʋ鿴�����ײͰ��������
	@RequestMapping(value = "/CarIdSelectPack2.action")
	public@ResponseBody Map<String,Object> CarIdSelectPack(HttpServletRequest request ) {
		int rstState = 0;//�Ƿ����ײ�
		int owerState = 0;//�����Ƿ���û�
		String money = "";
		String carId = request.getParameter("carId");
		TranSact tran = new TranSact();
		tran.setTranState(1);
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//���ݳ��ƺŲ��ײ�
		

		
		List<Map<String,Object>> typePack= parmService.IdQueryParmName(9);//�ײ������б�
		Map<String, Object> dates=new HashMap<String, Object>();
		Map<String,Object> owerMoney= owerMapper.CarQueryOwer(carId);//carID���û����
		if(owerMoney!=null) {
			owerState=1;
			dates.put("owerMoney", owerMoney);
		}
		
		if(tranList.size()>0) {
			 money = transactService.refund(carId);//���Ʋ��ײ����������˷ѽ��
			rstState=1;
			dates.put("tran", tranList.get(0));//�����ײͰ���� ������
		}
		
		dates.put("TypePack", typePack);
		dates.put("rstState", rstState);
		dates.put("owerState", owerState);
		dates.put("money", money);//���˽��
		return dates;
	}
	//czk-����ҳ�ײ����͸ı��¼������ײ��б�
	@RequestMapping(value = "/SelectTypeChange2.action")
	public@ResponseBody Map<String,Object> SelectTypeChange2(HttpServletRequest request ) {//pageCurr����Ϊ�գ�����ʼ��
		Integer typeId = Integer.parseInt(request.getParameter("packType"));
		Pack pack = new Pack();
		pack.setPackType(typeId);
		pack.setPactState(1);
		List<Map<String,Object>> packList = packService.queryPackList(pack);
		List<Map<String, Object>>  carLoc =  carLocationMapper.getVipParkIdList();
		
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packList", packList);
		dates.put("carLoc",carLoc);
		return dates;
	}
	//============================================================================================================================
	//czk-ȷ��֧��������ײ�
	@RequestMapping(value = "/confirmPay.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public@ResponseBody Map<String,Object> confirmPay(HttpServletRequest request,int PackTranPyte,int payType
			,int adminId,int packId,String carId,@RequestParam(value = "carPark", required = true, defaultValue = "0")int carPark) {
		carPark(carId,carPark);//���ĳ�λ״̬����
		List<Map<String,Object>> y = carMapper.selectCarById(carId);
		if(y.size()==0) {
			int t = carMapper.createCarWithNewUser(carId);//���û����복����
		}
		Map<String,Object> map = null;
		if(PackTranPyte==1) {//�½��ײ�
			 map =transactService.addpackTran(carId, packId,payType,adminId,carPark);//payType=֧����ʽ����1�����  2���ֽ�   3��������
		}else if(PackTranPyte==2) {//����
			map=transactService.RenewalPackTran(carId, packId, payType,adminId);
		}else if(PackTranPyte==3) {//����
			map=transactService.changePackTran(carId, packId, payType,adminId,carPark);
		}
		
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("map", map);
		return dates;
	}
	
	//czk-�����ײͰ����ı��ײ��˿��ж�
	@RequestMapping(value = "/payAndRefund.action")
	public@ResponseBody double payAndRefund(HttpServletRequest request ) {
		Integer packId = Integer.parseInt(request.getParameter("packId"));//�ײ�id
		String carId = request.getParameter("carId");//����
		double tranmoney =transactService.payAndRefund(carId, packId);
	
		return tranmoney;
	}
	
	public void carPark(String carId,int carPark  ) {
		if(carPark!=0) {
			//���ĳ�λcarPark״̬ ռ��
			Map<String, Object> map = new HashMap<>();
			map.put("parkState", 2);
			map.put("parkId", carPark);
			int a = carLocationMapper.updateParkStateById(map);
		}
		
	}
	

}
