package dev.jartificer.liquorstore.service;

import dev.jartificer.liquorstore.model.LiquorDto;
import dev.jartificer.liquorstore.repository.liquors.Liquor;
import dev.jartificer.liquorstore.repository.liquors.LiquorRepository;
import dev.jartificer.liquorstore.repository.roles.EndUserRole;
import dev.jartificer.liquorstore.repository.users.EndUser;
import dev.jartificer.liquorstore.repository.users.EndUserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LiquorServiceTests {

  @InjectMocks private LiquorService service;

  @Mock private LiquorRepository repository;

  @Mock private EndUserRepository userRepository;

  @Mock private ModelMapper modelMapper;


  @Test
  public void getAllLiquors_whenAllInputOk_displayPage() {
    Integer page = 1;
    Integer pageSize = 2;
    String sortBy = "id";

    Pageable pageRequest = PageRequest.of(page, pageSize, Sort.by(sortBy));

  }

  @Test
  public void create_whenAllInputOk_liquorIsCreated() {

    UUID id = UUID.fromString("49a16788-384b-4330-9caf-d87318b87c16");

    LiquorDto liquorDto = createLiquorDto();
    String username = "user";
    EndUser endUser = createEndUser();

    Liquor newLiquor = createLiquorFromDto(id, liquorDto, endUser);
    when(userRepository.findById(username)).thenReturn(Optional.of(endUser));
    when(modelMapper.map(liquorDto, Liquor.class)).thenReturn(newLiquor);

    service.create(liquorDto, username);

    verify(repository).save(newLiquor);
  }

  @Test
  public void update_whenAllInputOk_liquorIsSaved() {

    UUID id = UUID.fromString("49a16788-384b-4330-9caf-d87318b87c16");

    LiquorDto liquorDto = createLiquorDto();

    EndUser endUser = createEndUser();

    Liquor newLiquor = createLiquorFromDto(id, liquorDto, endUser);

    UserDetails userDetails = createUserDetails();

    when(repository.findById(id)).thenReturn(Optional.of(newLiquor));

    service.update(id, liquorDto, userDetails);

    verify(repository).save(newLiquor);
  }

  @Test
  public void update_whenIdNotFound_throwResourceNotFoundException() {

    UUID id = UUID.fromString("0000000000-0000-0000-0000-0000000000");
    LiquorDto liquorDto = createLiquorDto();

    UserDetails userDetails = createUserDetails();

    Exception exception =
        assertThrows(
            ResourceNotFoundException.class, () -> service.update(id, liquorDto, userDetails));
  }

  @Test
  public void update_whenNotCreatorOrAdmin_throwAccessDeniedException() {
    UserDetails invalidUserDetails = createInvalidUserDetails();
    UUID id = UUID.fromString("49a16788-384b-4330-9caf-d87318b87c16");
    LiquorDto newLiquorDto = createLiquorDto();
    EndUser endUser = createNonCreatorEndUser();
    Liquor liquor = createLiquorFromDto(id, newLiquorDto, endUser);
    when(repository.findById(id)).thenReturn(Optional.of(liquor));
    Exception exception =
        assertThrows(
            AccessDeniedException.class,
            () -> service.update(id, newLiquorDto, invalidUserDetails));
  }

  @Test
  public void partialUpdate_whenAllInputOk_liquorIsSaved() {

    UUID id = UUID.fromString("49a16788-384b-4330-9caf-d87318b87c16");

    LiquorDto liquorDto = createLiquorDto();

    EndUser endUser = createEndUser();

    Liquor newLiquor = createLiquorFromDto(id, liquorDto, endUser);

    UserDetails userDetails = createUserDetails();

    when(repository.findById(id)).thenReturn(Optional.of(newLiquor));

    service.partialUpdate(id, liquorDto, userDetails);

    verify(repository).save(newLiquor);
  }

  @Test
  public void partialUpdate_whenIdNotFound_throwResourceNotFoundException() {

    UUID id = UUID.fromString("0000000000-0000-0000-0000-0000000000");
    LiquorDto liquorDto = createLiquorDto();

    UserDetails userDetails = createUserDetails();

    Exception exception =
        assertThrows(
            ResourceNotFoundException.class,
            () -> service.partialUpdate(id, liquorDto, userDetails));
  }

  @Test
  public void partialUpdate_whenNotCreatorOrAdmin_throwAccessDeniedException() {
    UserDetails invalidUserDetails = createInvalidUserDetails();
    UUID id = UUID.fromString("49a16788-384b-4330-9caf-d87318b87c16");
    LiquorDto newLiquorDto = createLiquorDto();
    EndUser endUser = createNonCreatorEndUser();
    Liquor liquor = createLiquorFromDto(id, newLiquorDto, endUser);
    when(repository.findById(id)).thenReturn(Optional.of(liquor));
    Exception exception =
        assertThrows(
            AccessDeniedException.class,
            () -> service.partialUpdate(id, newLiquorDto, invalidUserDetails));
  }

  @Test
  public void deleteById_whenIdExists_idIsDeleted() {
    UUID id = UUID.fromString("49a16788-384b-4330-9caf-d87318b87c16");
    service.deleteById(id);
    verify(repository).deleteById(id);
  }

  @Test
  public void deleteById_whenIdDoesNotExist_throwResourceNotFoundException() {
    UUID id = UUID.fromString("0000000000-0000-0000-0000-0000000000");
    Mockito.doThrow(new EmptyResultDataAccessException(0)).when(repository).deleteById(id);
    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
  }

  private Liquor createLiquorFromDto(UUID id, LiquorDto newLiquorDto, EndUser endUser) {
    Liquor liquor = new Liquor();
    liquor.setId(id);
    liquor.setName(newLiquorDto.getName());
    liquor.setProducer(newLiquorDto.getProducer());
    liquor.setAbv(newLiquorDto.getAbv());
    liquor.setStock(newLiquorDto.getStock());
    liquor.setLiquorCreator(endUser);
    return liquor;
  }

  private EndUser createEndUser() {
    EndUserRole endUserRole = new EndUserRole();
    endUserRole.setRole("user");

    EndUser endUser = new EndUser();
    endUser.setEndUserRole(endUserRole);
    endUser.setEmail("user");
    endUser.setPassword("password");
    return endUser;
  }

  private EndUser createNonCreatorEndUser() {
    EndUserRole endUserRole = new EndUserRole();
    endUserRole.setRole("user");

    EndUser endUser = new EndUser();
    endUser.setEndUserRole(endUserRole);
    endUser.setEmail("other");
    endUser.setPassword("password");
    return endUser;
  }

  private LiquorDto createLiquorDto() {
    LiquorDto newLiquorDto = new LiquorDto();
    newLiquorDto.setId(UUID.randomUUID());
    newLiquorDto.setName(RandomStringUtils.randomAlphabetic(10));
    newLiquorDto.setProducer(RandomStringUtils.randomAlphabetic(10));
    newLiquorDto.setAbv(new Random().nextFloat());
    newLiquorDto.setStock(new Random().nextInt());
    return newLiquorDto;
  }

  private UserDetails createUserDetails() {
    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_WRITER"));
      }

      @Override
      public String getPassword() {
        return "password";
      }

      @Override
      public String getUsername() {
        return "user";
      }

      @Override
      public boolean isAccountNonExpired() {
        return true;
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return true;
      }
    };
  }

  private UserDetails createInvalidUserDetails() {
    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_WRITER"));
      }

      @Override
      public String getPassword() {
        return "wrong_password";
      }

      @Override
      public String getUsername() {
        return "wrong_user";
      }

      @Override
      public boolean isAccountNonExpired() {
        return true;
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return true;
      }
    };
  }
}
