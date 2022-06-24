package com.security.service;

import com.booking.data.service.AbstractDataService;
import com.security.domain.Computer;
import com.security.model.ComputerDto;
import org.springframework.stereotype.Service;

@Service
public class ComputerServiceImpl extends AbstractDataService<Integer, Computer, ComputerDto> implements ComputerService {
}
