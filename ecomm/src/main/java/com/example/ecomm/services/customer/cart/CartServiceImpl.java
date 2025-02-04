package com.example.ecomm.services.customer.cart;

import com.example.ecomm.dto.AddProductInCartDto;
import com.example.ecomm.dto.CartItemsDto;
import com.example.ecomm.dto.OrderDto;
import com.example.ecomm.entity.CartItems;
import com.example.ecomm.entity.Order;
import com.example.ecomm.entity.Product;
import com.example.ecomm.entity.User;
import com.example.ecomm.enums.OrderStatus;
import com.example.ecomm.repository.CartItemsRepository;
import com.example.ecomm.repository.OrderRepository;
import com.example.ecomm.repository.ProductRepository;
import com.example.ecomm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl  implements  CartService{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartItemsRepository cartItemsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUser_id(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems=cartItemsRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProduct_id(), activeOrder.getId(), addProductInCartDto.getUser_id());

        if(optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        else {
            Optional<Product> optionalProduct=productRepository.findById(addProductInCartDto.getProduct_id());
            Optional<User> optionalUser=userRepository.findById(addProductInCartDto.getUser_id());

            if(optionalUser.isPresent() && optionalProduct.isPresent()) {
                CartItems cart=new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart=cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount()+cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount()+ cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);

            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found...");
            }
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);

        List<CartItemsDto> cartItemsDtos=activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        OrderDto orderDto=new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());

        orderDto.setCartItems(cartItemsDtos);

        return orderDto;
    }
}
