package com.nagarro.nagp.orderManagement.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.orderManagement.dao.OrderManagementDao;
import com.nagarro.nagp.orderManagement.entities.Order;
import com.nagarro.nagp.orderManagement.services.OrderManagementService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	@Autowired
	private EurekaClient eurekaClient;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

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

	@Override
	public void notifyProviders(String code) {
		Order order = getOrderForCode(code);
		String url = "/provider/notifyProviders";
		HttpEntity<String> request = new HttpEntity<>(order.getServiceRegion());
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("orders", false);
		restTemplate.postForObject(instance.getHomePageUrl() + url, request, null);

	}

	@Override
	public void updateProvider(String code, String username) {
		Order order = getOrderForCode(code);
		order.setServiceProvider(username);
		changeOrderStatus(code, "Complete");
		orderManagementDao.save(order);

		HttpEntity<String> request1 = new HttpEntity<>(order.getUsername());
		InstanceInfo instance1 = eurekaClient.getNextServerFromEureka("users", false);
		restTemplate.postForObject(instance1.getHomePageUrl() + "notifyUser", request1, null);

		HttpEntity<String> request2 = new HttpEntity<>(order.getServiceProvider());
		InstanceInfo instance2 = eurekaClient.getNextServerFromEureka("providers", false);
		restTemplate.postForObject(instance2.getHomePageUrl() + "notifyProvider", request2, null);
	}

	public EurekaClient getEurekaClient() {
		return eurekaClient;
	}

	public void setEurekaClient(EurekaClient eurekaClient) {
		this.eurekaClient = eurekaClient;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
