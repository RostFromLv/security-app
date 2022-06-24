package com.security.rest;

import com.security.model.ComputerDto;
import com.security.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/computers")
public class ComputerControllerV1 {

  private final ComputerService computerService;

  @Autowired
  public ComputerControllerV1(ComputerService computerService) {
    this.computerService = computerService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ComputerDto create(@RequestBody @Validated ComputerDto computerDto){
    return computerService.create(computerDto);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public ComputerDto update(@RequestBody @Validated ComputerDto computerDto){
    return computerService.update(computerDto, computerDto.getId());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Collection<ComputerDto> getAll(){
    return computerService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ComputerDto getById(@PathVariable Integer id){
    return computerService.findById(id).get();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Integer id){
    computerService.deleteById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAll(){
    computerService.deleteAll();
  }
}
