package com.unaiz.springkart.dto;

import com.unaiz.springkart.entity.Address;
import com.unaiz.springkart.entity.OrderItem;
import com.unaiz.springkart.entity.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {

    Long id;
    String email;
    List<OrderItemDTO> orderItems;
    AddressDTO address;
    PaymentDTO payment;
    LocalDateTime orderDate;
    Double totalAmount;
    String orderStatus;


}
