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
import com.great.bean.SearchUtil;
import com.great.service.ILogService;

@Controller
@RequestMapping("/logHandler")
public class LogHandler {
	@Autowired
	@Qualifier("logServiceImpl")
	private ILogService logService;
	//method = RequestMethod.POST,produces = "application/json;charset=utf-8"
	@RequestMapping(value="/queryLogList.action")
	public ModelAndView queryLogList(HttpServletRequest request ,SearchUtil searchUitl ,@RequestParam(value = "pageNum", required = true, defaultValue = "1")int pageNum) {
		ModelAndView mav=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 5);
		List<Map<String,Object>> logList=logService.logList(searchUitl);
		int pages=page.getPageNum();//当前页数
		Integer allNum=page.getPages();//总页数
		mav.addObject("pageNum", pageNum);
		mav.addObject("allNum", allNum);
		mav.addObject("logList", logList);
		mav.addObject("searchUitl", searchUitl);
		mav.setViewName("forward:/backstage/looklog.jsp");
		return mav;
	}
}
