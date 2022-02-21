package com.example.liquorstore.repository.users;

import com.example.liquorstore.repository.roles.EndUserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class EndUser {

  @Id private String email;
  private String password;

  @ManyToOne
  @JoinColumn(name="role_name")
  private EndUserRole role;
}
