package com.tb.inpost.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tb.inpost.dto.ProductRequestDTO;
import com.tb.inpost.dto.ProductResponseDTO;
import com.tb.inpost.model.Product;
import com.tb.inpost.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productService;	
	
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}



	@GetMapping
	public List<Product> productList() {

		return productService.getProductList();

	}
	
	@PostMapping("/{productId}/calculate")
	public ResponseEntity<ProductResponseDTO> getProductCalculate(@PathVariable UUID productId,
											@RequestBody ProductRequestDTO requestDto) {
		
		
		return productService.getProductCalculate(productId, requestDto);

	}

}
