package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
	

//	@Bean
//	Hibernate5Module hivernate5Module() {
//		Hibernate5Module hibernate5Module = new Hibernate5Module();
//		return hibernate5Module;
//	}
}
