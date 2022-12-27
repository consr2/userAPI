package com.example.demo.book;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	
	public void create(String name, int price, int amount) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setAmount(amount);
		
		bookRepository.save(book);
	}
	
	public Book findByName(String name) {
		return bookRepository.findByName(name).get();
	}
}
