package com.learn.security;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class JwtAuthentication implements Authentication {

  private boolean authenticated;
  private String email;
  private String firstName;
  private String credentials;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return email;
  }

  @Override
  public String getName() {
    return firstName;
  }
}
