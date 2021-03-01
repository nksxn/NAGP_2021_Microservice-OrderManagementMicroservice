package com.nagarro.nagp.orderManagement.services;

import java.util.List;

import com.nagarro.nagp.orderManagement.entities.Order;

public interface OrderManagementService {
	List<Order> getAllOrders();

	Order getOrderForCode(String code);

	boolean createOrder(Order order);

	boolean changeOrderStatus(String code, String status);

	List<Order> getAllOrdersForUsername(String username);

	List<Order> getAllOrdersForServiceArea(String serviceArea);

	void notifyProviders(String code);

	void updateProvider(String code, String username);
}
