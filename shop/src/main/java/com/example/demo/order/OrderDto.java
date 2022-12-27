package com.example.demo.order;

import lombok.Data;

@Data
public class OrderDto {
	private Integer orderid;
	private String userName;
	private String bookName;
	private int price;
	private int quantity;
	
	public OrderDto(BookOrder order) {
		this.orderid = order.getId();
		this.userName = order.getUser().getName();
		this.bookName = order.getBook().getName();
		this.price = order.getPrice();
		this.quantity = order.getQuantity();
	}
}
