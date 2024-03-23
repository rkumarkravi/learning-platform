package com.rk.olms.dtos.responses;

import com.rk.olms.daos.models.UserEntity;
import com.rk.olms.enums.Role;
import io.jsonwebtoken.lang.Collections;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class UserLoginResDto {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private List<String> interests;
    private Role role;
    private String at;
    private String rt;

    public UserLoginResDto(UserEntity userEntity) {
        this.setUserId(userEntity.getId());
        this.setUsername(userEntity.getUsername());
        this.setEmail(userEntity.getEmail());
        this.setFullName(userEntity.getFullName());
        this.setPhoneNumber(userEntity.getPhoneNumber());
        this.setInterests(Arrays.asList(userEntity.getInterests().split(",")));
        this.setRole(userEntity.getRole());
    }

    public UserLoginResDto() {
    }
}
