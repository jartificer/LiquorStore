package dev.jartificer.liquorstore.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

    private String email;
    private String password;

}
