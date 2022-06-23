package com.security.securitytestapp.service.repository;

import com.security.securitytestapp.domain.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop,Integer> {
}
