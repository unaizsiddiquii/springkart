package com.unaiz.springkart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

import java.util.List;

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

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}
