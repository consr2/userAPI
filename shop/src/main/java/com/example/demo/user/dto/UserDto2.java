package com.example.demo.user.dto;

import java.util.List;

import com.example.demo.order.BookOrder;
import com.example.demo.order.OrderDto2;
import com.example.demo.user.SiteUser;

import lombok.Data;

@Data
public class UserDto2 {

	private Integer id;
	
	private String name;
	
	private int age;
	
	private List<OrderDto2> orderList;

	public UserDto2(SiteUser user) {
		this.id = user.getId();
		this.name = user.getName();
		this.age = user.getAge();
	} 
}
