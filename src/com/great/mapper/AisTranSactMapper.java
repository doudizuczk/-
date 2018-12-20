package com.great.mapper;

import java.util.List;

import com.great.bean.TranSact;

/*
 * 自动判定月缴用户是否过期
 * */
public interface AisTranSactMapper {
	public List<TranSact> queryAllTran();//查询套餐办理表中所有记录
	public int updateTranSactState(int tranId);//修改过期套餐的状态
}
