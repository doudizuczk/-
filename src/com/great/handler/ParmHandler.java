package com.great.handler;

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
import com.great.bean.Parm;
import com.great.service.IParmService;

@Controller
@RequestMapping("/parm")
public class ParmHandler {
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	//��ȡ�����б�
	@RequestMapping(value="/parmList.action")
	public ModelAndView parmList(HttpServletRequest request,Parm parm,@RequestParam(value = "pageNum", required = true, defaultValue = "1")int pageNum) {
		ModelAndView mav=new ModelAndView();
		
		Page<Object> page=PageHelper.startPage(pageNum, 5);
		List<Map<String,Object>> parmList=parmService.queryAllParm();
		mav.addObject("parmList", parmList);
		int parmCount=(int)page.getTotal();
		Integer allNum=page.getPages();//��ҳ��
		mav.addObject("pageNum", pageNum);
		mav.addObject("allNum", allNum);
		mav.addObject("parm", parm);
		mav.addObject("parmCount",parmCount);
		mav.setViewName("forward:/backstage/parm.jsp");
		
		return mav;
	}
	//�޸�֮ǰ��ѯ������Ϣ
	@RequestMapping(value="/beforeChange.action",method = RequestMethod.GET)
	public ModelAndView beforeChange(int parmId) {
		ModelAndView mav=new ModelAndView();
		Parm parm=parmService.changeParm(parmId);//��ѯ��������
		List<Map<String,Object>> parmTypeList=parmService.queryParmType();//��ѯ��������б�
		mav.addObject("parm", parm);
		mav.addObject("parmTypeList", parmTypeList);
		mav.setViewName("forward:/backstage/changeparm.jsp");
		return mav;
	}
	//�ύ�����޸���Ϣ
	@RequestMapping(value="/saveChanges.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String saveChanges(@RequestBody Parm parm) {
		int parmPid=parm.getParmPid();
		int parmId=parm.getParmId()+12;
		parm.setParmId(parmId);//�������ݿ����Ĳ���id
		Parm checkParm=parmService.repeatCheck(parm);//�������ظ����
		if(checkParm==null) {
		int result=parmService.savechange(parm);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
		}else {
			return "2";
		}
	}
	//��ȡ��������
	@RequestMapping(value="/getParmPname.action")
	public ModelAndView getParmPname() {
		ModelAndView mav=new ModelAndView();
		List<Map<String,Object>> parmTypeList=parmService.queryParmType();//��ѯ��������б�
		mav.addObject("parmTypeList", parmTypeList);
		mav.setViewName("forward:/backstage/createparm.jsp");
		return mav;
	}
	//��������
	@RequestMapping(value="/createParm.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String createParm(@RequestBody Parm parm) {
		Parm theparm=parmService.searchType(parm.getParmPid());
		Parm checkParm=parmService.repeatCheck(parm);
		parm.setParmType(theparm.getParmName());
		if(checkParm==null) {
		int result=parmService.createParm(parm);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}else {
		return "2";
	}
	}
}
