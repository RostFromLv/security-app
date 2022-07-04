package com.learn.security;

import com.learn.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.io.Serializable;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider implements Serializable {

  private static final long serialVersionUID = 3522491090480550716L;

  private final SecretKey accessSecret;
  private final SecretKey refreshSecret;

  public JwtProvider(@Value("${jwt.secret.access}") String accessSecret,
                     @Value("${jwt.secret.access}") String refreshSecret) {
    this.accessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
    this.refreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret));
  }


  public String generateAccessToken(UserDto userDto){
     LocalDateTime now = LocalDateTime.now();
     Instant accessExpirationInstant = now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
     Date accessExpiration = Date.from(accessExpirationInstant);
    return Jwts.builder()
        .setSubject(userDto.getEmail())
        .setExpiration(accessExpiration)
        .signWith(accessSecret)
        .claim("email",userDto.getEmail())
        .claim("password",userDto.getPassword())
        .compact();
  }

  public String generateRefreshToken(UserDto userDto){
     LocalDateTime now = LocalDateTime.now();
     Instant refreshExpirationInstant = now.plusDays(25).atZone(ZoneId.systemDefault()).toInstant();
     Date refreshExpiration = Date.from(refreshExpirationInstant);
     return Jwts.builder()
         .setSubject(userDto.getEmail())
         .setExpiration(refreshExpiration)
         .signWith(refreshSecret)
         .compact();
  }

  public boolean validateAccessToken(String accessToken){
    return validateToken(accessToken,accessSecret);
  }
  public boolean validateRefreshToken(String accessToken){
    return validateToken(accessToken,accessSecret);
  }

  private boolean validateToken(String token, Key secret){
    try {
      Jwts.parserBuilder()
          .setSigningKey(secret)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException expEx) {
      log.error("Token expired", expEx);
    } catch (UnsupportedJwtException unsEx) {
      log.error("Unsupported jwt", unsEx);
    } catch (MalformedJwtException mjEx) {
      log.error("Malformed jwt", mjEx);
    } catch (Exception e) {
      log.error("invalid token", e);
    }
    return false;
  }

  public Claims getAccessClaims(String token){
    return getClaims(token,refreshSecret);
  }

  public Claims getRefreshClaims(String token){
    return getClaims(token,refreshSecret);
  }

  private Claims getClaims (String token,Key secret){
    return Jwts.parserBuilder()
        .setSigningKey(secret)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

}
