package dev.jartificer.liquorstore.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsProvider {
  UserDetails getUserDetails();
}
