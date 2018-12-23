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
import com.great.bean.Pack;
import com.great.bean.PageInfo;
import com.great.service.IPackService;
import com.great.service.IParmService;

/**
 * �ײͱ�Handler--!czk
 * @author Administrator
 *
 */
@Controller// ��ע�͵ĺ����ǽ��������ó�Ϊ������ύ����������
@RequestMapping("/pack")
public class PackHandler {
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	@Autowired
	@Qualifier("packServiceImpl")
	private IPackService packService;
	
	
	//CZK-�˵�����ײ͹���ҳ����ת--pack_List.jsp
	@RequestMapping(value = "/pack_List.action")
	public ModelAndView JumpPackList(HttpServletRequest request 
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
		List<Map<String,Object>> TypePack= parmService.IdQueryParmName(9);//�����б�
		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(10);//״̬�б�
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("StatePack", StatePack);
		dates.put("Type", TypePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/pack_List.jsp");
		return mav;
	}
	//CZK����AJAXģ����ѯ�ײ�
	@RequestMapping(value = "/queryPackList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryPackList(HttpServletRequest request,@RequestBody Pack pack
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr����Ϊ�գ�����ʼ��
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		List<Map<String,Object>> packList = packService.queryPackList(pack);//�ײ��б�
		int curPage=page.getPageNum();//��ǰҳ��
		int totalPage=page.getPages();//��ҳ��
		int totalNum=(int) page.getTotal();//�ܼ�¼��
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packList", packList);
		PageInfo pageInfo=new PageInfo(curPage, totalPage, totalNum,dates);//��ҳ��Ϣ��
		return pageInfo;
		
	}
	//�޸��ײ�״̬
	@RequestMapping(value ="/updatePackState.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody int updateUserState(HttpServletRequest request ) {
		Integer packId=Integer.parseInt(request.getParameter("packId"));
		Integer state=Integer.parseInt(request.getParameter("state"));
		Pack pack = new Pack();
		if(state==1) {
			pack.setPactState(2);
		}
		if(state==2) {
			pack.setPactState(1);
		}
		pack.setPackId(packId);
		int a = packService.updatePackState(pack);
		
		return a;
	}
	//CZK-packList��������ײ���ת
	@RequestMapping(value = "/add_pack.action")
	public ModelAndView adminList(HttpServletRequest request) {//pageCurr����Ϊ�գ�����ʼ��
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> TypePack= parmService.IdQueryParmName(9);//�ײ������б�
//		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(10);//�ײ�״̬�б�
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("TypePack", TypePack);
//		dates.put("StatePack", StatePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/addPack.jsp");
		return mav;
	}
	//�����ײ������
	@RequestMapping(value ="/addQuerypackExist.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody int addQueryAdminExist(HttpServletRequest request) {
		String packName=request.getParameter("packName");
		Pack pack = new Pack();
		pack.setPackName(packName);
		List<Map<String,Object>> packList = packService.addQueryPackExist(pack);
		if(packList.size()>0) {
			return 1;
		}
		return 0;
	}
	
	//�����ײͲ�������
	@RequestMapping(value = "/addpack.action", method = RequestMethod.POST)
	public @ResponseBody int addAdminList(HttpServletRequest request,@RequestBody Pack pack) {
		int a = packService.addPackAtt(pack);
		return a;
	}
	
	//ǰ�˵���޸��ײ�,��תupdatePack.jsp
	@RequestMapping(value = "/updatePack.action", method = RequestMethod.GET, produces = "application/json;charset=utf-8")//CZK�޸Ĺ���Ա
	public ModelAndView updateUser(HttpServletRequest request) {
		Integer packId=Integer.parseInt(request.getParameter("packId"));
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packList =  packService.queryPackList(pack);//id���û�
		List<Map<String,Object>> TypePack= parmService.IdQueryParmName(9);//�����б�
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packMap", packList.get(0));
		dates.put("TypePack", TypePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/updatePack.jsp");
		return mav;
	}
	//�޸��ײͲ�������
	@RequestMapping(value = "/addUpdatePack.action", method = RequestMethod.POST)
	public @ResponseBody int addUpdateAdmin(HttpServletRequest request,@RequestBody Pack pack) {
		
		int a = packService.updatePackAtt(pack);
		return a;
	}


}
