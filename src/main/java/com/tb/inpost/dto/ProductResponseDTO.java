package com.tb.inpost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductResponseDTO {
	
	private String name; 	
	
	private Integer amount;
	private Double discount;
	
	@JsonProperty("discount-percentage")
	private Double discountPercentage;
	
	@JsonProperty("total-price")
	private Double totalPrice;

}
