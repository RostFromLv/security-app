package com.learn.jwt.converter;

import com.learn.security.JwtAuthentication;
import com.learn.security.JwtProvider;
import com.learn.security.JwtUtils;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public abstract class BaseJWTConverter {

  private final JwtProvider jwtProvider;

  public BaseJWTConverter(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  protected Authentication convertByString(String token){
    jwtProvider.validateAccessToken(token);

    Claims claims = null;
    try {
      claims = jwtProvider.getAccessClaims(token);
    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }
    JwtAuthentication authentication = JwtUtils.generate(claims);

    return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
  }
}
