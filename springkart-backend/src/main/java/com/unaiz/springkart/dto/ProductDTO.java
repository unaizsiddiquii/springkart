package com.unaiz.springkart.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String productName;
    private String image;

    @NotBlank(message = "Product description cannot be empty")
    @Size(min = 6, message = "Product description must contain at least 6 characters")
    private String description;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @PositiveOrZero(message = "Special price cannot be negative")
    private double specialPrice;

    @Min(value = 0, message = "Discount cannot be less than 0")
    @Max(value = 95, message = "Discount cannot be more than 95%")
    private double discount;

}
