package com.great.handler;

import java.util.List;
import java.util.Map;

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

import com.great.bean.Menu;
import com.great.bean.Role;
import com.great.service.IMenuService;
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
	
	@Autowired
	@Qualifier("menuServiceImpl")
	private IMenuService menuService;
	//根据角色id查询对应菜单
	@RequestMapping(value="/queryAll.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody List<Map<String,Object>> queryAll(int roleId){
		List<Map<String,Object>> roleList=roleMenuService.queryAll(roleId);
		List<Map<String,Object>> menuList=menuService.queryMenu();
		for(int i=0;i<menuList.size();i++) {
			for(int j=0;j<roleList.size();j++) {
				if(menuList.get(i).get("menuId").equals(roleList.get(j).get("menuId"))) {
					menuList.get(i).put("checked", true);
				}
			}
		}
		return menuList;
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
	//修改权限
	@RequestMapping(value="/changeRoleMenu.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String changeRoleMenu(String menuId,int roleId) {
		int result=roleMenuService.deleteRoleMenu(roleId);
		int results=0;
		String[] str=menuId.split(",");
		int[] menuIds=new int[str.length];
		for(int i=0;i<str.length;i++) {
			menuIds[i]=Integer.valueOf(str[i]);
		}
		for(int j=0;j<menuIds.length;j++) {
			results=roleMenuService.addRoleMenu(roleId, menuIds[j]);
		}
		if(results>0) {
			return "1";
		}else {
			return "0";
		}
	}
}
