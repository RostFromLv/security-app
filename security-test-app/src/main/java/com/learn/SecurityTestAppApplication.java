package com.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableResourceServer
//@EnableAuthorizationServer
public class SecurityTestAppApplication {
  public static void main(String[] args) {
    SpringApplication.run(SecurityTestAppApplication.class, args);
  }

}
