package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.book.BookService;
import com.example.demo.order.OrderService;
import com.example.demo.user.UserControllerAPI;
import com.example.demo.user.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@AutoConfigureMockMvc
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class ShopApplicationTests {

	private final BookService bookService;
	private final OrderService orderService;
	private final UserControllerAPI userControllerApi;
	private final MockMvc mockMvc;

	//유저 등록 테스트
	//@Test
	void createBook() throws Exception{
		
		UserDto user = new UserDto("호랑이", 15);
		String json = new ObjectMapper().writeValueAsString(user);
		
		mockMvc.perform(post("/api/v2/user")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	//유저 목록조회 테스트
	//@Test
	public void getTest() throws Exception{
		mockMvc.perform(get("/api/v2/user/select")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$data").exists())
		.andDo(print());
	}
	
	
	
	//주문 등록
	//@Test
	void createOrder() {
		orderService.create("호랑이", "삼국지", 10000, 1);
		orderService.create("호랑이", "초한지", 60000, 3);
	}


	
	
	
	
	
	
}
