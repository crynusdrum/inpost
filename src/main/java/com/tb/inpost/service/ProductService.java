package com.tb.inpost.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

        // Applying count Based Discount Policy
        Double totalPrice = product.getPrice() * productRequestDTO.getAmount();
        Double discount = countBasedDiscountPolicy(product, productRequestDTO.getAmount());

        totalPrice = totalPrice - discount;

        // Applying percentage Based Discount Policy
        Double percentageDiscount = percentageBasedDiscountPolicy(product);

        totalPrice = totalPrice - (totalPrice * percentageDiscount);

		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		
		productResponseDTO.setName(product.getTitle());
		productResponseDTO.setAmount(productRequestDTO.getAmount());
		productResponseDTO.setTotalPrice(totalPrice);
		productResponseDTO.setDiscount(discount);
		productResponseDTO.setDiscountPercentage(percentageDiscount);
		
		return ResponseEntity.ok(productResponseDTO);
	}
	
	
	/**
	 * 
	 * countBasedDiscountPolicy (the more pieces of the product are ordered, the bigger the discount is) 
     * Example: Apply 5% discount for every 10 units purchased
	 * 
	 * 
	 * @param product
	 * @param amount
	 * @return
	 */
	Double countBasedDiscountPolicy(Product product, Integer amount) {

    	Integer discountUnit = 10;
        Double discountPercentage = 0.05;

        int discountQuantity = amount / discountUnit;
        return discountQuantity * (product.getPrice() * discountPercentage);
    }
    
    
    /**
     * 
     * percentageBasedDiscountPolicy (discount basen on percentage)
     * Example: 10%
     * 
     * 
     * @param product
     * @return
     */
	Double percentageBasedDiscountPolicy(Product product) {

    	Double discountPercentage = 0.1;
        return discountPercentage;
    }

}
