package com.tb.inpost.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.tb.inpost.dto.ProductRequestDTO;
import com.tb.inpost.dto.ProductResponseDTO;
import com.tb.inpost.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Test
    void calculatePriceAmount_Returns200() {
        // Arrange
        ProductController controller = new ProductController(productService);
        UUID productId = UUID.randomUUID();
       // CalculateRequestDto requestDto = new CalculateRequestDto(5); // Example amount
        ProductRequestDTO requestDto = new ProductRequestDTO();
        requestDto.setAmount(5);

        // Mocking product and service behavior
        //Product product = new Product("Product 1", 100.0);
        
        ResponseEntity<ProductResponseDTO> responseDTO = new ResponseEntity<ProductResponseDTO>(HttpStatusCode.valueOf(200));
        
        when(productService.getProductCalculate(productId, requestDto)).thenReturn(responseDTO);

        ResponseEntity<ProductResponseDTO> response = controller.getProductCalculate(productId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

