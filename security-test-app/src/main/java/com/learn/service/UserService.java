package com.learn.service;

import com.booking.data.service.DataService;
import com.learn.domain.AuthenticatorProvider;
import com.learn.model.UserDto;

public interface UserService extends DataService<Integer, UserDto> {
  UserDto findUserByEmail(String email);
  UserDto createUserAfterSuccessOauthLogin(String name, String email,String lastName, AuthenticatorProvider provider);
  boolean userWasChanged(UserDto dbUser,UserDto targetUser);
}
