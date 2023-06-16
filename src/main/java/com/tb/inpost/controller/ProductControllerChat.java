package com.tb.inpost.controller;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tb.inpost.dto.CalculateRequestDtoChat;
import com.tb.inpost.dto.PriceAmountDtoChat;
import com.tb.inpost.dto.ProductDtoChat;
import com.tb.inpost.model.Product;
import com.tb.inpost.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductControllerChat {

    private final ProductService productService;

    public ProductControllerChat(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDtoChat> getProduct(@PathVariable UUID productId) {
        Product product = productService.getProduct(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        ProductDtoChat productDto = convertToDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/{productId}/calculateChat")
    public ResponseEntity<PriceAmountDtoChat> calculatePriceAmount(@PathVariable UUID productId,
            													@RequestBody CalculateRequestDtoChat requestDto) {

        Product product = productService.getProduct(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // Apply count-based discount
        double totalPrice = product.getPrice() * requestDto.getAmount();
        double discount = calculateCountBasedDiscount(product, requestDto.getAmount());
        totalPrice -= discount;

        // Apply percentage-based discount
        double percentageDiscount = calculatePercentageBasedDiscount(product);
        totalPrice -= (totalPrice * percentageDiscount);

        PriceAmountDtoChat resultDto = new PriceAmountDtoChat(totalPrice, requestDto.getAmount());
        return ResponseEntity.ok(resultDto);
    }

    private double calculateCountBasedDiscount(Product product, int amount) {
        // Your count-based discount calculation logic here
        // Example: Apply 5% discount for every 10 units purchased
        int discountUnit = 10;
        double discountPercentage = 0.05;

        int discountQuantity = amount / discountUnit;
        return discountQuantity * (product.getPrice() * discountPercentage);
    }

    private double calculatePercentageBasedDiscount(Product product) {
        // Your percentage-based discount calculation logic here
        // Example: Apply 10% discount for all products
        double discountPercentage = 0.1;
        return discountPercentage;
    }

    // Convert Product entity to DTO for response
    private ProductDtoChat convertToDto(Product product) {
        // Implementation of conversion from Product to ProductDto
    	var productDTO = new ProductDtoChat();
    	BeanUtils.copyProperties(product, productDTO);
    	
    	return productDTO;
    }
}

