//package com.example.liquorstore.repository.users;
//
//import com.example.liquorstore.security.BCryptPasswordEncoder;
//
//public class SaveUser {
//    private final EndUserRepository endUserRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    public SaveUser(EndUserRepository endUserRepository, BCryptPasswordEncoder passwordEncoder) {
//        this.endUserRepository = endUserRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public void saveUser() {
//
//        endUserRepository.save(new EndUser().setEmail("test").setPassword(passwordEncoder.encode("pass1"));
//    }
//
//
//}
