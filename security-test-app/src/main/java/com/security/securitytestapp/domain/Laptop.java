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
@Table(name = "laptop")
public class Laptop implements Convertible {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String brand;
  @Column
  private Integer ram;
  @Column
  private Integer memory;
  @Column
  @Enumerated(value = EnumType.STRING)
  private MemoryType memoryType;
  @Column
  private String processor;
}
