package com.tb.inpost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.tb.inpost.dto.ProductRequestDTO;
import com.tb.inpost.dto.ProductResponseDTO;
import com.tb.inpost.model.Product;
import com.tb.inpost.repository.ProductRepository;



@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
	
	
	ProductService productService;

	
	@Mock
	ProductRepository productRepository;
	
	@BeforeEach
	void setup() {
		productService = new ProductService(productRepository);

	}

	@Test
	@DisplayName("getProductCalculateTest")
	void getProductCalculateTest_notNull() {
		
		UUID uuid = new UUID(1, 1);
		when(productRepository.findById(uuid)).thenReturn(buildProduct());  
		
		ProductRequestDTO requestDTO = new  ProductRequestDTO();
		requestDTO.setAmount(10);
		
		ResponseEntity<ProductResponseDTO> productResponseDTO = productService.getProductCalculate(uuid, requestDTO);
		
		assertNotNull(productResponseDTO);
		
		
	}
	
	
	private Optional<Product> buildProduct() {
		
		Product product = new Product();
		
		UUID uuid = new UUID(1, 1);
		product.setId(uuid);
		product.setPrice(100.0);
		product.setTitle("product mock");
		
		return Optional.of(product);		
		
	}
	
	
	@Test
	@DisplayName("countBasedDiscountPolicyTest")
	void countBasedDiscountPolicyTest() {
		
		Optional<Product> product = buildProduct();
		
		Double discount = productService.countBasedDiscountPolicy(product.get(), 10);
		
		assertEquals(5.0, discount);		
		
    }
	
	@Test
	@DisplayName("percentageBasedDiscountPolicyTest")
	void percentageBasedDiscountPolicyTest() {
		
		Optional<Product> product = buildProduct();
		
		Double discount = productService.percentageBasedDiscountPolicy(product.get());
		
		assertEquals(0.1, discount);		
		
    }
		
	
	
	

}
