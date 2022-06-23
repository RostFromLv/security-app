package com.security.securitytestapp.service;

import com.booking.data.service.AbstractDataService;
import com.security.securitytestapp.domain.Laptop;
import com.security.securitytestapp.model.LaptopDto;
import org.springframework.stereotype.Service;

@Service
public class LaptopServiceImpl extends AbstractDataService<Integer, Laptop, LaptopDto> implements LaptopService {
}
