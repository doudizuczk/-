package com.great.handler;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Car;
import com.great.bean.Menu;
import com.great.bean.PageInfo;
import com.great.bean.Parm;
import com.great.bean.Role;
import com.great.bean.Rule;
import com.great.service.ICarService;
import com.great.service.IMenuService;
import com.great.service.IParmService;
import com.great.service.IRuleService;
import com.great.service.impl.CarLocationServiceImpl;
import com.great.service.impl.CarServiceImpl;
import com.mysql.fabric.xmlrpc.base.Data;

/*
 * ����������� @linanping
 * */
@Controller()
@RequestMapping("/aliPayHandler")
public class AliPayHandler {

	static {// ��ʼ������ ��ȻsignVerified����֤ʧ��
		Configs.init("zfbinfo.properties");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "pay", method = RequestMethod.POST)
	public void alipay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.info("֧���������첽֪ͨ��");s
		System.out.println("֧���������첽֪ͨ��");
		String message = "success";
		Map<String, String> params = new HashMap<String, String>();
		// ȡ�����в�����Ϊ����֤ǩ��
		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			params.put(parameterName, request.getParameter(parameterName));
		}

		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "UTF-8");
		} catch (AlipayApiException e) {
			e.printStackTrace();
			message = "failed";
		}
		if (signVerified) {
			// LogUtil.info("��֤ǩ���ɹ���");
			System.out.println("��֤ǩ���ɹ���");
			// �������е�appid�������appid����ͬ����Ϊ�쳣֪ͨ
			if (!Configs.getAppid().equals(params.get("app_id"))) {
				// LogUtil.info("�븶��ʱ��appid��ͬ����Ϊ�쳣֪ͨ��Ӧ���ԣ�");
				System.out.println("�븶��ʱ��appid��ͬ����Ϊ�쳣֪ͨ��Ӧ���ԣ�");
				message = "failed";
			} else {
				String outtradeno = params.get("out_trade_no");
				// LogUtil.info(outtradeno + "�Ŷ����ص�֪ͨ��");
				System.out.println(outtradeno + "�Ŷ����ص�֪ͨ��");
				// �����ݿ��в��Ҷ����Ŷ�Ӧ�Ķ������������������ݿ��еĽ��Աȣ����Բ��ϣ�ҲΪ�쳣֪ͨ
				String status = params.get("trade_status");
				if (status.equals("WAIT_BUYER_PAY")) { // ���״̬�����ڵȴ��û�����
					
				} else if (status.equals("TRADE_CLOSED")) { // ���״̬��δ����׳�ʱ�رգ���֧����ɺ�ȫ���˿�
					
				} else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { // ���״̬���Ѿ�֧���ɹ�
					// �ɹ� ����״̬
				} else {
					// weixinpayBack.updateAccOrder(outtradeno);
				}
				// LogUtil.info(outtradeno + "������״̬�Ѿ��޸�Ϊ" + status);
				System.out.println(outtradeno + "������״̬�Ѿ��޸�Ϊ" + status);
			}
		} else { // �����֤ǩ��û��ͨ��
			message = "failed";
//		LogUtil.info("��֤ǩ��ʧ�ܣ�");
			System.out.println("��֤ǩ��ʧ�ܣ�");
		}
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(message.getBytes());
		out.flush();
		out.close();
	}

}
