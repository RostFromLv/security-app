package com.learn.jwt;

import com.learn.service.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PostgresUser implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  private PostgresUser(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

    String userPassword =  userRepository.findByEmail(userEmail).get().getPassword();

    return new User(userEmail, userPassword,new ArrayList<>());
  }


}
