package com.example.demo.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.book.Book;
import com.example.demo.book.BookService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserService userService;
	private final BookService bookService;
	private final EntityManager em;
	
	//주문 등록
	public void create(String username, String book, int price, int quantity) {
		SiteUser user = userService.findByName(username);
		Book book1 = bookService.findByName(book);
		
		BookOrder order = new BookOrder();
		order.setBook(book1);
		order.setUser(user);
		order.setPrice(price);
		order.setQuantity(quantity);
		
		orderRepository.save(order);
	}
	
	
	//모든 리스트 보기
	public List<BookOrder> findAll(){
		return orderRepository.findAll();
	}
	
	//쿼리 생성
	public List<BookOrder> findAllWithBookAndUser(){
		return orderRepository.query1();
	}
	
	//쿼리로 바로 Dto로 받기
	public List<OrderDto> makequery(){
		String sql = "select new com.example.demo.order.OrderDto2(o.id, u.name, b.name, o.price, o.quantity)" +
					" from BookOrder o" +
					" join o.user u" +
					" join o.book b";
		List<OrderDto> oList = em.createQuery(sql, OrderDto.class).getResultList();
		
		return oList;
	}
	
}
