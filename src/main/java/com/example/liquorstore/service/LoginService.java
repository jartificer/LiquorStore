package com.example.liquorstore.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.liquorstore.model.LoginDto;
import com.example.liquorstore.repository.users.EndUser;
import com.example.liquorstore.repository.users.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.liquorstore.security.SecurityConstants.SECRET;

@Service
public class LoginService {
  private final EndUserRepository endUserRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public LoginService(EndUserRepository endUserRepository, BCryptPasswordEncoder passwordEncoder) {
    this.endUserRepository = endUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String checkUserAndPassword(LoginDto loginDto) {
    EndUser user =
        endUserRepository
            .findById(loginDto.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    String dtoPassword = loginDto.getPassword();
    String databasePassword = user.getPassword();
    if (!passwordEncoder.matches(dtoPassword, databasePassword)) {
      throw new AccessDeniedException("Username or password not found");
    }
    Algorithm algorithm = Algorithm.HMAC256(SECRET);
    JWTCreator.Builder builder = JWT.create();
    builder.withSubject(user.getEmail())
            .withIssuedAt(new Date())
            .withClaim("role", user.getEndUserRole().getRole());
    return builder.sign(algorithm);
  }
}
