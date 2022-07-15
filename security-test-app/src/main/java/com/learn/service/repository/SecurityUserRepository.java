package com.learn.service.repository;

import com.learn.domain.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Integer> {
  boolean existsSecurityUserByPrincipalName(String principalName);
}
