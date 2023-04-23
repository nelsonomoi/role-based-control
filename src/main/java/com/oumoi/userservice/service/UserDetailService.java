package com.oumoi.userservice.service;

import com.oumoi.userservice.domain.User;
import com.oumoi.userservice.domain.UserPrincipal;
import com.oumoi.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Netokuonana +"+username);
        User user =  this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid user credentials given"));
        return new UserPrincipal(user);
    }
}
