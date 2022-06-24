package com.security.service;

import com.booking.data.service.AbstractDataService;
import com.security.domain.Car;
import com.security.model.CarDto;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends AbstractDataService<Integer, Car, CarDto> implements CarService{



}
