package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.book.BookService;
import com.example.demo.order.OrderService;

@SpringBootTest
class ShopApplicationTests {

	@Autowired
	private BookService bookService;
	@Autowired
	private OrderService orderService;
	
	

	//책등록
	//@Test
	void createBook() {
		bookService.create("삼국지", 10000, 100);
		bookService.create("초한지", 15000, 150);
	}
	
	//주문 등록
	//@Test
	void createOrder() {
		orderService.create("홍길동", "삼국지", 20000, 2);
		orderService.create("홍길동", "초한지", 60000, 3);
	}
}
