package com.learn.rest;

import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class DefaultController {

  @GetMapping
  public String homePage(){
    return "Start home page";
  }

  Response response = new Response();
}
