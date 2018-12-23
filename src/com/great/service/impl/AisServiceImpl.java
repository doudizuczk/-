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
		 * �ж��Ƿ����������
		 * */
		boolean result=false;
		try {
		List<Map<String,Object>> whiteCarList=carMapper.queryWhiteListCarByCarId(tran.getCarId());
		System.out.println("���ƺ���"+tran.getCarId()+"����������="+whiteCarList.size());
		/*�ǰ���������*/
		if(whiteCarList.size()!=0) {
			for(Map map:whiteCarList) {
				Number number=(Number)map.get("parkId");
				int parkId=number.intValue();//��ȡ��λ״̬
				int updatePark=aisTranMapper.updatePark(parkId);//�޸ĳ�λ״̬
				if(updatePark>0) {
					int updateTran=aisTranMapper.updateTranState(tran.getCarId());//�޸��ײ�״̬
					if(updateTran>0) {
						int updateCarState=carMapper.changeCarType(tran.getCarId());//�޸ĳ���״̬
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
			/*���ǰ���������*/
		}else {
			System.out.println("���ַǰ���������"+tran.getCarId());
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
