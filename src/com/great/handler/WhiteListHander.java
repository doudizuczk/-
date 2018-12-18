package com.great.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.great.service.IWhiteListService;

/*创建人@lian shengwei
 * 创建日期：12018-12-13
 * 白名单:最新
 */
@Controller()
@RequestMapping("/whiteListHander")
public class WhiteListHander {
	@Autowired
	@Qualifier("whiteListServiceImpl")
	private IWhiteListService whiteListService;
	//白名单办理检测chechCarId.action
	@RequestMapping(value ="/chechCarId.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String chechCarId(HttpServletRequest request, String carId) {
		boolean result=false;
		
		if(result) {
			return "1";
		}else {
			return "2";
		}
	}
	//新增白名单列表
	@RequestMapping("/addWhiteList.action")
	public ModelAndView addWhiteList(HttpServletRequest request) {
		ModelAndView model=new ModelAndView();
		model.setViewName("forward:/backstage/add_whitelist.jsp");
		return model;		
	}
	//获取白名单列表
	@RequestMapping("/whiteList.action")
	public ModelAndView queryAllWhiteList(HttpServletRequest request,Integer pageNum) {
		if(pageNum==null) {
			pageNum=1;
		}
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 10);
		List<Map<String,Object>> whiteList=whiteListService.queryAllWhiteList();
		model.addObject("pageNum",page.getPageNum());//当前页码
		model.addObject("pages",page.getPages());//总页码数
		model.addObject("whiteList",whiteList);
		model.setViewName("forward:/backstage/whitelist.jsp");
		return model;		
	}
	//白名单列表翻页和查询
	@RequestMapping(value ="/turnPageWhiteList.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody List<Map<String,Object>> turnPageWhiteList(HttpServletRequest request) {		
		int pageNum=Integer.parseInt(request.getParameter("pageNums"));
		String stages=request.getParameter("stage");
        String carId=request.getParameter("carId");
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("carId",carId); 
        if(stages!=null&&stages!="") {
        	int stage=Integer.parseInt(stages);
        	map.put("stage",stage);
        }
        Map<String, Object> map2 = new HashMap<String, Object>();  
        Page<Object> page=PageHelper.startPage(pageNum, 10);
		List<Map<String,Object>> whiteList=whiteListService.turnPageWhiteList(map);
		map2.put("pageNum",page.getPageNum());  
        map2.put("pages", page.getPages());  
        whiteList.add(0, map2);
		return whiteList;
	}
	//禁用白名单
	@RequestMapping(value ="/stopWhiteList.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String stopWhiteList(HttpServletRequest request, Integer tranId) {
		boolean result=false;
		int tranIds=0;
		if(tranId!=null) {
			tranIds=tranId;
		}
		result=whiteListService.stopWhiteList(tranIds);
		if(result) {
			return "1";
		}else {
			return "2";
		}
	}
	//启用白名单
	@RequestMapping(value ="/starWhiteList.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String starWhiteList(HttpServletRequest request, Integer tranId) {
		boolean result=false;
		int tranIds=0;
		if(tranId!=null) {
			tranIds=tranId;
		}
		result=whiteListService.starWhiteList(tranId);
		if(result) {
			return "1";
		}else {
			return "2";
		}
	}

}
