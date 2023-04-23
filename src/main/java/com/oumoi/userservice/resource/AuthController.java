package com.oumoi.userservice.resource;


import com.oumoi.userservice.domain.dto.LoginRequestDTO;
import com.oumoi.userservice.domain.dto.TokenResponseDTO;
import com.oumoi.userservice.service.AuthService;
import com.oumoi.userservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        logger.info("Request allowed");
        return new ResponseEntity<>(this.authService.login(loginRequestDTO), HttpStatus.OK);
    }

}
