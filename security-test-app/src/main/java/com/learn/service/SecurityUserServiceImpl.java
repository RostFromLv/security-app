package com.learn.service;

import com.booking.data.service.AbstractDataService;
import com.learn.domain.SecurityUser;
import com.learn.model.SecurityUserDto;
import com.learn.service.repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserServiceImpl  extends AbstractDataService<Integer, SecurityUser, SecurityUserDto> implements SecurityUserService{


  private final SecurityUserRepository securityUserRepository;

  @Autowired
  public SecurityUserServiceImpl(
      SecurityUserRepository securityUserRepository) {
    this.securityUserRepository = securityUserRepository;
  }

  @Override
  public boolean existByUserPrincipalName(String principalName) {
    return this.securityUserRepository.existsSecurityUserByPrincipalName(principalName);
  }
}
