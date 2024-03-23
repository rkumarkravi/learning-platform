package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.models.UserEntity;
import com.rk.olms.daos.repos.UserEntityRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.UserLoginReqDto;
import com.rk.olms.dtos.requests.UserRegisterReqDto;
import com.rk.olms.dtos.responses.UserLoginResDto;
import com.rk.olms.dtos.responses.UserRegisterResDto;
import com.rk.olms.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    private final UserEntityRepository userEntityRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserEntityRepository userEntityRepository, AuthenticationManager authenticationManager, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseDto<UserRegisterResDto> register(UserRegisterReqDto userReq) {
        ResponseDto<UserRegisterResDto> responseDto = new ResponseDto<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userReq.getEmail());
        userEntity.setRole(userReq.getRole());
        userEntity.setUsername(userReq.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userReq.getPassword()));
        userEntity.setPhoneNumber(userReq.getPhoneNumber());
        userEntity.setInterests(String.join(",", userReq.getInterests()));
        userEntity.setFullName(userReq.getFullName());
        userEntity = userEntityRepository.save(userEntity);

        UserRegisterResDto userRegisterResDto = new UserRegisterResDto(userEntity);
        responseDto.setPayload(userRegisterResDto);
        responseDto.setRs("S");
        responseDto.setRd("Successful");

        return responseDto;
    }

    public ResponseDto<UserLoginResDto> login(UserLoginReqDto userReq) {
        ResponseDto<UserLoginResDto> responseDto = new ResponseDto<>();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userReq.getEmail(), userReq.getPassword()));
        if (authentication.isAuthenticated()) {
            Optional<UserEntity> userEntityOptional = Optional.of(((SecurityUserDetails) authentication.getPrincipal()).getUserEntity());
//            log.info("principal is :{} | credentials is:{}", authentication.getPrincipal(), authentication.getCredentials());
//            log.info("getAuthorities {}", authentication.getAuthorities());
//            log.info("getDetails {}", authentication.getDetails());
//            log.info("getName {}", authentication.getName());
            UserLoginResDto userResDto = new UserLoginResDto(userEntityOptional.get());

            Map<String, Object> claims = new HashMap<>();
            claims.put("emid", Base64.getEncoder().encode(userReq.getEmail().getBytes()));

            Map<String, String> tokens = jwtUtil.generateToken(userReq.getEmail(), claims);
            userResDto.setAt(tokens.get("authToken"));
            userResDto.setRt(tokens.get("refreshToken"));

            responseDto.setPayload(userResDto);
            responseDto.setRs("S");
            responseDto.setRd("Successful");
        } else {
            responseDto.setRd("Invalid Credentials!");
        }
        return responseDto;
    }
}
