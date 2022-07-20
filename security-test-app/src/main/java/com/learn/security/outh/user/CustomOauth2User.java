package com.learn.security.outh.user;

import java.util.Map;

public abstract class CustomOauth2User {

  protected Map<String,Object> attributes;

  public CustomOauth2User(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public  Map<String,Object> getAttributes(){
    return attributes;
  }

  public abstract String getId();
  public abstract String getName();
  public abstract String getEmail();
}
