package com.great.service;

import java.util.List;

import com.great.bean.Order;

public interface IOrderService {
	public boolean addOrder(Order order);
	public Order queryOrder(Order order);
	public List<Order> queryAllOrder(Order order);
}
