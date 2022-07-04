package com.learn.security;

import io.jsonwebtoken.Claims;

public class JwtUtils {

  public static JwtAuthentication generate(Claims claims){
    final JwtAuthentication jwtAuthentication = new JwtAuthentication();
    jwtAuthentication.setFirstName(claims.get("email",String.class));
    jwtAuthentication.setCredentials(claims.get("password",String.class));
    jwtAuthentication.setEmail(claims.getSubject());
    return jwtAuthentication;
  }
}
