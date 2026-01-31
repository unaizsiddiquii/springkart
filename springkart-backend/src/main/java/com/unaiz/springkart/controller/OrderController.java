package com.unaiz.springkart.controller;

import com.unaiz.springkart.dto.OrderDTO;
import com.unaiz.springkart.dto.OrderRequestDTO;
import com.unaiz.springkart.service.OrderService;
import com.unaiz.springkart.util.AuthUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@RequestMapping("/api")
public class OrderController {

    OrderService orderService;
    AuthUtil authUtil;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable String paymentMethod,
                                               @RequestBody OrderRequestDTO orderRequestDTO) {
        String email = authUtil.loggedInUserEmail();
        OrderDTO orderDTO = orderService.placeOrder(
                email,
                orderRequestDTO.getAddressId(),
                paymentMethod,
                orderRequestDTO.getPgName(),
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage()

        );
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
