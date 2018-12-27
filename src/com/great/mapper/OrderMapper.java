package com.great.mapper;

import java.util.List;

import com.great.bean.Order;

public interface OrderMapper {
	public int addOrder(Order order);
	public Order queryOrder(Order order);
	public List<Order> queryAllOrder(Order order);
}
