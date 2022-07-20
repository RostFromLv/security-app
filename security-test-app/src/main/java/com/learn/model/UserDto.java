package com.learn.model;

import com.booking.data.converter.Convertible;
import com.learn.domain.AuthenticatorProvider;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Convertible {

  private Integer id;

  @Email
  @NotNull
  private String email;

  private String password;
  @NotNull

  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  private AuthenticatorProvider provider;

  @NotNull
  private String providerId;
}
