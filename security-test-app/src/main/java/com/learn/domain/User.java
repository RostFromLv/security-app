package com.learn.domain;

import com.booking.data.converter.Convertible;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User  implements Convertible {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String email;
  @Column
  private String password;
  @Column
  private String firstName;
  @Column
  private String lastName;

  @Enumerated(EnumType.STRING)
  private AuthenticatorProvider provider;

  @Column
  private String providerId;
}
