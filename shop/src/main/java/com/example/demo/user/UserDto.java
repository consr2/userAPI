package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	
	private String name;
	private int age;
	
	public UserDto(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	
}
