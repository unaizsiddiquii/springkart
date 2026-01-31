package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.OrderDTO;
import com.unaiz.springkart.dto.OrderItemDTO;
import com.unaiz.springkart.entity.*;
import com.unaiz.springkart.exception.APIException;
import com.unaiz.springkart.exception.ResourceNotFoundException;
import com.unaiz.springkart.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    CartRepository cartRepository;
    AddressRepository addressRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    CartService cartService;
    ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(
            String userEmail,
            Long addressId,
            String paymentMethod,
            String pgName,
            String pgPaymentId,
            String pgStatus,
            String pgResponseMessage
    ) {

        Cart cart = cartRepository.findCartByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart", "email", userEmail));

        if (cart.getCartItems().isEmpty()) {
            throw new APIException("Cannot place order with empty cart");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address", "id", addressId));

        Order order = new Order();
        order.setEmail(userEmail);
        order.setAddress(address);
        order.setOrderStatus("Order Accepted");
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());

        Payment payment = new Payment(
                paymentMethod,
                pgName,
                pgPaymentId,
                pgResponseMessage,
                pgStatus
        );

        order.setPayment(payment);
        payment.setOrder(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setDiscount(item.getDiscount());
            orderItem.setOrderProductPrice(item.getProductPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        order = orderRepository.save(order);

        cart.getCartItems().forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
            cartService.deleteProductFromCart(
                    cart.getCartId(),
                    product.getProductId()
            );
        });

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderItems.forEach(oi ->
                orderDTO.getOrderItems()
                        .add(modelMapper.map(oi, OrderItemDTO.class))
        );

        return orderDTO;
    }
}
