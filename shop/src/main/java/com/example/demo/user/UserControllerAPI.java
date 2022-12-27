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

import com.example.demo.order.BookOrder;
import com.example.demo.order.OrderDto;
import com.example.demo.order.OrderService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserControllerAPI {

	private final UserService userService;
	private final OrderService OrderService;
	
	//유저 등록 후 pk return
	@PostMapping("/api/v2/user")
	public Integer createUser(@RequestBody @Valid UserForm userF) {
		
		return userService.save(userF);
	}
	
	//유저 업데이트 후 pk,이름 return
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
	public Result<List<UserDto>> getUser2() {
		List<SiteUser> userList = userService.findAll();
		List<UserDto> collect = userList.stream()
				.map(m -> new UserDto(m.getName(), m.getAge()))
				.collect(Collectors.toList());
		
		return new Result<>(collect.size(), collect);
	}
	
	
	@Data
	@AllArgsConstructor
	static class Result<T> {
		private int count;
		private T data;
	}
	
	
	//2022-12-27 api select의 최적화
	//1:M 관계 
	//페이징 문제
	//현재는 lazy 상태이기 때문에 null로 인식 된다 초기화 해재를 위해 for문 추가
	@GetMapping("api/v1/order")
	public List<BookOrder> getOrder() {
		List<BookOrder> bList = OrderService.findAll();
		for(BookOrder order : bList) {
			order.getBook().getName();
			order.getUser().getName();
		}
		return bList;
	}
	
	
	//쿼리가 너무 많이 던지게 됨
	//api를 위한 Dto생성후 던져주기
	@GetMapping("api/v2/order")
	public Result  getOrder2() {
		List<BookOrder> bList = OrderService.findAll();
		List<OrderDto> collect = bList.stream()
				.map(m -> new OrderDto(m))
				.collect(Collectors.toList());
		return new Result<>(collect.size(),collect);
	}
	
	
	
}
