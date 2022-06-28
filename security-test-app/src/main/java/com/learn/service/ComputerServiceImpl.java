package com.learn.service;

import com.booking.data.service.AbstractDataService;
import com.learn.domain.Computer;
import com.learn.model.ComputerDto;
import org.springframework.stereotype.Service;

@Service
public class ComputerServiceImpl extends AbstractDataService<Integer, Computer, ComputerDto> implements ComputerService {
}
