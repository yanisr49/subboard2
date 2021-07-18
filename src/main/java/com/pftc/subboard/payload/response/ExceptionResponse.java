package com.pftc.subboard.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExceptionResponse extends Response {
    private Integer status;
    private String error;
    private String exceptionClass;
    private String message;
    private String path;

    public ExceptionResponse(Integer status, String error, Class<?> exceptionClass, String message, String path) {
        super();
        
        this.status = status;
        this.error = error;
        this.exceptionClass = exceptionClass.getName();
        this.message = message;
        this.path = path;
    }
}
