package com.example.demo.order;

import lombok.Data;

@Data
public class OrderDto2 {
	private Integer orderid;
	private String userName;
	private String bookName;
	private int price;
	private int quantity;
	
	public OrderDto2(Integer orderid, String userName, String bookName, int price, int quantity) {
		this.orderid = orderid;
		this.userName = userName;
		this.bookName = bookName;
		this.price = price;
		this.quantity = quantity;
	}
	
	
}
