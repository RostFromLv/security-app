package com.learn.rest;

import com.learn.model.CarDto;
import com.learn.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/cars")
public class CarControllerV1 {

  private final CarService carService;

  @Autowired
  public CarControllerV1(CarService carService) {
    this.carService = carService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CarDto create(@RequestBody @Validated CarDto carDto){
    return carService.create(carDto);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public CarDto update(@RequestBody @Validated CarDto carDto){
    return carService.update(carDto, carDto.getId());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Collection<CarDto> getAll(){
    return carService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CarDto getById(@PathVariable Integer id){
    return carService.findById(id).get();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Integer id){
    carService.deleteById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAll(){
    carService.deleteAll();
  }
}
