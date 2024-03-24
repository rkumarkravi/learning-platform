package com.rk.olms.controllers;

import com.rk.olms.dtos.requests.RenewTknReqDto;
import com.rk.olms.dtos.requests.UserLoginReqDto;
import com.rk.olms.dtos.requests.UserRegisterReqDto;
import com.rk.olms.dtos.responses.RenewTknResDto;
import com.rk.olms.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Object userRegister(@RequestBody UserRegisterReqDto userRegisterDto){
        return authService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public Object userLogin(@RequestBody UserLoginReqDto userLoginReqDto){
        return authService.login(userLoginReqDto);
    }

    @PostMapping("/renewTkn")
    public Object renewTkn(@RequestBody RenewTknReqDto renewTknReqDto){
        return authService.renewTkn(renewTknReqDto);
    }
}
