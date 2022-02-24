package com.example.liquorstore.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsProvider {
  UserDetails getUserDetails();
}
