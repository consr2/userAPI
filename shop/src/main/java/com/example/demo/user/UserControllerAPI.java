package com.example.demo.user;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
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
	public UserDto updateUser(@PathVariable("id")Integer id,
			@RequestBody @Valid UserForm userF) {
		userService.update(id, userF);
		
		return new UserDto(userF.getName(), userF.getAge());
	}
	
	
	//엔티티를 직접 노출시 엔티티 변경시 api변경이 된다는 점이 문제
	//응답 스펙을 위한 로직 추가됨(jsonIgnore등)
	//json형태가 배열로 시작해버리게 된다(count를 추가할 때 문제)
	@GetMapping("/api/v1/user/select")
	public List<SiteUser> getUser(){
		return userService.findAll();
	}
	
	@GetMapping("api/v2/user/select")
	public Result getUser2() {
		List<SiteUser> userList = userService.findAll();
		List<UserDto> collect = userList.stream()
				.map(m -> new UserDto(m.getName(), m.getAge()))
				.collect(Collectors.toList());
		
		return new Result(collect.size(), collect);
	}
	
	
	@Data
	@AllArgsConstructor
	static class Result<T> {
		private int count;
		private T data;
	}
	
	
	
}
