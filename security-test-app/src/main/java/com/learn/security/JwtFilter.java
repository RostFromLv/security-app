package com.learn.security;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@Component
public class JwtFilter  extends GenericFilterBean {

  private static final String authorizationAHeader = "Authorization";

  private final JwtProvider provider;

  @Autowired
  public JwtFilter(JwtProvider provider) {
    this.provider = provider;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain filterChain) throws IOException, ServletException {

    String token = getTokenFromRequest((HttpServletRequest) request);
    if (token!=null && provider.validateAccessToken(token)){
      Claims claims = provider.getAccessClaims(token);
      JwtAuthentication jwtAuthentication  = JwtUtils.generate(claims) ;
      jwtAuthentication.setAuthenticated(true);
      SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
    }

    filterChain.doFilter(request,response);
  }

  private String getTokenFromRequest(HttpServletRequest request){
    String bearer  = request.getHeader(authorizationAHeader);
    if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
      return bearer.substring(7);
    }
    return  null;
  }
}
