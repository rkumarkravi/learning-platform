package com.rk.olms.dtos.responses;

import com.rk.olms.daos.models.UserEntity;
import com.rk.olms.enums.Role;
import lombok.Data;

@Data
public class UserRegisterResDto {
    private Long userId;
    private String email;
    private Role role;

    public UserRegisterResDto(UserEntity userEntity) {
        this.setUserId(userEntity.getId());
        this.setEmail(userEntity.getEmail());
        this.setRole(userEntity.getRole());
    }
}
