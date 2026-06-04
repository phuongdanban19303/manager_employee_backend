package l3_manager_employee.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import l3_manager_employee.Enity.TblUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${Ocean.jwt.secretKey}")
    private String secretKey;

    @Value("${Ocean.jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${Ocean.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Integer getUserId(String token) {
        return Integer.valueOf(parseToken(token).getSubject());
    }

    public String getRole(String token) {
        return parseToken(token).get("role", String.class);
    }

    public String getTeam(String token) {
        return parseToken(token).get("team", String.class);
    }

    public Date getExpiration(String token) {
        return parseToken(token).getExpiration();
    }

    public long getRemainingTime(String token) {
        Date expiration = getExpiration(token);
        return expiration.getTime() - System.currentTimeMillis();
    }

    public String generateAccessToken(TblUser user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("role", user.getRole())
                .claim("team", user.getTeam())
                .claim("type", "ACCESS")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(TblUser user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("type", "REFRESH")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isAccessToken(String token) {
        String type = parseToken(token).get("type", String.class);
        return "ACCESS".equals(type);
    }

    public boolean isRefreshToken(String token) {
        String type = parseToken(token).get("type", String.class);
        return "REFRESH".equals(type);
    }
}