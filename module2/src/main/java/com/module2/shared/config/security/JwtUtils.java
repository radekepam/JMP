package com.module2.shared.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class JwtUtils {

    private JwtUtils() {
        //do not initialize
    }

    @Value("jtw.secret")
    private static final String JWT_SIGNING_KEY = "secretShouldBeRandomChainOfCharacters";
    private static final long EXPIRATION_TIME_MINUTES = 120;
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";

    static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SIGNING_KEY).parseClaimsJws(token).getBody();
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    private static String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(EXPIRATION_TIME_MINUTES)))
                .signWith(SignatureAlgorithm.HS256, JWT_SIGNING_KEY)
                .compact();
    }

    public static boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
