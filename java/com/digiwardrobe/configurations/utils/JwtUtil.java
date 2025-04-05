package com.digiwardrobe.configurations.utils;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.digiwardrobe.data_access.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/*
 * A simple class to create new JWT tokens for users
 * 
 * generates and verifies the validity of provided JWT tokens
*/
@Component
public class JwtUtil {

    SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") final String base64Secret) {
        final byte[] decodedKey = Base64.getDecoder().decode(base64Secret);
        this.secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
        System.out.println("JWT Secret Key (Base64): " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
    }

    public String generateToken(final UserEntity user) {
        return Jwts.builder()
                .issuer("digiwardrobe") // TODO: think of a better issuer name + env file?
                .subject(user.getEmail()) // TODO: id instead of email?
                .issuedAt(new Date())
                /*
                 * .expiration(new Date(System.currentTimeMillis() + 86400000))
                 * TODO: valid for a day
                 */
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> parseToken(final String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}
