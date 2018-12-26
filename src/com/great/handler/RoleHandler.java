package com.great.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
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
import com.great.bean.Menu;
import com.great.bean.Role;
import com.great.service.IRoleService;

@Controller
@RequestMapping("/roleHandler")
public class RoleHandler {
	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;
	//获取角色列表
	@RequestMapping("/roleList.action")
	public ModelAndView queryAllMenu(HttpServletRequest request,Role role,@RequestParam(value = "pageNum", required = true, defaultValue = "1")int pageNum,
			@RequestParam(value = "roleState", required = true, defaultValue = "0")int roleState) {
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 5);
		List<Map<String,Object>> roleList=roleService.queryRole(role);
		Integer allNum=page.getPages();//总页数
		int roleCount=(int)page.getTotal();
		model.addObject("roleList",roleList);
		model.addObject("pageNum", pageNum);
		model.addObject("allNum", allNum);
		model.addObject("roleName", role);
		model.addObject("roleCount", roleCount);
		model.addObject("roleState", roleState);
		model.setViewName("forward:/backstage/role.jsp");
		return model;
	}
	//启用
	@RequestMapping(value="/startRole.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String startRole(int roleId) {
		int result=roleService.startRole(roleId);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//禁用
	@RequestMapping(value="/stopRole.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String stopRole(int roleId) {
		System.out.println(roleId);
		int result=roleService.stopRole(roleId);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//新增角色
	@RequestMapping(value="createRole.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String createRole(@Param("roleName") String roleName) {
		int result=roleService.createRole(roleName);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
}
