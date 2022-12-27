package com.example.demo.order;

import com.example.demo.book.Book;
import com.example.demo.user.SiteUser;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BookOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private SiteUser user;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Book book;
	
	private int price;
	
	private int quantity;
}
