package com.tb.inpost.model;



import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private Double price;

    public Product() {
    }

    public Product(String title) {
        this.title = title;
    }

	public Product(String title, Double price) {		
		this.title = title;
		this.price = price;
	}
	


}
