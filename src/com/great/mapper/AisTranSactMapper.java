package com.great.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.great.bean.TranSact;

/*
 * 自动判定月缴用户是否过期
 * */
public interface AisTranSactMapper {
	public List<TranSact> queryAllTran();//查询套餐办理表中所有记录
	//public int updateTranSactState(TranSact tran);//修改过期套餐的状态(修改所有相关表的)
	public int updateTranState(@Param("carId")String carId);//单表操作
	public int updatePark(int parkId);//修改车位状态
}
