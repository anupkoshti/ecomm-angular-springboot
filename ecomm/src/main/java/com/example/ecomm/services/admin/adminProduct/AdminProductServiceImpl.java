package com.example.ecomm.services.admin.adminProduct;

import com.example.ecomm.dto.ProductDto;
import com.example.ecomm.entity.Category;
import com.example.ecomm.entity.Product;
import com.example.ecomm.repository.CategoryRepository;
import com.example.ecomm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product=new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImg().getBytes());

        Category category=categoryRepository.findById(productDto.getCategory_id()).orElseThrow();

        product.setCategory(category);
        // Save the product first
        Product savedProduct = productRepository.save(product);

        // Now return the DTO from the saved entity
        return savedProduct.getDto();
    }

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

    @Override
    public boolean deleteProduct(Long id) {

        Optional<Product> optionalProduct=productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
