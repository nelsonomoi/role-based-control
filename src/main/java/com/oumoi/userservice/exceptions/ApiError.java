package com.oumoi.userservice.exceptions;


import lombok.Data;

@Data
public class ApiError {
    private int httpStatusCode;
    private String status;
    private String message;

    public ApiError(int httpStatusCode, String message){
        this.httpStatusCode = httpStatusCode;
        this.message= message;
    }

    public ApiError(int httpStatusCode,String status, String message){
        this.httpStatusCode = httpStatusCode;
        this.status=status;
        this.message= message;
    }
}
