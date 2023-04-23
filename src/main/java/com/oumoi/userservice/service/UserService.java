package com.oumoi.userservice.service;


import com.oumoi.userservice.domain.User;
import com.oumoi.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserByUsername(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Invalid credentials given"));
    }
}
