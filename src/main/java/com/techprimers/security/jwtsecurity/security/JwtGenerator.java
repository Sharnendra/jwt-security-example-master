package com.techprimers.security.jwtsecurity.security;

import org.springframework.stereotype.Component;

import com.techprimers.security.jwtsecurity.model.JwtUser;
import com.techprimers.security.jwtsecurity.model.JwtUser2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {


    public String generate(JwtUser jwtUser) {


        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getUserName());
        claims.put("userId", String.valueOf(jwtUser.getId()));
        claims.put("role", jwtUser.getRole());

        System.err.println("JSON genetrated token : "+Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "youtube")
                .compact());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "youtube")
                .compact();
    }
    
    public String generate2(JwtUser2 jwtUser) {


        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getUserName_new());
        claims.put("userId", String.valueOf(jwtUser.getId_new()));
        claims.put("role", jwtUser.getRole_new());

        System.err.println("JSON genetrated token : "+Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "youtube")
                .compact());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "youtube")
                .compact();
    }
}
