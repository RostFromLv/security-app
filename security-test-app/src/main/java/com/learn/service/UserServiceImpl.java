package com.learn.service;

import com.booking.data.converter.DtoConverter;
import com.booking.data.service.AbstractDataService;
import com.learn.domain.User;
import com.learn.model.UserDto;
import com.learn.service.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
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
    return this.userRepository.findByEmail(email)
        .map(u -> converter.convertToDto(u, UserDto.class))
        .orElseThrow(() -> new EntityNotFoundException("User not found: "+email));//Get with default repo
  }

}
