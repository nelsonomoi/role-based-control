package com.oumoi.userservice.config;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    public static final String FORBIDDEN_ERROR = "Forbiden request";
    public static final String ACCESS_DENINED = "Access denined";
    public static final String NOT_FOUND="Invalid user credentials";
    public static final String DOES_NOT_EXIST="No records found in the system";

    public static final String AUTHORITIES="authorities";

    public static final long EXPIRATION_TIME = 18000000;
}
