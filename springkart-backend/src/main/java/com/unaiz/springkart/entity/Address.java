package com.unaiz.springkart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String buildingName;
    private String city;
    private String country;
    private String pinCode;
    private String state;
    private String street;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
