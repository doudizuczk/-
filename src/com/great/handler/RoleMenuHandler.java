package com.great.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.Role;
import com.great.service.IRoleMenuService;
import com.great.service.IRoleService;

@Controller
@RequestMapping("/roleMenuHandler")
public class RoleMenuHandler {
	@Autowired
	@Qualifier("roleMenuServiceImpl")
	private IRoleMenuService roleMenuService;
	
	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;
	//根据角色id查询对应菜单
	@RequestMapping(value="/queryAll.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody List<Map<String,Object>> queryAll(int roleId){
		List<Map<String,Object>> allList=roleMenuService.queryAll(roleId);
		
		return allList;
	}
	//进入权限页面，查询角色
	@RequestMapping(value="/queryRole.action")
	public ModelAndView queryRole(){
		ModelAndView model=new ModelAndView();
		Role role=new Role();
		List<Map<String,Object>> roleList=roleService.queryRole(role);
		model.addObject("roleList", roleList);
		model.setViewName("forward:/backstage/rolemenu.jsp");
		return model;
	}
}
