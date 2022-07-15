package com.learn.domain;


import com.booking.data.converter.Convertible;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "security_user")
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser implements Convertible {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "principal_name")
  private String principalName;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "auth_provider")
  private AuthenticatorProvider authProvider;

  @OneToOne
  @JsonManagedReference
  @JoinColumn(name = "user_id",referencedColumnName = "id")
  private User user;
}
