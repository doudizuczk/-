package com.great.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		Integer pages=1;
		Page<Object> page=PageHelper.startPage(pages, 10);
		List<Map<String,Object>> menuList=menuService.queryAllMenu();
		Integer nowNum=page.getPageNum();//��ǰҳ��
		Integer allNum=page.getPages();//��ҳ��
		model.addObject("menuList",menuList);
		model.addObject("pageNum", nowNum);
		model.addObject("allNum", allNum);
		model.setViewName("forward:/backstage/menu.jsp");
		return model;
	}
	//��ҳ����
	@RequestMapping(value="/pageMenuList.action",method = RequestMethod.GET)
	public ModelAndView pageMenuList(HttpServletRequest request,Integer pageNum) {
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 10);
		List<Map<String,Object>> menuList=menuService.queryAllMenu();
		Integer nowNum=page.getPageNum();//��ǰҳ��
		Integer allNum=page.getPages();//��ҳ��
		model.addObject("menuList",menuList);
		model.addObject("pageNum", nowNum);
		model.addObject("allNum", allNum);
		model.setViewName("forward:/backstage/menu.jsp");
		return model;
	}
	//�����˵�
	@RequestMapping(value="/createMenu.action")
	public ModelAndView createMenu() {
		ModelAndView model=new ModelAndView();
		
		return model;
	}
}
