package com.learn.model;

import com.booking.data.converter.Convertible;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.learn.domain.BodyType;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDto implements Convertible {
  @NotNull(message = "Car dto ID cannot be null")
  private Integer id;
  @NotNull(message = "Car dto CAR_BRAND cannot be null ")
  private String carBrand;
  @NotNull(message = "Car dto YEAR cannot be null")
  private Integer year;
  @NotNull(message = "Car dto ENGINE_VOLUME cannot be null")
  private Float engineVolume;
  @NotNull(message = "Car dto BODY_TYPE cannot be null")
  private BodyType bodyType;
}
