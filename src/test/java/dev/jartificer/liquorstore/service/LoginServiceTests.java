package dev.jartificer.liquorstore.service;

import dev.jartificer.liquorstore.model.LoginDto;
import dev.jartificer.liquorstore.repository.roles.EndUserRole;
import dev.jartificer.liquorstore.repository.users.EndUser;
import dev.jartificer.liquorstore.repository.users.EndUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTests {
  @InjectMocks private LoginService service;
  @Mock private EndUserRepository repository;
  @Mock private PasswordEncoder passwordEncoder;

  @Test
  public void checkUserAndPassword_whenInputOk_returnJWT() {
  EndUser user = createEndUser();
  LoginDto loginDto = createLoginDto();
    lenient().when(repository.findById(loginDto.getEmail())).thenReturn(Optional.of(user));
    String dtoPassword = "password";
    String databasePassword = "password";
    lenient().when(!passwordEncoder.matches(dtoPassword, databasePassword)).thenReturn(false);
    service.checkUserAndPassword(loginDto);
  }

//  @Test
//  public String checkUserAndPassword_whenUserNotFound_throwAccessDeniedException() {
//
//  }

  private EndUser createEndUser() {
    EndUserRole endUserRole = new EndUserRole();
    endUserRole.setRole("user");

    EndUser endUser = new EndUser();
    endUser.setEndUserRole(endUserRole);
    endUser.setEmail("user");
    endUser.setPassword("password");
    return endUser;
  }

  private LoginDto createLoginDto() {
    LoginDto newLoginDto = new LoginDto();
    newLoginDto.setEmail("user");
    newLoginDto.setPassword("password");
    return newLoginDto;
  }
}
