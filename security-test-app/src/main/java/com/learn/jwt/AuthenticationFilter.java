package com.learn.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
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

    if (authentication == null || request.getCookies() == null) {
      log.error("Authentication is null");
      System.out.println(request.getHeader(HttpHeaders.LOCATION));
      System.out.println(request.getContextPath());
      System.out.println(request.getHeaderNames());
      filterChain.doFilter(request, response);
      return;
    }
    Authentication auth = this.authenticationProvider.authenticate(authentication);
    if (auth != null) {
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }
}
