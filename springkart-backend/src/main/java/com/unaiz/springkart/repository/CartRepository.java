package com.unaiz.springkart.repository;

import com.unaiz.springkart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.email = :email")
    Optional<Cart> findCartByEmail(@Param("email") String email);

    @Query("SELECT c FROM Cart c WHERE c.user.email = :email AND c.cartId = :cartId")
    Optional<Cart> findByEmailAndCartId(String email, Long cartId);

//    @Query("SELECT c FROM Cart c WHERE c.cartItems.")
//    List<Cart> findCartByProductId(Long productId);
}