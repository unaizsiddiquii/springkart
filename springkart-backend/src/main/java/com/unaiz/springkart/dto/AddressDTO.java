package com.unaiz.springkart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {


    private Long addressId;

    @NotBlank(message = "Building name is required")
    @Size(max = 100, message = "Building name is too long (max 100)")
    private String buildingName;

    @NotBlank(message = "Street is required")
    @Size(max = 150, message = "Street name is too long (max 150)")
    private String street;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 letters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(min = 2, max = 50, message = "State name must be between 2 and 50 letters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 50, message = "Country name must be between 2 and 50 letters")
    private String country;

    @NotNull(message = "Pin code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pin code must be exactly 6 numbers")
    private String pinCode;
}
