package com.rk.olms.dtos;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private String rs="F";
    private String rd="Failure";
    private T payload;
}
