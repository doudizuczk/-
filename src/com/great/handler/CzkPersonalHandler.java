package com.great.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Admin;
import com.great.bean.Ower;
import com.great.bean.PageInfo;
import com.great.service.IAdminService;
import com.great.service.ICzkPersonalService;
import com.great.service.IRoleService;
/**
 *为了避免冲突，单独写一个类试试看
 * @author Administrator
 *
 */


@Controller // 此注释的含义是将该类设置成为浏览器提交的上来的类
@RequestMapping("/czkPer")
public class CzkPersonalHandler {
	@Autowired
	@Qualifier("czkPerServiceImpl")
	private ICzkPersonalService czkService;
	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;
	
	//adminlist.jsp添加管理员跳转
	@RequestMapping(value = "/addAdmin.action")//CZK-adminlist页面点击新增管理员跳转
	//ModelAndView是一个容器，不可用ajax返回
	public ModelAndView adminList(HttpServletRequest request) {//pageCurr不能为空，并初始化
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> roleList = roleService.queryAllRoleList();//角色列表
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("roleList", roleList);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/addAdmin.jsp");
		return mav;
	}

	//修改管理员状态
	@RequestMapping(value ="/updateAdminState.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody int updateAdminState(HttpServletRequest request ) {
		
		Integer adminId=Integer.parseInt(request.getParameter("adminId"));
		Integer state=Integer.parseInt(request.getParameter("state"));
		Admin admin = new Admin();
		if(state==1) {
			admin.setState(2);
		}
		if(state==2) {
			admin.setState(1);
		}
		admin.setAdminId(adminId);
		int a = czkService.updateAdminAtt(admin);
		
		return a;
	}
	//新增管理员
	@RequestMapping(value = "/addAdmin.action", method = RequestMethod.POST)//CZK条件查找管理员
	public @ResponseBody int queryAdminList(HttpServletRequest request,@RequestBody Admin admin
			) {//pageCurr不能为空，并初始化
		int a = czkService.addAdmin(admin);
		
		return a;
	}
	//CZK-菜单点击user_list页面跳转
	@RequestMapping(value = "/userList.action")
	//ModelAndView是一个容器，不可用ajax返回
	public ModelAndView adminList(HttpServletRequest request
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("forward:/backstage/carUser_list.jsp");
		return mav;
	}
	
	//CZK条件查找车主
	@RequestMapping(value = "/queryOwerList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryAdminList(HttpServletRequest request,@RequestBody Ower ower
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
//		Map<String, Object>  ad = admin;//接收ajax传回的值
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		
		List<Map<String,Object>> owerList = czkService.conditionQueryCarUserList(ower);
		
		int curePage=page.getPageNum();//当前页数
		int totalPage=page.getPages();//总页数
		int totalNum=(int) page.getTotal();//总记录数
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("adminList", owerList);
		PageInfo pageInfo=new PageInfo(curePage, totalPage, totalNum,dates);//分页信息类
		return pageInfo;
	}


	

}
