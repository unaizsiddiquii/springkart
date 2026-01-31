package com.unaiz.springkart.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // ✅ INVERSE SIDE
    @OneToOne(mappedBy = "payment")
    Order order;

    String paymentMethod;
    String pgPaymentId;
    String pgStatus;
    String pgResponseMessage;
    String pgName;

    public Payment(String paymentMethod, String pgName, String pgPaymentId, String pgResponseMessage, String pgStatus) {
        this.paymentMethod = paymentMethod;
        this.pgName = pgName;
        this.pgPaymentId = pgPaymentId;
        this.pgResponseMessage = pgResponseMessage;
        this.pgStatus = pgStatus;
    }
}
