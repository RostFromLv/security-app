package com.security.securitytestapp.service;

import com.booking.data.service.AbstractDataService;
import com.security.securitytestapp.domain.Car;
import com.security.securitytestapp.model.CarDto;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends AbstractDataService<Integer, Car, CarDto> implements CarService {

}
