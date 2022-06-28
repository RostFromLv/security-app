package com.learn.security;

import io.jsonwebtoken.Claims;

public class JwtUtils {

  public static JwtAuthentication generate(Claims claims){
    final JwtAuthentication jwtAuthentication = new JwtAuthentication();
    jwtAuthentication.setFirstName(claims.get("mail",String.class));
    jwtAuthentication.setEmail(claims.getSubject());
    return jwtAuthentication;
  }
}
