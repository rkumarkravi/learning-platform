package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.models.UserEntity;
import com.rk.olms.daos.repos.UserRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.RenewTknReqDto;
import com.rk.olms.dtos.requests.UserLoginReqDto;
import com.rk.olms.dtos.requests.UserRegisterReqDto;
import com.rk.olms.dtos.responses.RenewTknResDto;
import com.rk.olms.dtos.responses.UserLoginResDto;
import com.rk.olms.dtos.responses.UserRegisterResDto;
import com.rk.olms.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.rk.olms.utils.Utility.B64EN;

@Service
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        userEntity = userRepository.save(userEntity);

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
            claims.put("emid", B64EN.encode(userReq.getEmail().getBytes()));
            claims.put("uid", 0L);
            claims.put("role", userEntityOptional.get().getRole());

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


    public ResponseDto<RenewTknResDto> renewTkn(RenewTknReqDto renewTknReqDto) {
        ResponseDto<RenewTknResDto> responseDto=new ResponseDto<>();
        Boolean isRtExpired=jwtUtil.isTokenExpired(renewTknReqDto.getRt());
        Boolean isAtExpired=jwtUtil.isTokenExpired(renewTknReqDto.getAt());
        Claims claimsRt =jwtUtil.getAllClaimsFromToken(renewTknReqDto.getRt());
        Claims claimsAt =jwtUtil.getAllClaimsFromToken(renewTknReqDto.getRt());

        Boolean isUidSame=claimsRt.get("uid").equals(claimsAt.get("uid"));

        if(isRtExpired){
            responseDto.setRs("99");
            responseDto.setRd("Please login again due to security reasons!");
            return responseDto;
        }

        if(jwtUtil.isRefreshToken(renewTknReqDto.getRt()) && isAtExpired && isUidSame){
            RenewTknResDto renewTknResDto=new RenewTknResDto();
            log.info("claims: {}",claimsRt);
            int uid = (int)claimsRt.get("uid");
            claimsRt.put("uid",++uid);

            String rt=jwtUtil.updateRefreshToken(renewTknReqDto.getRt(),claimsRt);
            String at=jwtUtil.doGenerateAuthToken(claimsRt.getSubject(),claimsRt);
            renewTknResDto.setAt(at);
            renewTknResDto.setRt(rt);

            responseDto.setPayload(renewTknResDto);
            responseDto.setRs("S");
            responseDto.setRs("Token Refreshed!");
        }

        return responseDto;
    }
}
