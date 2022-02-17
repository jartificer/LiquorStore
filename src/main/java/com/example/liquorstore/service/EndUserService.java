package com.example.liquorstore.service;

import com.example.liquorstore.repository.users.EndUser;
import com.example.liquorstore.repository.users.EndUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EndUserService implements UserDetailsService {
  private final EndUserRepository userRepository;

  public EndUserService(EndUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    EndUser endUser =
        userRepository
            .findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return User.withDefaultPasswordEncoder()
        .username(endUser.getEmail())
        .password(endUser.getPassword())
        .roles("USER")
        .build();
  }
}
