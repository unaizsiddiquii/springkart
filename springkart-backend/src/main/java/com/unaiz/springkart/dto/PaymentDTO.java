package com.unaiz.springkart.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDTO {

     Long id;
    String paymentMethod;
    String pgPaymentId;
    String pgStatus;
    String pgResponseMessage;
    String pgName;
}
