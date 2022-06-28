package com.learn.model;

import com.booking.data.converter.Convertible;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto implements Convertible {
  @NotNull
  private Integer id;
  @Email
  @NotNull
  private String email;
  @NotNull
  private String password;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private Integer age;
}
