package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Menu;

public interface MenuMapper {
	public List<Map<String,Object>> queryAllMenu();
}
