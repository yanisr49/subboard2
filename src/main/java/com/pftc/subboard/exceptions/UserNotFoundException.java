package com.pftc.subboard.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1208088191233435107L;

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}