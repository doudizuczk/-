package com.great.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/*创建人@lian shengwei
 * 创建日期：2018-12-13
 * 白名单业务接口1
 */
public interface IWhiteListService {
	public List<Map<String,Object>> queryAllWhiteList();//搜索白名单列表
	public List<Map<String,Object>> turnPageWhiteList(Map<String,Object> map);//白名单翻页和模糊查询
	public boolean stopWhiteList(int tranId);//禁用
	public boolean starWhiteList(int tranId);//启用
	public boolean chechCarId(String carId);//检测车牌号
	
}
