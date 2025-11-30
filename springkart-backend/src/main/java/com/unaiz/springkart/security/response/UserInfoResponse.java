package com.unaiz.springkart.security.response;

import lombok.Getter;
import java.util.List;

@Getter
public class UserInfoResponse {
    private final Long id;
    private final String username;
    private final List<String> roles;
//    private String jwtToken;

    public UserInfoResponse(Long id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}


