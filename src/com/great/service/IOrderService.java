package com.great.service;

import com.great.bean.Order;

public interface IOrderService {
	public boolean addOrder(Order order);
	public Order queryOrder(Order order);
}
