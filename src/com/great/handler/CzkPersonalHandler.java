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
 *Ϊ�˱����ͻ������дһ�������Կ�
 * @author Administrator
 *
 */


@Controller // ��ע�͵ĺ����ǽ��������ó�Ϊ������ύ����������
@RequestMapping("/czkPer")
public class CzkPersonalHandler {
	@Autowired
	@Qualifier("czkPerServiceImpl")
	private ICzkPersonalService czkService;
	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;
	
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
	//��������Ա
	@RequestMapping(value = "/addAdmin.action", method = RequestMethod.POST)//CZK�������ҹ���Ա
	public @ResponseBody int queryAdminList(HttpServletRequest request,@RequestBody Admin admin
			) {//pageCurr����Ϊ�գ�����ʼ��
		int a = czkService.addAdmin(admin);
		
		return a;
	}
	//CZK-�˵����user_listҳ����ת
	@RequestMapping(value = "/userList.action")
	//ModelAndView��һ��������������ajax����
	public ModelAndView adminList(HttpServletRequest request
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("forward:/backstage/carUser_list.jsp");
		return mav;
	}
	
	//CZK�������ҳ���
	@RequestMapping(value = "/queryOwerList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryAdminList(HttpServletRequest request,@RequestBody Ower ower
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
//		Map<String, Object>  ad = admin;//����ajax���ص�ֵ
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		
		List<Map<String,Object>> owerList = czkService.conditionQueryCarUserList(ower);
		
		int curePage=page.getPageNum();//��ǰҳ��
		int totalPage=page.getPages();//��ҳ��
		int totalNum=(int) page.getTotal();//�ܼ�¼��
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("adminList", owerList);
		PageInfo pageInfo=new PageInfo(curePage, totalPage, totalNum,dates);//��ҳ��Ϣ��
		return pageInfo;
	}


	

}
