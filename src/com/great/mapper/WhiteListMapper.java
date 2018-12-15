package com.great.mapper;

import java.util.List;
import java.util.Map;
/*创建人：@lian shengwei
 * 创建日期：2018-12-13
 * 1
 */
public interface WhiteListMapper {
	public List<Map<String, Object>> queryAllWhiteList();//搜索白名单列表
	public List<Map<String, Object>> turnPageWhiteList(Map<String,Object> map);//白名单翻页和模糊查询
	public int stopWhiteList(int tranId);//禁用白名单
	public int starWhiteList(int tranId);//启用白名单
	public int chechCarId(int carId);//检测白名单车牌号
}
