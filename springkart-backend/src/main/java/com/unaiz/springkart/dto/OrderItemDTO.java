package com.unaiz.springkart.dto;


import com.unaiz.springkart.entity.Order;
import com.unaiz.springkart.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDTO {
    Long id;
    ProductDTO product;
    Integer quantity;
    Double discount;
    Double orderProductPrice;
}
