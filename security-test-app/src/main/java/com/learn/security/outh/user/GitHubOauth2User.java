package com.learn.security.outh.user;

import java.util.Map;

public class GitHubOauth2User extends CustomOauth2User {


 public GitHubOauth2User(Map<String, Object> attributes) {
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
 public String getId() {return  ((Integer) attributes.get("id")).toString();}
}
