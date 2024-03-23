package com.rk.olms.dtos.requests;

import com.rk.olms.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserRegisterReqDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private List<String> interests;
    private Role role;
}
