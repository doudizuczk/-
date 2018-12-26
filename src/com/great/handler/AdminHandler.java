package com.great.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.great.aoplog.AfterLog;
import com.great.aoplog.Log;
import com.great.bean.Admin;
import com.great.bean.Menu;
import com.great.bean.PageInfo;
import com.great.service.IAdminService;
import com.great.service.IParmService;
import com.great.service.IRoleService;

@Controller // 此注释的含义是将该类设置成为浏览器提交的上来的类
@RequestMapping("/admin")
public class AdminHandler {
	@Autowired
	@Qualifier("adminServiceImpl")
	private IAdminService adminService;
	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;
	
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	
	@AfterLog(operationType="登录操作",operationName="管理员登录")
	@RequestMapping(value = "/login.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String adminLogin(HttpServletRequest request, Admin admin, String code) {
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		String keyCode = (String) session.getAttribute("keyCode");
		Admin temp = adminService.queryAdmin(admin);
		//if (keyCode.toLowerCase().equals(code.toLowerCase())) {
			if (temp != null) {
				session.setAttribute("loggingAdmin", temp);
				return "1";
			} else {
				return "0";
			}
		//}else {
		//	return "3";
		//}
	}
	
	//CZK-菜单点击adminlist页面跳转
	@RequestMapping(value = "/adminList.action")
	//ModelAndView是一个容器，不可用ajax返回
	public ModelAndView adminList(HttpServletRequest request, Admin admin
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> roleList = roleService.queryAllRoleList();//角色列表
		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(2);//管理员状态列表
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("roleList", roleList);
		dates.put("StatePack", StatePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/admin_list.jsp");
		return mav;
	}
	//CZK条件查找管理员
	@RequestMapping(value = "/queryAdminList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryAdminList(HttpServletRequest request,@RequestBody Admin admin
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
//		Map<String, Object>  ad = admin;//接收ajax传回的值
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		List<Map<String,Object>> adminList = adminService.conditionQueryAdminList(admin);//管理员列表
		int curPage=page.getPageNum();//当前页数
		int totalPage=page.getPages();//总页数
		int totalNum=(int) page.getTotal();//总记录数
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("adminList", adminList);
//		dates.put("queryAdmin", admin);//查询条件回填查询文本框
		PageInfo pageInfo=new PageInfo(curPage, totalPage, totalNum,dates);//分页信息类
		return pageInfo;
	}

	
	
	

}