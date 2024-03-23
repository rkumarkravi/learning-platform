package com.rk.olms.configs.security;

import com.rk.olms.dtos.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseDto<Void> generalHandling(Exception ex){
        ResponseDto<Void> response = new ResponseDto<>();
        response.setRd("Oops Something went wrong!");
        ex.printStackTrace();
        return response;
    }
}
