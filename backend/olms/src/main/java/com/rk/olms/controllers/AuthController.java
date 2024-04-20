package com.rk.olms.controllers;

import com.rk.olms.dtos.requests.RenewTknReqDto;
import com.rk.olms.dtos.requests.UserLoginReqDto;
import com.rk.olms.dtos.requests.UserRegisterReqDto;
import com.rk.olms.exception.JwtTokenExpiredException;
import com.rk.olms.services.AuthService;
import org.springframework.web.bind.annotation.*;

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
    public Object userLogin(@RequestBody UserLoginReqDto userLoginReqDto) {
        return authService.login(userLoginReqDto);
    }

    @PostMapping("/renewTkn")
    public Object renewTkn(@RequestBody RenewTknReqDto renewTknReqDto) {
        return authService.renewTkn(renewTknReqDto);
    }

    @PostMapping("/validTkn")
    public Object validTkn(@RequestHeader("Authorization") String auth) throws JwtTokenExpiredException {
        if (auth.contains("Bearer ")) {
            auth = auth.replace("Bearer ", "");
        }
        return authService.validateTkn(auth);
    }
}
