package com.great.handler;

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
import com.great.service.IMenuService;


@Controller()
@RequestMapping("/menuHandler")
public class MenuHandler {
	@Autowired
	@Qualifier("menuServiceImpl")
	private IMenuService menuService;
	//����˵��б�
	@RequestMapping("/menuList.action")
	public ModelAndView queryAllMenu(HttpServletRequest request,Menu menu,@RequestParam(value = "pageNum", required = true, defaultValue = "1")int pageNum) {
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 5);
		List<Map<String,Object>> menuList=menuService.queryAllMenu(menu);
		Integer allNum=page.getPages();//��ҳ��
		int menuCount=(int)page.getTotal();
		model.addObject("menuList",menuList);
		model.addObject("pageNum", pageNum);
		model.addObject("allNum", allNum);
		model.addObject("menuName", menu);
		model.addObject("menuCount", menuCount);
		model.setViewName("forward:/backstage/menu.jsp");
		return model;
	}
	//��ҳ����
//	@RequestMapping(value="/pageMenuList.action",method = RequestMethod.GET)
//	public ModelAndView pageMenuList(HttpServletRequest request,Integer pageNum,String adminName) {
//		ModelAndView model=new ModelAndView();
//		Page<Object> page=PageHelper.startPage(pageNum, 5);
//		List<Map<String,Object>> menuList=menuService.queryAllMenu(adminName);
//		Integer nowNum=page.getPageNum();//��ǰҳ��
//		Integer allNum=page.getPages();//��ҳ��
//		model.addObject("menuList",menuList);
//		model.addObject("pageNum", nowNum);
//		model.addObject("allNum", allNum);
//		model.setViewName("forward:/backstage/menu.jsp");
//		return model;
//	}
	//�����˵�֮ǰ����ȡһ���˵�
	@RequestMapping(value="/createMenu.action",method = RequestMethod.GET)
	public ModelAndView getFirstMenu() {
		ModelAndView model=new ModelAndView();
		List<Map<String,Object>> firstMenuList=menuService.queryFirstMenu();
		model.addObject("firstMenuList", firstMenuList);
		model.setViewName("forward:/backstage/createmenu.jsp");
		return model;
	}
	//�����˵�
	@AfterLog(operationType="����Ա����",operationName="�����˵�")
	@RequestMapping(value="/addMenu.action",method = RequestMethod.POST)
	public @ResponseBody String createMenu(@RequestBody Menu menu) {
			menu.setMenuSeq(100);
			Integer result=menuService.createNewMenu(menu);
			if(result>0) {
				return "1";
			}else {
				return "0";
			}
	}
	//����˵�������
	@AfterLog(operationType="����Ա����",operationName="���ò˵�")
	@RequestMapping(value="/controlMenu.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String startMenu(HttpServletRequest request, Integer menuId) {
		Integer result=menuService.manageMenu(menuId);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//���ò˵�
	@AfterLog(operationType="����Ա����",operationName="���ò˵�")
	@RequestMapping(value="/stopMenu.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String stopMenu(HttpServletRequest request, Integer menuId) {
		Integer result=menuService.stopMenu(menuId);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//���˵�,�ύ1
	@RequestMapping(value="/getLeftMenu.action",produces = "application/json;charset=utf-8")
	public  ModelAndView getLeftMenu(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("loggingAdmin");
		List<Map<String,Object>> menuList=menuService.queryLeftMenu(admin.getRoleId());
		if(menuList.size()>0) {
			mav.addObject("menuList", menuList);
			mav.setViewName("forward:/backstage/backmain.jsp");
		}
		return mav;
	}
}
