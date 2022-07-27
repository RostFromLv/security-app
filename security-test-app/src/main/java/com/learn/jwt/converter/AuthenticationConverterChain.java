package com.learn.jwt.converter;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class AuthenticationConverterChain implements AuthenticationConverter {

  private final Set<AuthenticationConverter> converters;

  public AuthenticationConverterChain() {
    this.converters = new LinkedHashSet<>();
  }

  public AuthenticationConverterChain(Set<AuthenticationConverter> converters) {
    this.converters = new LinkedHashSet<>(converters);
  }

  @Override
  public Authentication convert(HttpServletRequest request) {
    for (AuthenticationConverter converter : converters) {
      Authentication auth = converter.convert(request);
      if (auth != null) {
        return auth;
      }
    }
    return null;
  }

  public void addConverter(AuthenticationConverter converter) {
    this.converters.add(converter);
  }
}
