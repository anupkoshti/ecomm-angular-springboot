package com.example.ecomm.services.customer;

import com.example.ecomm.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByName(String name);
}
