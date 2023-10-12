package com.tuan.blogs.jwt;

import com.tuan.blogs.security.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.expiration}")
    private int JWT_EXPIRATION;

    public String generateToken(CustomUserDetail customUserDetail) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+ JWT_EXPIRATION);
        // create jwt from  only field in User (username, id, email) with setSubject
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.ES256, JWT_SECRET)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid Jwt Token" + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired Jwt Token" + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported Jwt Token" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty" + ex.getMessage());
        }
        return false;
    }
}
