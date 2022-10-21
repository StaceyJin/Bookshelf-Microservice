package com.jin.bookshelf.composite.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("com.jin")
public class BookCompositeServiceApplication {

	@Bean
	RestTemplate restTemplate() { return new RestTemplate(); }

	public static void main(String[] args) {
		SpringApplication.run(BookCompositeServiceApplication.class, args);
	}

}
