package dev.jartificer.liquorstore.repository.users;

import dev.jartificer.liquorstore.repository.roles.EndUserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class EndUser {

  @Id
  @Column(name = "email", length = 50)
  private String email;

  private String password;

  @ManyToOne
  @JoinColumn(name = "role_name")
  private EndUserRole endUserRole;
}
