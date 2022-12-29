package com.example.demo.order;

import com.example.demo.book.Book;
import com.example.demo.user.SiteUser;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BookOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int price;
	
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private SiteUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Book book;
}
