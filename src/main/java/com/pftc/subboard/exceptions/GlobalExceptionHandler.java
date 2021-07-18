package com.pftc.subboard.exceptions;

import com.pftc.subboard.payload.response.ExceptionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException e, WebRequest request) {
        logger.debug("IllegalArgumentException", e.getCause());

        String uri = request.getDescription(false).split("=")[1];
        return ResponseEntity.badRequest().body(new ExceptionResponse(400, "Bad Request", IllegalArgumentException.class, e.getMessage(), uri));
    }  

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handleException(UserAlreadyExistsException e, WebRequest request) {
        logger.debug("UserAlreadyExistsException", e.getCause());
        
        String uri = request.getDescription(false).split("=")[1];
        return ResponseEntity.badRequest().body(new ExceptionResponse(400, "Bad Request", UserAlreadyExistsException.class, "User already exists !",uri));
    }
}
