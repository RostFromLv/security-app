package com.learn.jwt.converter;

import com.learn.security.JwtProvider;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class BearerTokenAuthenticationConverter extends BaseJWTConverter implements AuthenticationConverter {


  public BearerTokenAuthenticationConverter(JwtProvider jwtProvider) {
    super(jwtProvider);
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

    return convertByString(token);
  }


}
