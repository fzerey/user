package com.fzerey.user.domain.service.User;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${auth.jwt.secret}")
    private String secretKey;
    private final long tokenValidityInMilliseconds = 3600000;

    @Override
    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("sub", user.getSubId());
        var attributes = user.getUserAttributes();
        for (var attribute : attributes) {
            claims.put(attribute.getAttribute().getKey(), attribute.getValue());
        }
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

}
