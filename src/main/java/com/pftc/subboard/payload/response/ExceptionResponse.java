package com.pftc.subboard.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExceptionResponse extends Response {
    private String exceptionClass;

    public ExceptionResponse(String message, Class<?> exceptionClass) {
        super(message);
        this.exceptionClass = exceptionClass.getName();
    }
}
