package com.example.liquorstore.repository.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class EndUser {

  @Id private String email;
  private String password;
}
