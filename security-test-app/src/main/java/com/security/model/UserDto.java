package com.security.model;

import com.booking.data.converter.Convertible;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto implements Convertible {
  @NotNull(message = "User id cannot be null")
  private Integer id;
  @Email
  @NotNull(message = "User email cannot be null")
  private String email;
  @NotNull(message = "User password cannot be null")
  private String password;
  @NotNull(message = "User firstName cannot be null")
  private String firstName;
  @NotNull(message = "User lastName cannot be null")
  private String lastName;
}
