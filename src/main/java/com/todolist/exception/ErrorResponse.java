package com.todolist.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data 
public class ErrorResponse {

    private HttpStatus status;
    private String message;   

   
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
