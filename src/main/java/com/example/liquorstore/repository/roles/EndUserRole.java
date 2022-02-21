package com.example.liquorstore.repository.roles;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
@Setter
@Getter

public class EndUserRole {

    @Id
    @Column(name="role_name", length=50)
    private String role;


}
