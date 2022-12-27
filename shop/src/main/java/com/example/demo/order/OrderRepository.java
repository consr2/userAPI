package com.example.demo.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<BookOrder, Integer>{
	
	@Query("select o from BookOrder o join fetch o.user m join fetch o.book b")
	List<BookOrder> query1();
}
