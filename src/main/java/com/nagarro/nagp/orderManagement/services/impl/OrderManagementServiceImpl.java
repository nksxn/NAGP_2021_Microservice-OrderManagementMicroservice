package com.nagarro.nagp.orderManagement.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.orderManagement.dao.OrderManagementDao;
import com.nagarro.nagp.orderManagement.entities.Order;
import com.nagarro.nagp.orderManagement.services.OrderManagementService;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	@Autowired
	OrderManagementDao orderManagementDao;

	@Override
	public Order getOrderForCode(String code) {
		return orderManagementDao.getOrderForCode(code);
	}

	@Override
	public boolean createOrder(Order order) {
		LocalDateTime now = LocalDateTime.now();
		order.setStatus("Pending");
		order.setCreatedTime(dtf.format(now));
		order.setModifiedTime(dtf.format(now));
		return orderManagementDao.createOrder(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderManagementDao.getAllOrders();
	}

	@Override
	public boolean changeOrderStatus(String code, String status) {
		return orderManagementDao.changeOrderStatus(code, status);
	}

	@Override
	public List<Order> getAllOrdersForUsername(String username) {
		return orderManagementDao.getAllOrdersForUsername(username);
	}

	@Override
	public List<Order> getAllOrdersForServiceArea(String serviceArea) {
		return orderManagementDao.getAllOrdersForServiceArea(serviceArea);
	}

}
