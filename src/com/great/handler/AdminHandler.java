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

@Controller // ��ע�͵ĺ����ǽ��������ó�Ϊ������ύ����������
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
	
	@AfterLog(operationType="��¼����",operationName="����Ա��¼")
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
	
	//CZK-�˵����adminlistҳ����ת
	@RequestMapping(value = "/adminList.action")
	//ModelAndView��һ��������������ajax����
	public ModelAndView adminList(HttpServletRequest request, Admin admin
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> roleList = roleService.queryAllRoleList();//��ɫ�б�
		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(2);//����Ա״̬�б�
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("roleList", roleList);
		dates.put("StatePack", StatePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/admin_list.jsp");
		return mav;
	}
	//CZK�������ҹ���Ա
	@RequestMapping(value = "/queryAdminList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryAdminList(HttpServletRequest request,@RequestBody Admin admin
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
//		Map<String, Object>  ad = admin;//����ajax���ص�ֵ
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		List<Map<String,Object>> adminList = adminService.conditionQueryAdminList(admin);//����Ա�б�
		int curPage=page.getPageNum();//��ǰҳ��
		int totalPage=page.getPages();//��ҳ��
		int totalNum=(int) page.getTotal();//�ܼ�¼��
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("adminList", adminList);
//		dates.put("queryAdmin", admin);//��ѯ���������ѯ�ı���
		PageInfo pageInfo=new PageInfo(curPage, totalPage, totalNum,dates);//��ҳ��Ϣ��
		return pageInfo;
	}

	
	
	

}