package com.example.demo.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.order.BookOrder;
import com.example.demo.order.OrderDto;
import com.example.demo.order.OrderDto2;
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
	//현재 결제목록이 2개이고 lazy로 인해 2바퀴 돌게 된다.
	//bList는 전체를 가져오는 쿼리 1개, 해당 유저의 이름을 가져오는 쿼리 1개
	//book정보를 가져오는 쿼리 2개가 필요하다. 만약 book이 많아지면 그만큼
	//쿼리 수가 많아지게 된다. (n+1문제)
	//api를 위한 Dto생성후 던져주기
	@GetMapping("api/v2/order")
	public Result  getOrder2() {
		List<BookOrder> bList = OrderService.findAll();
		List<OrderDto> collect = bList.stream()
				.map(m -> new OrderDto(m))
				.collect(Collectors.toList());
		return new Result<>(collect.size(),collect);
	}
	
	
	//fetch join을 이용해 쿼리 1개로 불러옴
	@GetMapping("/api/v3/order")
	public Result getOrder3() {
		List<BookOrder> bList = OrderService.findAllWithBookAndUser();
		List<OrderDto> collect = bList.stream()
				.map(m -> new OrderDto(m))
				.collect(Collectors.toList());
		return new Result<>(collect.size(),collect);
	}
	
	
	//바로 Dto로 받기
	@GetMapping("/api/v4/order")
	public List<OrderDto> getOrder4() {
		return OrderService.makequery();
	}
	
	
	//컬렉션을 조회할 시(xxToMany)는 현재는 해당 갯수만큼 쿼리가 많이 나가게 된다.
	//이 부분을 최적화 시켜 보자
	@GetMapping("/api/v1/getuser")
	public List<SiteUser> getUser3() {
		List<SiteUser> user = userService.findAll();
		return user;
	}
	
	@Data
	static class UserOrderDto{
		private Integer id;
		private String name;
		private int age;
		private List<OrderDto> orderList;
		
		public UserOrderDto(SiteUser user) {
			this.id = user.getId();
			this.name = user.getName();
			this.age = user.getAge();
			this.orderList = user.getOrderList().stream()
					.map(m -> new OrderDto(m))
					.collect(Collectors.toList());
			
		}
	}
	
	
	
	//v2에서는 dto를 만들어서 사용함 orderList또한 Dto로 만들어서 반환
	//현재는 쿼리가 많아 나가기 때문에 역시 jpql 사용해야함
	//현제쿼리 순서 유저목록-오더목록-책목록(유저4명이라 4번 오더목록이 실행 
	// 1번 유져의 오더에 2개의 주문이 있기 떄문에 2번 더 나감)
	@GetMapping("/api/v2/getuser")
	public List<UserOrderDto> getUser4(){
		List<SiteUser> user = userService.findAll();
		List<UserOrderDto> collect = user.stream()
				.map(m -> new UserOrderDto(m))
			.	collect(Collectors.toList());
		
		return collect;
	}
	
	//v3 현재 배열로 감싸져 있어서 이것 또한 result에 넣어서 보내주는 것이 좋음
	//fetch join
	@GetMapping("/api/v3/getuser")
	public Result<List<UserOrderDto>> getUser5() {
		List<SiteUser> user = userService.findAllByQuery();
		List<UserOrderDto> collect = user.stream()
				.map(m -> new UserOrderDto(m))
			.	collect(Collectors.toList());
		return new Result<List<UserOrderDto>>(collect.size(), collect);
	}
	
	
	
	
}
