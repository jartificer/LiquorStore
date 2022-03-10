package com.example.liquorstore.api.controllers;

import com.example.liquorstore.model.LoginDto;
import com.example.liquorstore.repository.users.EndUserRepository;
import com.example.liquorstore.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/login")
public class LoginController {
  private final LoginService loginService;

  @Autowired
  public LoginController(LoginService loginService, EndUserRepository endUserRepository) {
    this.loginService = loginService;
  }

  @PostMapping
  public ResponseEntity<Object> authenticateUser(@RequestBody LoginDto loginDto) {
    return ResponseEntity.ok()
        .headers(
            httpHeaders -> httpHeaders.setBearerAuth(loginService.checkUserAndPassword(loginDto)))
        .build();
  }
}
