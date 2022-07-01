package com.learn.jwt;

import com.learn.model.UserDto;
import com.learn.service.UserService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PostgresUser implements UserDetailsService {

  private final UserService userService;

  @Autowired
  private PostgresUser(UserService userService) {
    this.userService = userService;
  }



  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
    UserDto userDto = userService.findUserByEmail(userEmail);

    String userPassword = userDto.getPassword();

    User user =new User(userEmail, userPassword,new ArrayList<>());
    return new User(userEmail, userPassword,new ArrayList<>());
  }
}
