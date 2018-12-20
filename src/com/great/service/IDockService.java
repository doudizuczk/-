package com.great.service;


import java.util.List;
import java.util.Map;

import com.great.bean.Dock;
import com.great.bean.Rule;

public interface IDockService {
	/*
	 * 查询停靠实体对象 @linanping
	 * */
	public List<Dock> queryDock(Dock dock);
}
