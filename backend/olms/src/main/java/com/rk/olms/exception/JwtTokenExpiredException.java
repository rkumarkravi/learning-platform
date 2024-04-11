package com.rk.olms.exception;

public class JwtTokenExpiredException extends Exception {
    public JwtTokenExpiredException() {
        super();
    }

    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
