package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.OrderDTO;
import jakarta.transaction.Transactional;

public interface OrderService {

    @Transactional
    OrderDTO placeOrder(String userEmail,
                        Long addressId,
                        String paymentMethod,
                        String pgName,
                        String pgPaymentId,
                        String pgStatus,
                        String pgResponseMessage);
}
