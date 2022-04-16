package dev.jartificer.liquorstore.service;

import dev.jartificer.liquorstore.repository.users.EndUser;
import dev.jartificer.liquorstore.repository.users.EndUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
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
    return User.withUsername(endUser.getEmail())
        .passwordEncoder(Function.identity())
        .password(endUser.getPassword())
        .roles(endUser.getEndUserRole().getRole())
        .build();
  }
}
