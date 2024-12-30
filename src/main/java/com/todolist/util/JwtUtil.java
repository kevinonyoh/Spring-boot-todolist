package com.todolist.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String secretKey = "your-secret-key";  // Replace with a stronger secret in production
    private long validityInMilliseconds = 3600000; // 1 hour
    private Algorithm algorithm = Algorithm.HMAC256(secretKey);  // Define algorithm

    // Generate JWT token with claims
    public String generateToken(String id, String email) {
        return JWT.create()
                .withClaim("id", id)  // Add user ID as a claim
                .withClaim("email", email)  // Add user email as a claim
                .withExpiresAt(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .sign(algorithm);
    }

    // Validate token and extract user ID
    public String extractId(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaim("id").asString();
    }

    // Validate if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

   
    public Date extractExpiration(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getExpiresAt();
    }

    
    public Map<String, String> validateTokenAndRetrieveSubject(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

           
            String id = jwt.getClaim("id").asString();
            String email = jwt.getClaim("email").asString();

           
            Map<String, String> claims = new HashMap<>();
            claims.put("id", id);
            claims.put("email", email);

            return claims;
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }
}
