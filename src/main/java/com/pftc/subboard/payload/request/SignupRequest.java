package com.pftc.subboard.payload.request;

import com.pftc.subboard.payload.interfaces.IRequest;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest implements IRequest {
    private String username;    
    private String password;

    @Override
    public void validate() throws IllegalArgumentException {
        // User's username can't be null or empty
        if(!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username must be defined");
        }
        // User's password can't be null or empty
        if(!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password must be defined");
        }
    }  
}
