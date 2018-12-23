package com.great.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Order;
import com.great.mapper.DockMapper;
import com.great.mapper.OrderMapper;
import com.great.service.IOrderService;
@Service("orderServiceImpl")
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public Order queryOrder(Order order) {
		// TODO Auto-generated method stub
		return orderMapper.queryOrder(order);
	}

	@Override
	public boolean addOrder(Order order) {
		// TODO Auto-generated method stub
		return orderMapper.addOrder(order)>0;
	}

}
