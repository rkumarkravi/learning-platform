package com.rk.olms.configs.security;

import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.exception.JwtTokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler({BadCredentialsException.class, JwtTokenExpiredException.class})
    public ResponseEntity<ResponseDto<Void>> badCreds(Exception ex) {
        ResponseDto<Void> response = new ResponseDto<>();
        response.setRd(ex.getMessage() + ", Please try again!");
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto<Void> generalHandling(Exception ex) {
        ResponseDto<Void> response = new ResponseDto<>();
        response.setRd("Oops Something went wrong, Please try again!");
        ex.printStackTrace();
        return response;
    }
}
