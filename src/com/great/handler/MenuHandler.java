package com.great.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Menu;
import com.great.service.IMenuService;


@Controller()
@RequestMapping("/menuHandler")
public class MenuHandler {
	@Autowired
	@Qualifier("menuServiceImpl")
	private IMenuService menuService;
	
	@RequestMapping("/menuList.action")
	public ModelAndView queryAllMenu(HttpServletRequest request,Integer pageNum,Integer searchNum) {
		ModelAndView model=new ModelAndView();
		//Page<Object> page=PageHelper.startPage(pageNum, searchNum);
		List<Map<String,Object>> menuList=menuService.queryAllMenu();
		model.addObject("menuList",menuList);
		model.setViewName("forward:/backstage/menu.jsp");
		return model;
	}
}
