package com.unaiz.springkart.repository;

import com.unaiz.springkart.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    Optional<CartItem> findCartItemByCartIdAndProductId(Long cartId, Long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    void deleteCartItemByCartIdAndProductId(Long cartId, Long productId);
}