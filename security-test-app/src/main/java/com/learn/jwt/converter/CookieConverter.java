package com.learn.jwt.converter;

import com.learn.security.JwtProvider;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class CookieConverter extends BaseJWTConverter implements AuthenticationConverter {

  private final String tokenStringName = "accessToken";

  public CookieConverter() {
    super(new JwtProvider());
  }

  @Override
  public Authentication convert(HttpServletRequest request) {

    Cookie[] cookies  = request.getCookies();

    if (cookies!=null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(tokenStringName)) {
          return convertByString(cookie.getValue());
        }
      }
    }
    return null;
  }
}
