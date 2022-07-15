package com.learn.service;

import com.booking.data.service.AbstractDataService;
import com.learn.domain.Car;
import com.learn.model.CarDto;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends AbstractDataService<Integer, Car, CarDto> implements CarService{

}
