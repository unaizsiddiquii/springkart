package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.CartDTO;
import com.unaiz.springkart.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CartService {

    @Transactional
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String email);

    @Transactional
    CartDTO updateCartProductQuantity(Long productId, Integer quantity, User user);

    @Transactional
    String deleteProductFromCart(Long cartId, Long productId);
}
