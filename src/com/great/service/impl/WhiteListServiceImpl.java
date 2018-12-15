package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.WhiteListMapper;
import com.great.service.IWhiteListService;

/*������@lian shengwei
 * �������ڣ�2018-12-13
 * ������ҵ��ʵ����1
 */
@Service("whiteListServiceImpl")
public class WhiteListServiceImpl implements IWhiteListService {
	@Autowired
	private WhiteListMapper whiteListMapper;
	//����������
	@Override
	public List<Map<String, Object>> queryAllWhiteList() {
		return whiteListMapper.queryAllWhiteList();
	}
	//���ð�����
	@Override
	public boolean stopWhiteList(int tranId) {
		// TODO Auto-generated method stub
		int count=whiteListMapper.stopWhiteList(tranId);
		return count>0;
	}
	//���ð�����
	@Override
	public boolean starWhiteList(int tranId) {
		// TODO Auto-generated method stub
		int count=whiteListMapper.starWhiteList(tranId);
		return count>0;
	}
	//��������ҳ��ģ����ѯ
	@Override
	public List<Map<String, Object>> turnPageWhiteList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whiteListMapper.turnPageWhiteList(map);
	}
	//��⳵�ƺ�
	@Override
	public boolean chechCarId(String carId) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
