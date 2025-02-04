package com.example.ecomm.services.customer;

import com.example.ecomm.dto.ProductDto;
import com.example.ecomm.entity.Product;
import com.example.ecomm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList=productRepository.findAll();
        return productList.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductByName(String name) {
        List<Product> productList=productRepository.findAllByNameContaining(name);
        return productList.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
