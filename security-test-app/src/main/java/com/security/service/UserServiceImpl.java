package com.security.service;

import com.booking.data.service.AbstractDataService;
import com.security.domain.User;
import com.security.model.UserDto;
import com.security.service.repository.UserRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends AbstractDataService<Integer, User, UserDto> implements UserService {

  private  final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto findUserByEmail(String email) {
    Assert.notNull(email);
    User user = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException(String.format("User not found with email: %s",email)));

    return  this.findById(user.getId()).get();
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existUserByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
