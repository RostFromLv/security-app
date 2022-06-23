package com.security.securitytestapp.domain;


import com.booking.data.converter.Convertible;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car")
public class Car implements Convertible {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String carBrand;
  @Column
  private Integer year;
  @Column
  private Float engineVolume;
  @Column
  @Enumerated(value = EnumType.STRING)
  private BodyType bodyType;
}
