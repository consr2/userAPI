package com.example.demo.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserForm {

	private Integer id;
	
	@NotEmpty
	private String name;
	
	private Integer age;
	
}
