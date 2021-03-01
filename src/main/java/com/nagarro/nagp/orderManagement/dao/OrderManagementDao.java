package com.nagarro.nagp.orderManagement.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nagarro.nagp.orderManagement.entities.Order;

@Repository
public class OrderManagementDao {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	private static final Logger LOG = LoggerFactory.getLogger(OrderManagementDao.class);

	private static List<Order> orders = new ArrayList<Order>();

	public Order getOrderForCode(String code) {
		List<Order> result = orders.stream().filter(c -> c.getCode().equalsIgnoreCase(code))
				.collect(Collectors.toList());
		return result.get(0);
	}

	public boolean createOrder(Order order) {
		LOG.info("Mocking the DB call. Adding data to the list");
		List<Order> result = orders.stream().filter(c -> c.getCode().equalsIgnoreCase(order.getCode()))
				.collect(Collectors.toList());
		if (result.isEmpty()) {
			orders.add(order);
			return true;
		}
		LOG.error("Order code already exists in the database.");
		return false;
	}

	public List<Order> getAllOrders() {
		return orders;
	}

	public boolean changeOrderStatus(String code, String status) {
		LocalDateTime now = LocalDateTime.now();
		Order order = getOrderForCode(code);
		if (order == null) {
			return false;
		}
		order.setStatus(status);
		order.setModifiedTime(dtf.format(now));
		orders.add(order);
		return Boolean.TRUE;
	}

	public List<Order> getAllOrdersForUsername(String username) {
		LOG.info("Mocking the DB call. Fetching data to the list");
		List<Order> result = orders.stream().filter(c -> c.getUsername().equalsIgnoreCase(username))
				.collect(Collectors.toList());
		if (result.isEmpty()) {
			return null;
		}
		return result;
	}

	public List<Order> getAllOrdersForServiceArea(String serviceArea) {
		LOG.info("Mocking the DB call. Fetching data to the list");
		List<Order> result = orders.stream().filter(c -> c.getServiceRegion().equalsIgnoreCase(serviceArea))
				.collect(Collectors.toList());
		if (result.isEmpty()) {
			return null;
		}
		return result;
	}

	public void save(Order order) {
		orders.add(order);

	}

}
