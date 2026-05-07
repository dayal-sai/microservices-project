package com.auth.server.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	
    private final String SECRET = "my-super-secret-key-which-is-very-long-12345";

	    public String generateToken(String username, String role) {
	        return Jwts.builder()
	                .setSubject(username)
	                .claim("role", role)   // ADD ROLE
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
	                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
	                .compact();
	    }

	    public String extractUsername(String token) {
	        return Jwts.parser()
	                .setSigningKey(SECRET)
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
	    
}
