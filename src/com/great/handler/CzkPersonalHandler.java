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
import com.great.service.IParmService;
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
	@Qualifier("adminServiceImpl")
	private IAdminService adminService;
	@Autowired
	@Qualifier("czkPerServiceImpl")
	private ICzkPersonalService czkService;
	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	
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
	//管理员查存
	@RequestMapping(value ="/addQueryAdminExist.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody int addQueryAdminExist(HttpServletRequest request) {
		String account=request.getParameter("account");
		Admin admin = new Admin();
		admin.setAccount(account);
		List<Map<String,Object>> adminlist = adminService.addQueryAdminExist(admin);
		if(adminlist.size()>0) {
			return 1;
		}
		return 0;
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
	//修改管理员插入数据
	@RequestMapping(value = "/addUpdateAdmin.action", method = RequestMethod.POST)
	public @ResponseBody int addUpdateAdmin(HttpServletRequest request,@RequestBody Admin admin) {
		int a = czkService.addUpdateAdmin(admin);
		return a;
	}
	//修改车主插入数据
	@RequestMapping(value = "/addUpdateUser.action", method = RequestMethod.POST)
	public @ResponseBody int addUpdateUser(HttpServletRequest request,@RequestBody Ower ower) {
		int a = czkService.addupdateUser(ower);
		return a;
	}
	//新增管理员插入数据
	@RequestMapping(value = "/addAdmin.action", method = RequestMethod.POST)
	public @ResponseBody int addAdminList(HttpServletRequest request,@RequestBody Admin admin) {
		int a = czkService.addAdmin(admin);
		return a;
	}
	//前台点击修改管理员,跳转updateAdmin.jsp
	@RequestMapping(value = "/updateAdmin.action", method = RequestMethod.GET, produces = "application/json;charset=utf-8")//CZK修改管理员
	public ModelAndView updateAdmin(HttpServletRequest request) {//pageCurr不能为空，并初始化
		Integer adminId=Integer.parseInt(request.getParameter("adminId"));
		Admin admin = new Admin();
		admin.setAdminId(adminId);
		List<Map<String,Object>> adminMap = adminService.conditionQueryAdminList(admin);//管理员列表
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> roleList = roleService.queryAllRoleList();//角色列表
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("roleList", roleList);
		dates.put("adminMap", adminMap.get(0));
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/updateAdmin.jsp");
		return mav;
	}
	
	//CZK-菜单点击车主管理页面跳转--carUser_list.jsp
	@RequestMapping(value = "/userList.action")
	//ModelAndView是一个容器，不可用ajax返回
	public ModelAndView adminList(HttpServletRequest request
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(7);//车主状态列表
		ModelAndView mav = new ModelAndView();
		Map<String,Object> dates = new HashMap<String,Object>();
		dates.put("StatePack", StatePack);
		mav.addObject("dates", dates);
		mav.setViewName("forward:/backstage/carUser_list.jsp");
		return mav;
	}
	
	//CZK条件AJAX模糊查找车主
	@RequestMapping(value = "/queryOwerList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryAdminList(HttpServletRequest request,@RequestBody Ower ower
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
//		Map<String, Object>  ad = admin;//接收ajax传回的值
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		List<Map<String,Object>> owerList = czkService.conditionQueryCarUserList(ower); 
		int curPage=page.getPageNum();//当前页数
		int totalPage=page.getPages();//总页数
		int totalNum=(int) page.getTotal();//总记录数
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("adminList", owerList);
		PageInfo pageInfo=new PageInfo(curPage, totalPage, totalNum,dates);//分页信息类
		return pageInfo;
	}
	//修改车主状态
	@RequestMapping(value ="/updateUserState.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody int updateUserState(HttpServletRequest request ) {
		Integer userId=Integer.parseInt(request.getParameter("userId"));
		Integer state=Integer.parseInt(request.getParameter("state"));
		Ower user = new Ower();
		if(state==1) {
			user.setOwerState(2);
		}
		if(state==2) {
			user.setOwerState(1);
		}
		user.setOwerId(userId);
		int a = czkService.updateUserState(user);
		
		return a;
	}
	//前台点击修改管理员,跳转updateUser.jsp
	@RequestMapping(value = "/updateUser.action", method = RequestMethod.GET, produces = "application/json;charset=utf-8")//CZK修改管理员
	public ModelAndView updateUser(HttpServletRequest request) {
		Integer userId=Integer.parseInt(request.getParameter("userId"));
		Ower user = new Ower();
		user.setOwerId(userId);
		List<Map<String,Object>> userMapList = czkService.conditionQueryCarUserList(user);
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("userMap", userMapList.get(0));
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/updateUser.jsp");
		return mav;
	}

	

}
