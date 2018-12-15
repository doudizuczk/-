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

/*������@lian shengwei
 * �������ڣ�12018-12-13
 * ������
 */
@Controller()
@RequestMapping("/whiteListHander")
public class WhiteListHander {
	@Autowired
	@Qualifier("whiteListServiceImpl")
	private IWhiteListService whiteListService;
	//��ȡ�������б�
	@RequestMapping("/whiteList.action")
	public ModelAndView queryAllWhiteList(HttpServletRequest request,Integer pageNum) {
		if(pageNum==null) {
			pageNum=1;
		}
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 10);
		List<Map<String,Object>> whiteList=whiteListService.queryAllWhiteList();
		model.addObject("pageNum",page.getPageNum());//��ǰҳ��
		model.addObject("pages",page.getPages());//��ҳ����
		model.addObject("whiteList",whiteList);
		model.setViewName("forward:/backstage/whitelist.jsp");
		return model;		
	}
	//�������б�ҳ�Ͳ�ѯ
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
		List<Map<String,Object>> whiteList=whiteListService.turnPageWhiteList(map);
		return whiteList;
	}
	//���ð�����
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
	//���ð�����
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
