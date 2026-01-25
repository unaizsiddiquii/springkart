package com.unaiz.springkart.util;

import com.unaiz.springkart.entity.User;
import com.unaiz.springkart.exception.ResourceNotFoundException;
import com.unaiz.springkart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public User loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));
    }

    public Long loggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));
        return user.getUserId();
    }

    public String loggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));
        return user.getEmail();
    }

}
