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
 * 套餐表Handler--!czk
 * @author Administrator
 *
 */
@Controller// 此注释的含义是将该类设置成为浏览器提交的上来的类
@RequestMapping("/pack")
public class PackHandler {
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	@Autowired
	@Qualifier("packServiceImpl")
	private IPackService packService;
	
	
	//CZK-菜单点击套餐管理页面跳转--pack_List.jsp
	@RequestMapping(value = "/pack_List.action")
	public ModelAndView JumpPackList(HttpServletRequest request 
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
		List<Map<String,Object>> TypePack= parmService.IdQueryParmName(9);//类型列表
		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(10);//状态列表
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("StatePack", StatePack);
		dates.put("Type", TypePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/pack_List.jsp");
		return mav;
	}
	//CZK条件AJAX模糊查询套餐
	@RequestMapping(value = "/queryPackList.action", method = RequestMethod.POST)
	public @ResponseBody PageInfo queryPackList(HttpServletRequest request,@RequestBody Pack pack
			,@RequestParam(value = "pageCurr", required = true, defaultValue = "1")int pageCurr) {//pageCurr不能为空，并初始化
		Page<Object> page=PageHelper.startPage(pageCurr, 5);
		List<Map<String,Object>> packList = packService.queryPackList(pack);//套餐列表
		int curPage=page.getPageNum();//当前页数
		int totalPage=page.getPages();//总页数
		int totalNum=(int) page.getTotal();//总记录数
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packList", packList);
		PageInfo pageInfo=new PageInfo(curPage, totalPage, totalNum,dates);//分页信息类
		return pageInfo;
		
	}
	//修改套餐状态
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
	//CZK-packList点击新增套餐跳转
	@RequestMapping(value = "/add_pack.action")
	public ModelAndView adminList(HttpServletRequest request) {//pageCurr不能为空，并初始化
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> TypePack= parmService.IdQueryParmName(9);//套餐类型列表
//		List<Map<String,Object>> StatePack= parmService.IdQueryParmName(10);//套餐状态列表
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("TypePack", TypePack);
//		dates.put("StatePack", StatePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/addPack.jsp");
		return mav;
	}
	//新增套餐名查存
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
	
	//新增套餐插入数据
	@RequestMapping(value = "/addpack.action", method = RequestMethod.POST)
	public @ResponseBody int addAdminList(HttpServletRequest request,@RequestBody Pack pack) {
		int a = packService.addPackAtt(pack);
		return a;
	}
	
	//前端点击修改套餐,跳转updatePack.jsp
	@RequestMapping(value = "/updatePack.action", method = RequestMethod.GET, produces = "application/json;charset=utf-8")//CZK修改管理员
	public ModelAndView updateUser(HttpServletRequest request) {
		Integer packId=Integer.parseInt(request.getParameter("packId"));
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packList =  packService.queryPackList(pack);//id查用户
		List<Map<String,Object>> TypePack= parmService.IdQueryParmName(9);//类型列表
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packMap", packList.get(0));
		dates.put("TypePack", TypePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/updatePack.jsp");
		return mav;
	}
	//修改套餐插入数据
	@RequestMapping(value = "/addUpdatePack.action", method = RequestMethod.POST)
	public @ResponseBody int addUpdateAdmin(HttpServletRequest request,@RequestBody Pack pack) {
		
		int a = packService.updatePackAtt(pack);
		return a;
	}


}
