package com.nagarro.nagp.orderManagement.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

	private String code;
	private String address;
	private String username;
	private String description;
	private String status;
	private String createdTime;
	private String modifiedTime;
	private String serviceRegion;
	private String serviceProvider;

}
