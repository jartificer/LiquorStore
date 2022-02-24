package com.example.liquorstore.repository.liquors;

import com.example.liquorstore.repository.users.EndUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Liquor {
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String name;
  private String producer;
  private Float abv;
  private Integer stock;

  @ManyToOne
  @JoinColumn(name = "creator")
  private EndUser liquorCreator;
}
