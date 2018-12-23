package com.great.mapper;

import com.great.bean.Order;

public interface OrderMapper {
	public int addOrder(Order order);
	public Order queryOrder(Order order);
}
