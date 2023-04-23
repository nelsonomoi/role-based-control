package com.oumoi.userservice.filters;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.oumoi.userservice.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

import static com.oumoi.userservice.config.SecurityConstants.AUTHORIZATION_PREFIX;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !StringUtils.startsWithIgnoreCase(authHeader,AUTHORIZATION_PREFIX)){
            logger.info("Invalid header");
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(AUTHORIZATION_PREFIX.length());

        String userEmail = jwtUtil.extractUsername(token);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            logger.info("TokenValid Status" +jwtUtil.isTokenValid(token,userEmail));
            if (jwtUtil.isTokenValid(token, userEmail)) {

                logger.info("niko hapa ndani");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

        }

        filterChain.doFilter(request,response);
    }
}
