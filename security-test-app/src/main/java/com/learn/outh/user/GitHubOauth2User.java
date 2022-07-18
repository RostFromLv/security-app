package com.learn.outh.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class GitHubOauth2User implements OAuth2User {

 private final OAuth2User oAuth2User;

 @Autowired
  public GitHubOauth2User(OAuth2User oAuth2User) {
    this.oAuth2User = oAuth2User;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.oAuth2User.getAttributes();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.oAuth2User.getAuthorities();
  }

  @Override
  public String getName() {
    return this.oAuth2User.getAttribute("name");
  }
  public String getEmail(){
   return this.oAuth2User.getAttribute("email");
  }
  public String getPrincipalName(){
   return this.oAuth2User.getAttribute("id");
  }
}
