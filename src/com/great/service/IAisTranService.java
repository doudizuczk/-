package com.great.service;

import java.util.List;

import com.great.bean.TranSact;

public interface IAisTranService {
	public List<TranSact> queryAllTran();//查询套餐办理表中所有记录
	public boolean updateTranSactState(TranSact tran);//修改过期套餐的状态
}
