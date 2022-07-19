package com.learn.outh.user;

import java.util.Map;

public class GoogleOauth2User extends CustomOauth2User {

  public GoogleOauth2User(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getEmail(){
    return (String) attributes.get("email");
  }

  @Override
  public String getId() {return  (String) attributes.get("sub");}

}
