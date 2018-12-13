package com.great.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.great.service.IMenuService;
import com.great.service.IWhiteListService;

/*创建人：@lian shengwei
 * 创建日期：2018-12-13
 * 白名单Action
 */
@Controller()
@RequestMapping("/whiteListHander")
public class WhiteListHander {
	@Autowired
	@Qualifier("whiteListServiceImpl")
	private IWhiteListService whiteListService;
	
	@RequestMapping("/whiteList.action")
	public ModelAndView queryAllWhiteList(HttpServletRequest request,Integer pageNum) {
		System.out.println(pageNum);
		if(pageNum==null) {
			pageNum=1;
		}
		ModelAndView model=new ModelAndView();
		//Page<Object> page=PageHelper.startPage(pageNum, searchNum);
		List<Map<String,Object>> whiteList=whiteListService.queryAllWhiteList();
		model.addObject("whiteList",whiteList);
		model.setViewName("forward:/backstage/whitelist.jsp");
		return model;		
	}

}
