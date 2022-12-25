package com.example.demo.user;

import lombok.Data;

@Data
public class UserResponse {

	private Integer id;
	
	private String name;
	
	private Integer age;

	public UserResponse(Integer id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
}
