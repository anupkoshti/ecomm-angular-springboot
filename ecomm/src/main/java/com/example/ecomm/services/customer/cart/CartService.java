package com.example.ecomm.services.customer.cart;

import com.example.ecomm.dto.AddProductInCartDto;
import com.example.ecomm.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);
}
