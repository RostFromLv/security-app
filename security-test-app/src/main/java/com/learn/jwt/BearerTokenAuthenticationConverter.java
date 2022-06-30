package com.learn.jwt;

import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class BearerTokenAuthenticationConverter implements AuthenticationConverter {


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
    byte[] decodedToken = Base64.getDecoder().decode(token);

    return new UsernamePasswordAuthenticationToken(userEmail, null);
  }
}
