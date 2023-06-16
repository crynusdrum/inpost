package com.tb.inpost.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tb.inpost.dto.PriceAmountDtoChat;
import com.tb.inpost.dto.ProductRequestDTO;
import com.tb.inpost.dto.ProductResponseDTO;
import com.tb.inpost.model.Product;
import com.tb.inpost.repository.ProductRepository;

@Service
public class ProductService {
	
	
	private final ProductRepository productRepository;
	
	
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getProductList(){
		
		return productRepository.findAll();
	}
	
	public Product getProduct(UUID id) {
		
		Optional<Product> item = productRepository.findById(id);
		
		return item.isPresent() ? item.get() : null;
	
	}
	
	
	public ResponseEntity<ProductResponseDTO> getProductCalculate(UUID productId, ProductRequestDTO productRequestDTO){
		
		
        Product product = getProduct(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // Apply count-based discount
        double totalPrice = product.getPrice() * productRequestDTO.getAmount();
        double discount = calculateCountBasedDiscount(product, productRequestDTO.getAmount());
        totalPrice -= discount;

        // Apply percentage-based discount
        double percentageDiscount = calculatePercentageBasedDiscount(product);
        totalPrice -= (totalPrice * percentageDiscount);

//        PriceAmountDtoChat resultDto = new PriceAmountDtoChat(totalPrice, productRequestDTO.getAmount());
//        return ResponseEntity.ok(resultDto);
		
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		
		productResponseDTO.setName(product.getTitle());
		productResponseDTO.setAmount(productRequestDTO.getAmount());
		productResponseDTO.setTotalPrice(totalPrice);
		productResponseDTO.setDiscount(discount);
		productResponseDTO.setDiscountPercentage(percentageDiscount);
		
		return ResponseEntity.ok(productResponseDTO);
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

}
