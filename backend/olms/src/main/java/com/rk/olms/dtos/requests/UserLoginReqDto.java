package com.rk.olms.dtos.requests;

import com.rk.olms.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserLoginReqDto {
    private String password;
    private String email;
}
