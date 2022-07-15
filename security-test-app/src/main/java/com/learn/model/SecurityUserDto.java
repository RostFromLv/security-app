package com.learn.model;

import com.booking.data.converter.Convertible;
import com.learn.domain.AuthenticatorProvider;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecurityUserDto implements Convertible {
  @NotNull
  private Long id;
  @NotNull
  private String principalName;
  @NotNull
  private AuthenticatorProvider authProvider;
  @NotNull
  private Integer userId;
}
