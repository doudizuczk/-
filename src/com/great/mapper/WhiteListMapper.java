package com.great.mapper;

import java.util.List;
import java.util.Map;
/*创建人：@lian shengwei
 * 创建日期：2018-12-13
 * 白名单操作数据库接口类
 */
public interface WhiteListMapper {
	public List<Map<String, Object>> queryAllWhiteList();
}
