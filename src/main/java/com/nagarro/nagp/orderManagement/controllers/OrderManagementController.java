package com.nagarro.nagp.orderManagement.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.orderManagement.entities.Order;
import com.nagarro.nagp.orderManagement.services.OrderManagementService;

@RestController
@RequestMapping(value = "/order")
@EnableCircuitBreaker
public class OrderManagementController {

	@Value("${server.port}")
	private int port;

	@Resource
	OrderManagementService orderManagementService;

	private static final Logger LOG = LoggerFactory.getLogger(OrderManagementController.class);

	@GetMapping(value = "/{code}")
	Order getOrderForCode(@PathVariable(name = "code") String code) {
		LOG.info("Working from port " + port + " of Order management miroservice");
		return orderManagementService.getOrderForCode(code);
	}

	@PostMapping()
	public ResponseEntity<Void> createOrder(@RequestBody Order order) {
		LOG.info("Working from port " + port + " of Order management microservice");
		if (orderManagementService.createOrder(order)) {
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@GetMapping()
	public ResponseEntity<List<Order>> getAllOrders() {
		LOG.info("Working from port " + port + " of Order management microservice");
		List<Order> result = orderManagementService.getAllOrders();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<List<Order>>(result, headers, HttpStatus.OK);
	}

	@PostMapping(value = "/status/{status}")
	public ResponseEntity<Void> changeOrderStatus(@RequestBody String code, @PathVariable("status") String status) {
		LOG.info("Working from port " + port + " of Order management microservice");
		if (orderManagementService.changeOrderStatus(code, status)) {
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<Void>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@GetMapping(value = "/serviceArea/{serviceArea}")
	public ResponseEntity<List<Order>> getAllOrdersForServiceArea(
			@PathVariable(name = "serviceArea") String serviceArea) {
		LOG.info("Working from port " + port + " of Order management microservice");
		List<Order> result = orderManagementService.getAllOrdersForServiceArea(serviceArea);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<List<Order>>(result, headers, HttpStatus.OK);
	}

	@GetMapping(value = "/user/{username}")
	public ResponseEntity<List<Order>> getAllOrdersForUsername(@PathVariable(name = "username") String username) {
		LOG.info("Working from port " + port + " of Order management microservice");
		List<Order> result = orderManagementService.getAllOrdersForUsername(username);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<List<Order>>(result, headers, HttpStatus.OK);
	}

	@PostMapping(value = "/notify")
	public ResponseEntity<Void> notifyProviders(@RequestBody String code) {
		orderManagementService.notifyProviders(code);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
	}

}
