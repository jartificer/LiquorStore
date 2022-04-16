package dev.jartificer.liquorstore.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextDetailsProvider implements UserDetailsProvider {

  @Override
  public UserDetails getUserDetails() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (UserDetails) principal;
  }
}
