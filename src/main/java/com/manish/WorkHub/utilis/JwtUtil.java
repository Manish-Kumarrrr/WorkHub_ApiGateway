package com.manish.WorkHub.utilis;

import com.manish.WorkHub.dto.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @PostConstruct
    public void init() {
        System.out.println("JwtUtil initialized@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    @Value("${secret.key}")
    private String SECRET_KEY;

    /**
     * Returns the signing key used for JWT token signing and verification.
     *
     * @return SecretKey for JWT signing
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Extracts the user Email (subject) from the JWT token.
     *
     * @param token The JWT token
     * @return The extracted user Email
     */
    public String extractEmail(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (JwtException e) {
            throw new JwtException("Error extracting Email from token: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token
     * @return The expiration date of the token
     */
    public Date extractExpiration(String token) {
        try {
            return extractAllClaims(token).getExpiration();
        } catch (JwtException e) {
            throw new JwtException("Error extracting expiration date from token: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token
     * @return Claims containing all the details encoded in the token
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(getSigningKey())
                    .build().parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has expired", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token", e);
        } catch (MalformedJwtException e) {
            throw new JwtException("Malformed JWT token", e);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims string is empty", e);
        }
    }

    /**
     * Generates a JWT token for a given user.
     *
     * @param user The user for whom the token is generated
     * @return The generated JWT token
     */
    public String generateToken(User user) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", user.getRole());
            return createToken(claims, user.getEmail());
        } catch (Exception e) {
            throw new JwtException("Error generating token: " + e.getMessage(), e);
        }
    }

    /**
     * Creates the JWT token with custom claims and subject (user Email).
     *
     * @param claims Custom claims to be included in the token
     * @param Email The subject (user Email) of the token
     * @return The created JWT token
     */
    private String createToken(Map<String, Object> claims, String Email) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(Email)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50))
                    .setHeaderParam("typ", "JWT")
                    .signWith(getSigningKey())
                    .compact();
        } catch (Exception e) {
            throw new JwtException("Error creating token: " + e.getMessage(), e);
        }
    }

    /**
     * Validates the JWT token by checking its expiration.
     *
     * @param token The JWT token
     * @return True if the token is valid (not expired), false otherwise
     */
    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException e) {
            throw new JwtException("Error validating token: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token The JWT token
     * @return True if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (JwtException e) {
            throw new JwtException("Error checking token expiration: " + e.getMessage(), e);
        }
    }
}
