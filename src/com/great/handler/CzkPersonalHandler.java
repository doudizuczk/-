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
 *Ϊ�˱����ͻ������дһ�������Կ�
 * @author Administrator
 *
 */


@Controller // ��ע�͵ĺ����ǽ��������ó�Ϊ������ύ����������
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
	
	//adminlist.jsp��ӹ���Ա��ת
	@RequestMapping(value = "/addAdmin.action")//CZK-adminlistҳ������������Ա��ת
	//ModelAndView��һ��������������ajax����
	public ModelAndView adminList(HttpServletRequest request) {//pageCurr����Ϊ�գ�����ʼ��
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> roleList = roleService.queryAllRoleList();//��ɫ�б�
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("roleList", roleList);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/addAdmin.jsp");
		return mav;
	}
	//����Ա���
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

	//�޸Ĺ���Ա״̬
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
	//�޸Ĺ���Ա��������
	@RequestMapping(value = "/addUpdateAdmin.action", method = RequestMethod.POST)
	public @ResponseBody int addUpdateAdmin(HttpServletRequest request,@RequestBody Admin admin) {
		int a = czkService.addUpdateAdmin(admin);
		return a;
	}
	//�޸ĳ�����������
	@RequestMapping(value = "/addUpdateUser.action", method = RequestMethod.POST)
	public @ResponseBody int addUpdateUser(HttpServletRequest request,@RequestBody Ower ower) {
		int a = czkService.addupdateUser(ower);
		return a;
	}
	//��������Ա��������
	@RequestMapping(value = "/addAdmin.action", method = RequestMethod.POST)
	public @ResponseBody int addAdminList(HttpServletRequest request,@RequestBody Admin admin) {
		int a = czkService.addAdmin(admin);
		return a;
	}
	//ǰ̨����޸Ĺ���Ա,��תupdateAdmin.jsp
	@RequestMapping(value = "/updateAdmin.action", method = RequestMethod.GET, produces = "application/json;charset=utf-8")//CZK�޸Ĺ���Ա
	public ModelAndView updateAdmin(HttpServletRequest request) {//pageCurr����Ϊ�գ�����ʼ��
		Integer adminId=Integer.parseInt(request.getParameter("adminId"));
		Admin admin = new Admin();
		admin.setAdminId(adminId);
		List<Map<String,Object>> adminMap = adminService.conditionQueryAdminList(admin);//����Ա�б�
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> roleList = roleService.queryAllRoleList();//��ɫ�б�
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("roleList", roleList);
		dates.put("adminMap", adminMap.get(0));
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/updateAdmin.jsp");
		return mav;
	}
	
	//CZK-�˵������������ҳ����ת--carUser_list.jsp
	@RequestMapping(value = "/userList.action")
	//ModelAndView��һ��������������ajax����
	public ModelAndView adminList(HttpServletRequest request
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(7);//����״̬�б�
		ModelAndView mav = new ModelAndView();
		Map<String,Object> dates = new HashMap<String,Object>();
		dates.put("StatePack", StatePack);
		mav.addObject("dates", dates);
		mav.setViewName("forward:/backstage/carUser_list.jsp");
		return mav;
	}
	
	//CZK����AJAXģ�����ҳ���
	@RequestMapping(value = "/queryOwerList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryAdminList(HttpServletRequest request,@RequestBody Ower ower
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
//		Map<String, Object>  ad = admin;//����ajax���ص�ֵ
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		List<Map<String,Object>> owerList = czkService.conditionQueryCarUserList(ower); 
		int curPage=page.getPageNum();//��ǰҳ��
		int totalPage=page.getPages();//��ҳ��
		int totalNum=(int) page.getTotal();//�ܼ�¼��
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("adminList", owerList);
		PageInfo pageInfo=new PageInfo(curPage, totalPage, totalNum,dates);//��ҳ��Ϣ��
		return pageInfo;
	}
	//�޸ĳ���״̬
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
	//ǰ̨����޸Ĺ���Ա,��תupdateUser.jsp
	@RequestMapping(value = "/updateUser.action", method = RequestMethod.GET, produces = "application/json;charset=utf-8")//CZK�޸Ĺ���Ա
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
