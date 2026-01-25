package com.unaiz.springkart.controller;

import com.unaiz.springkart.dto.CartDTO;
import com.unaiz.springkart.entity.User;
import com.unaiz.springkart.repository.CartRepository;
import com.unaiz.springkart.service.CartService;
import com.unaiz.springkart.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    CartRepository cartRepository;

    @Autowired
    AuthUtil authUtil;


    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity) {

        return ResponseEntity
                .ok(cartService.addProductToCart(productId, quantity));
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> getCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.FOUND);
    }

    @GetMapping("/users/cart")
    public ResponseEntity<CartDTO> getCartById() {
        String email = authUtil.loggedInUserEmail();
        return new ResponseEntity<>(cartService.getCart(email), HttpStatus.FOUND);
    }

    @PatchMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProductQuantity(@PathVariable("productId") Long productId,
                                                             @PathVariable("operation") String operation) {
        User user = authUtil.loggedInUser();
        Integer quantity = operation.equals("delete") ? -1 : 1;
        return new ResponseEntity<>(cartService.updateCartProductQuantity(productId, quantity, user), HttpStatus.OK);

    }

    @DeleteMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId){
        String email = authUtil.loggedInUserEmail();
        return new ResponseEntity<>(cartService.deleteProductFromCart(cartId, productId), HttpStatus.OK);
    }

}
