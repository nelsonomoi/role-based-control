package com.oumoi.userservice.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.oumoi.userservice.config.SecurityConfig;
import com.oumoi.userservice.config.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.oumoi.userservice.config.SecurityConstants.AUTHORITIES;
import static java.util.Arrays.stream;

@Component
public class JwtUtil {

    @Value("${jwt.token.secret}")
    private String secret;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Algorithm getSigningKey(){

        byte[] keyBytes = Base64.getDecoder().decode(secret);

        Algorithm algorithm = Algorithm.HMAC512(keyBytes);

        return algorithm;

    }

    public String generateToken(UserDetails userDetails) {
        String token = JWT.create()
                .withArrayClaim(AUTHORITIES,getAuthorities(userDetails))
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(getSigningKey());
        return token;
    }

    private String[] getAuthorities(UserDetails userDetails){

        List<String> authorities = new ArrayList<>();

        userDetails.getAuthorities().stream().forEach(grantedAuthority -> {
            authorities.add(String.valueOf(grantedAuthority));
        });

        return authorities.toArray(new String[0]);
    }

    public JWTVerifier verifier(){



        return JWT.require(getSigningKey())
                .acceptLeeway(1)
                .build();
    }


    public boolean isTokenValid(String token,String username){
        JWTVerifier verifier = verifier();

        logger.info("authorities"+String.valueOf(verifier.verify(token).getClaim("authorities")));

        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        JWTVerifier verifier =  verifier();
        return verifier.verify(token).getExpiresAt().before(new Date());
    }


    public String extractUsername(String token){
        JWTVerifier verifier =  verifier();
        return verifier.verify(token).getSubject();
    }
}

