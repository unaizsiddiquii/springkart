package com.unaiz.springkart.dto;

import com.unaiz.springkart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long cartId;
    private Double totalPrice = 0.0;
    List<ProductDTO> products = new ArrayList<>();
}
