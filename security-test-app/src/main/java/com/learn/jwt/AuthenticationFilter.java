package com.learn.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationConverter converter;

  private final AuthenticationProvider authenticationProvider;

  public AuthenticationFilter(
      AuthenticationConverter converter,
      AuthenticationProvider authenticationProvider) {
    this.converter = converter;
    this.authenticationProvider = authenticationProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    Authentication authentication = this.converter.convert(request);


    if (authentication == null) {

      // todo: handle case when authentication is null(not just exception)
    }

    Authentication auth = this.authenticationProvider.authenticate(authentication);
    if (auth != null) {
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }
}
