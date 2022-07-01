package com.learn.jwt;

import com.learn.security.JwtAuthentication;
import com.learn.security.JwtProvider;
import com.learn.security.JwtUtils;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class BearerTokenAuthenticationConverter implements AuthenticationConverter {


  private final JwtProvider jwtProvider;

  @Autowired
  public BearerTokenAuthenticationConverter(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }


  @Override
  public Authentication convert(HttpServletRequest request) {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header == null) {
      return null;
    }
    String token = null;
    if (header.startsWith("Bearer ")) {
      token = header.substring(7);
    }

    jwtProvider.validateAccessToken(token);

    Claims claims = jwtProvider.getAccessClaims(token);
    JwtAuthentication authentication = JwtUtils.generate(claims);

    return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
  }
}
