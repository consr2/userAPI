package com.example.demo.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.SiteUser;

public interface BookRepository extends JpaRepository<Book, Integer>{
	Optional<Book> findByName(String name);
}
