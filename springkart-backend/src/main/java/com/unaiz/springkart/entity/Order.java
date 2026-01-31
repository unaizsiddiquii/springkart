package com.unaiz.springkart.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Email
    @Column(nullable = false)
    String email;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    Address address;

    // ✅ OWNING SIDE
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    Payment payment;

    @CurrentTimestamp
    LocalDateTime orderDate;

    Double totalAmount;
    String orderStatus;
}
