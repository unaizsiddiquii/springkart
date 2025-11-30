package com.unaiz.springkart.security.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "Username is already required")
    @Size(min = 3, max = 20, message = "Username must be 3-120 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Size(min = 6, max = 50, message = "Email must be between 6–50 characters")
    @Email(message = "Invalid email format")
    private String email;

    private Set<String> role;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 40, message = "Password must be 6–40 characters")
    private String password;

}