package com.example.demo.book;

import java.util.List;

import com.example.demo.order.BookOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private int price;
	
	private int amount;
	
	@JsonIgnore
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<BookOrder> orderList;
}
