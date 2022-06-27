package com.security.rest;

import com.security.model.UserDto;
import com.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@RequestMapping("/api/v1/users")
@RestController
public class UserControllerV1 {
  private final UserService userService;

  public UserControllerV1(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto create(@RequestBody UserDto userDto){
    return userService.create(userDto);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public  UserDto getByMail(@PathParam("mail") String mail){
    return userService.findUserByEmail(mail);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Integer id){
    userService.deleteById(id);
  }
}
