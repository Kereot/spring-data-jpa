package com.gb.market.auth.services;

import com.gb.market.auth.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties properties;
    public String generateToken(UserDetails user) {
        String username = user.getUsername();
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Map<String, Object> claims = new HashMap<>(Map.of("authorities", authorities));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + properties.getExpireTime().toMillis()))
                .signWith(SignatureAlgorithm.HS256, properties.getSecret())
                .compact();
    }

//    public String getUsername(String bearerTokenValue) {
//        return parse(bearerTokenValue).getSubject();
//    }
//
//    public List<? extends GrantedAuthority> getAuthorities(String bearerTokenValue) {
//        List<String> authorities = (List<String>) parse(bearerTokenValue).get("authorities");
//        return authorities.stream()
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//    }
//
//    private Claims parse(String bearerTokenValue) {
//        return Jwts.parser()
//                .setSigningKey(properties.getSecret())
//                .parseClaimsJws(bearerTokenValue)
//                .getBody();
//    }
}
