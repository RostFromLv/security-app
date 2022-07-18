package com.learn.outh.user;

import com.learn.domain.AuthenticatorProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Oauth2UserFactory {
  public static OAuth2User getOauth2User(AuthenticatorProvider provider,OAuth2User user){
    if (provider.equals(AuthenticatorProvider.FACEBOOK)){
      return new FacebookOauth2User();
    }

  }
}
