package com.example.demo.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserControllerAPI {

	private final UserService userService;
	
	@PostMapping("/api/v2/user")
	public Integer createUser(@RequestBody @Valid UserForm userF) {
		
		return userService.save(userF);
	}
	
	@PutMapping("api/v2/user/{id}")
	public UserResponse updateUser(@PathVariable("id")Integer id,
			@RequestBody @Valid UserForm userF) {
		userService.update(id, userF);
		
		return new UserResponse(id, userF.getName(), userF.getAge());
	}
	
}
