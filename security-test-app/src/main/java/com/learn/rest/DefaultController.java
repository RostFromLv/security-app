package com.learn.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class DefaultController {

  @GetMapping
  public String homePage(){
    return "Congrats you are at home page";
  }
}
