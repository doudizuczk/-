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
 * 车辆处理对象 @linanping
 * */
@Controller()
@RequestMapping("/aliPayHandler")
public class AliPayHandler {

	static {// 初始化参数 不然signVerified会验证失败
		Configs.init("zfbinfo.properties");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "pay", method = RequestMethod.POST)
	public void alipay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LogUtil.info("支付宝付款异步通知！");s
		System.out.println("支付宝付款异步通知！");
		String message = "success";
		Map<String, String> params = new HashMap<String, String>();
		// 取出所有参数是为了验证签名
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
			// LogUtil.info("验证签名成功！");
			System.out.println("验证签名成功！");
			// 若参数中的appid和填入的appid不相同，则为异常通知
			if (!Configs.getAppid().equals(params.get("app_id"))) {
				// LogUtil.info("与付款时的appid不同，此为异常通知，应忽略！");
				System.out.println("与付款时的appid不同，此为异常通知，应忽略！");
				message = "failed";
			} else {
				String outtradeno = params.get("out_trade_no");
				// LogUtil.info(outtradeno + "号订单回调通知。");
				System.out.println(outtradeno + "号订单回调通知。");
				// 在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
				String status = params.get("trade_status");
				if (status.equals("WAIT_BUYER_PAY")) { // 如果状态是正在等待用户付款
					
				} else if (status.equals("TRADE_CLOSED")) { // 如果状态是未付款交易超时关闭，或支付完成后全额退款
					
				} else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { // 如果状态是已经支付成功
					// 成功 更新状态
				} else {
					// weixinpayBack.updateAccOrder(outtradeno);
				}
				// LogUtil.info(outtradeno + "订单的状态已经修改为" + status);
				System.out.println(outtradeno + "订单的状态已经修改为" + status);
			}
		} else { // 如果验证签名没有通过
			message = "failed";
//		LogUtil.info("验证签名失败！");
			System.out.println("验证签名失败！");
		}
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(message.getBytes());
		out.flush();
		out.close();
	}

}
