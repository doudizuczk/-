package com.great.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Menu;
import com.great.bean.PageInfo;
import com.great.bean.Parm;
import com.great.bean.Role;
import com.great.bean.Rule;
import com.great.service.IMenuService;
import com.great.service.IParmService;
import com.great.service.IRuleService;
import com.mysql.fabric.xmlrpc.base.Data;

/*
 * 车辆处理对象 @linanping
 * */
@Controller()
@RequestMapping("/carHandler")
public class CarHandler {
	
	
//	@RequestMapping("/carLogin.action")
//	public @ResponseBody String carLogin(HttpServletRequest request, String carId) {
//		HttpSession session=request.getSession();
//		
//	}
	
	

}
