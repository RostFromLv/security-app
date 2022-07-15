package com.learn.service;

import com.booking.data.service.DataService;
import com.learn.model.SecurityUserDto;

public interface SecurityUserService extends DataService<Integer, SecurityUserDto> {
    boolean existByUserPrincipalName(String userId);
}
