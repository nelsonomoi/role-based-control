package com.oumoi.userservice.exceptions;


import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

//    @ExceptionHandler(value = {UserPrincipalNotFoundException.class, UsernameNotFoundException.class})
//    public ResponseEntity<Object> userNotFoundException(UsernameNotFoundException e){
//        logger.info("custom exception class");
//        return new ResponseEntity<>("User not found", HttpStatus.OK);
//    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> authenticationException(AuthenticationException e){

        ApiError apiError =  new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {TokenExpiredException.class})
    public ResponseEntity<Object> tokenExpiredException(TokenExpiredException e){
        ApiError apiError =  new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.toString(),
                e.getMessage()
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> accessDeniedException(AccessDeniedException e){
        ApiError apiError =  new ApiError(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.toString(),
                e.getMessage()
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}
