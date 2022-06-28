package com.learn.security;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {
  @NotNull
  private String email;
  @NotNull
  private String password;
}
