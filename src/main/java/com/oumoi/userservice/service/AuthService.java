package com.oumoi.userservice.service;


import com.oumoi.userservice.domain.User;
import com.oumoi.userservice.domain.dto.LoginRequestDTO;
import com.oumoi.userservice.domain.dto.TokenResponseDTO;
import com.oumoi.userservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final UserDetailService userDetailService;

    private final AuthenticationManager authenticationManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {

        logger.warn("request is here");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()
                    )
            );

            logger.warn("now here");
        }catch (UsernameNotFoundException exception){
            logger.info("User not found exception");
        }

        UserDetails user = this.userDetailService.loadUserByUsername(loginRequestDTO.getEmail());

        String token = this.jwtUtil.generateToken(user);

        return new TokenResponseDTO(token);
    }
}
