package com.learn.service;

import com.booking.data.service.AbstractDataService;
import com.learn.domain.AuthenticatorProvider;
import com.learn.domain.User;
import com.learn.model.UserDto;
import com.learn.service.repository.UserRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends AbstractDataService<Integer, User, UserDto>
    implements UserService {

  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

  @Override
  public UserDto create(UserDto target) {

    if (target.getPassword() == null) {
      target.setPassword(passwordEncoder.encode("passWord"));
    } else {
      target.setPassword(passwordEncoder.encode(target.getPassword()));
    }

    return super.create(target);
  }

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto findUserByEmail(String email) {
    Assert.notNull(email);
    return this.userRepository.findByEmail(email)
        .map(u -> converter.convertToDto(u, UserDto.class)).orElse(null);
//        .orElseThrow(() -> new EntityNotFoundException("User not found: "+email));
  }


  @Override
  public UserDto createUserAfterSuccessOauthLogin(String name, String email,String lastName,
                                                  AuthenticatorProvider provider) {
    UserDto userDto = new UserDto();
    userDto.setFirstName(name);
    userDto.setLastName(lastName);
    userDto.setEmail(email);
    userDto.setPassword(null);

    return create(userDto);
  }

  @Override
  public boolean userWasChanged(UserDto dbUser, UserDto targetUser) {
    boolean emailWasChanged = !dbUser.getEmail().equals(targetUser.getEmail());
    boolean nameWasChanged = !dbUser.getFirstName().equals(targetUser.getFirstName());
    boolean lastNameWasChanged = !dbUser.getLastName().equals(targetUser.getLastName());

    return emailWasChanged || nameWasChanged || lastNameWasChanged;
  }
}
