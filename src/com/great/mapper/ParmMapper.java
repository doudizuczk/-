package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Parm;

public interface ParmMapper {
	public List<Map<String,Object>> queryAllParm();//查询所有参数
	public Parm changeParm(Integer parmId);//修改参数之前，根据id查询参数信息
	public List<Map<String,Object>> queryParmType();//查询参数类型
	public Parm searchType(int parmPid);//查询参数类型
	public int savechange(Parm parm);//保存参数修改
	public int createParm(Parm parm);//新增菜单
}
