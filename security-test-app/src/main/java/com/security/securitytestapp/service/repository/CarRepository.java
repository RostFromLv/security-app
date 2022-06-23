package com.security.securitytestapp.service.repository;

import com.security.securitytestapp.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Integer, Car> {
}
