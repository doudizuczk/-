package com.great.mapper;

import java.util.List;

import com.great.bean.Dock;

public interface DockMapper {
	public List<Dock> queryDock(Dock dock);//根据车牌号获得进入车库时间
}
