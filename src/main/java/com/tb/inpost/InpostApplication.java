package com.tb.inpost;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tb.inpost.model.Product;
import com.tb.inpost.repository.ProductRepository;

@SpringBootApplication
public class InpostApplication {

	public static void main(String[] args) {
		SpringApplication.run(InpostApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository) {
		return args -> {
			List<Product> products = List.of(new Product("Product 1", 100.0),
					new Product("Product 2", 200.0),
					new Product("Product 3", 300.0));
			productRepository.saveAll(products);

			productRepository.findAll().stream().forEach(System.out::println);
		};
	}

}
