package com.learn.security.outh.user;

import com.learn.domain.AuthenticatorProvider;
import java.util.Map;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class Oauth2UserFactory {

  public static CustomOauth2User getOauth2User(String registrationId, Map<String,Object> attributes){
    if (registrationId.equals(AuthenticatorProvider.google.toString())){
      return new GoogleOauth2User(attributes);
    }
    if (registrationId.equals(AuthenticatorProvider.facebook.toString())){
      return new FacebookOauth2User(attributes);
    }
    if (registrationId.equals(AuthenticatorProvider.github.toString())){
      return new GitHubOauth2User(attributes);
    }else {
      throw new OAuth2AuthenticationException("Unknown provider!");
    }
  }
}
