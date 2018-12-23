package com.great.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.great.bean.TranSact;
import com.great.mapper.AisTranSactMapper;
import com.great.mapper.CarMapper;
import com.great.service.IAisTranService;
@Service("aisServiceImpl")
public class AisServiceImpl implements IAisTranService{
	@Autowired
	private AisTranSactMapper aisTranMapper;
	@Autowired
	private CarMapper carMapper;
	
	
	@Override
	public List<TranSact> queryAllTran() {
		return aisTranMapper.queryAllTran();
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean updateTranSactState(TranSact tran){
		/*
		 * 判断是否白名单车辆
		 * */
		boolean result=false;
		try {
		List<Map<String,Object>> whiteCarList=carMapper.queryWhiteListCarByCarId(tran.getCarId());
		System.out.println("车牌号是"+tran.getCarId()+"白名单长度="+whiteCarList.size());
		/*是白名单车辆*/
		if(whiteCarList.size()!=0) {
			for(Map map:whiteCarList) {
				Number number=(Number)map.get("parkId");
				int parkId=number.intValue();//获取车位状态
				int updatePark=aisTranMapper.updatePark(parkId);//修改车位状态
				if(updatePark>0) {
					int updateTran=aisTranMapper.updateTranState(tran.getCarId());//修改套餐状态
					if(updateTran>0) {
						int updateCarState=carMapper.changeCarType(tran.getCarId());//修改车辆状态
						if(updateCarState>0) {
							result=true;
						}else {
							return result;
						}
					}else {
						return result;
					}
				}else {
					return result;
				}
			}
			/*不是白名单车辆*/
		}else {
			System.out.println("发现非白名单车辆"+tran.getCarId());
			int updateTran=aisTranMapper.updateTranState(tran.getCarId());
			if(updateTran>0) {
				int updateCarState=carMapper.changeCarType(tran.getCarId());
				if(updateCarState>0) {
					result=true;
				}else {
					return result;
				}
			}else {
				return result;
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{	
			return result;
		}
	}
	

}
