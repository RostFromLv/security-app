package com.security.securitytestapp.model;

import com.booking.data.converter.Convertible;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.securitytestapp.domain.MemoryType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaptopDto implements Convertible {
  @NotNull(message = "Laptop dto ID cannot e null")
  private Integer id;
  @NotNull(message = "Laptop dto BRAND cannot null")
  private String brand;
  @NotNull(message = "Laptop dto RAM cannot be null")
  private Integer ram;
  @NotNull(message = "Laptop dto MEMORY cannot be null")
  private  Integer memory;
  @NotNull(message = "Laptop dto MEMORY_TYPE cannot be null")
  private MemoryType memoryType;
  @NotNull(message = "Laptop dto PROCESSOR cannot be null")
  private String processor;
}
