package com.rk.olms.filters;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.exception.JwtTokenExpiredException;
import com.rk.olms.services.SecurityUserDetailService;
import com.rk.olms.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.rk.olms.utils.Utility.GSON;

@Component
@Slf4j
public class CustomFilterForAuthorization extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtility;

    @Autowired
    private SecurityUserDetailService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String mode = httpServletRequest.getHeader("mode");
        String token = null;
        String userName = null;
        try {

            if (null == mode) {
                mode = "ANONYMOUS";
            }

            if (null != authorization && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                userName = jwtUtility.getUsernameFromToken(token);

                Boolean isTokenExpired = jwtUtility.isTokenExpired(token);

                if(isTokenExpired){
                    throw new JwtTokenExpiredException("Token Expired, generate new token");
                }
            }

            if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityUserDetails userDetails
                        = userService.loadUserByUsername(userName);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }
        } catch (Exception ex) {
            log.error("exception is ::", ex);
            if(ex instanceof JwtTokenExpiredException || ex instanceof BadCredentialsException){
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ResponseDto<String> exceptionResponse = new ResponseDto<>();
            exceptionResponse.setRd(ex.getMessage());
            httpServletResponse.getWriter().write(GSON.toJson(exceptionResponse));
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}