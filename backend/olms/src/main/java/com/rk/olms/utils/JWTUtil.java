package com.rk.olms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JWTUtil implements Serializable {

    public static final long JWT_AUTH_TOKEN_VALIDITY = 1 * 60 * 60;
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60;
    private static final long serialVersionUID = 234234523523L;
    @Value("${jwt.secret}")
    private String secretKey;

    public static String getTokenFromHeaderAuth(String authToken) {
        if (authToken.contains("Bearer")) {
            return authToken.substring(7);
        } else {
            return authToken;
        }
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Object getDataFromTokenByKey(String token, String key) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get(key);
    }

    public Long getUserIdFromToken(String token) {
        Long userId = Long.valueOf((Integer) this.getDataFromTokenByKey(this.getTokenFromHeaderAuth(token), "uid"));
        return userId;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        Claims claims = null;
        claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims;
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean isAuthToken(String token) {
        final String type = (String) getDataFromTokenByKey(token,"type");
        return type != null && type.equals("auth");
    }

    //generate token for user
    public Map<String,String> generateToken(String username, Map<String, Object> otherDetails) {

        Map<String, Object> claims = new HashMap<>(otherDetails);

        Map<String,String> data=new HashMap<>();
        claims.put("type","auth");
        data.put("authToken",doGenerateToken(claims,username));
        claims.put("type","refresh");
        data.put("refreshToken",doGenerateRefreshToken(claims,username));

        return data;
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        log.info("secret key: {}",secretKey);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_AUTH_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
