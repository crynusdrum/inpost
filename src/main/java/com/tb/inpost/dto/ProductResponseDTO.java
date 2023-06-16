package com.tb.inpost.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
	
	private String name; 
	
	
	private Integer amount;
	private Double discount;
	private Double discountPercentage;
	private Double totalPrice;

}
