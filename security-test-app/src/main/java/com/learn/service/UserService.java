package com.learn.service;

import com.booking.data.service.DataService;
import com.learn.model.UserDto;

public interface UserService extends DataService<Integer, UserDto> {
  UserDto findUserByEmail(String email);
  boolean existUserByEmail(String email);
}
