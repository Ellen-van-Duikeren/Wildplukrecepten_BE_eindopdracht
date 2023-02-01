package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dtos.UserDto;
import novi.nl.wildplukrecepten.models.User;
import novi.nl.wildplukrecepten.repositories.UserRepository;
import novi.nl.wildplukrecepten.security.SpringSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

// nieuw ivm password
//@SpringBootApplication(exclude=PasswordEncoder.class)


class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Captor
    ArgumentCaptor<User> captor;

    User user1;
    User user2;
    User user3;

    UserDto userDto1;

    UserServiceTest(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @BeforeEach
    void setUp() {
        user1 = new User("user@mail.com", "$2y$10$yq8BZ6yIcauc1NHKekI4Iu/cgF1GtFlzLLYvtssS0C3fouThfew32", true, "apikey1", "user", "user", "user@mail.com", null);
        user2 = new User("admin@mail.com", "$2y$10$yq8BZ6yIcauc1NHKekI4Iu/cgF1GtFlzLLYvtssS0C3fouThfew32", true, "apikey2", "admin", "admin", "admin@mail.com", null);
        user3 = new User("superUser@mail.com", "$2y$10$yq8BZ6yIcauc1NHKekI4Iu/cgF1GtFlzLLYvtssS0C3fouThfew32", true, "apikey3", "superUser", "superUser", "superUser@mail.com", null);

        userDto1 = new UserDto("user@mail.com", "password", true, "apikey1", "user", "user", "user@mail.com", null);
    }


    // testen..........................................................
    @Test
    void getUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        List<UserDto> usersFound = userService.getUsers();
        assertEquals(user1.getUsername(), usersFound.get(0).getUsername());
        assertEquals(user1.getPassword(), usersFound.get(0).getPassword());
        assertEquals(user1.isEnabled(), usersFound.get(0).getEnabled());
        assertEquals(user1.getApikey(), usersFound.get(0).getApikey());
        assertEquals(user1.getFirstname(), usersFound.get(0).getFirstname());
        assertEquals(user1.getLastname(), usersFound.get(0).getLastname());
        assertEquals(user1.getEmailadress(), usersFound.get(0).getEmailadress());

        assertEquals(user2.getUsername(), usersFound.get(1).getUsername());
        assertEquals(user2.getPassword(), usersFound.get(1).getPassword());
        assertEquals(user2.isEnabled(), usersFound.get(1).getEnabled());
        assertEquals(user2.getApikey(), usersFound.get(1).getApikey());
        assertEquals(user2.getFirstname(), usersFound.get(1).getFirstname());
        assertEquals(user2.getLastname(), usersFound.get(1).getLastname());
        assertEquals(user2.getEmailadress(), usersFound.get(1).getEmailadress());

        assertEquals(user3.getUsername(), usersFound.get(2).getUsername());
        assertEquals(user3.getPassword(), usersFound.get(2).getPassword());
        assertEquals(user3.isEnabled(), usersFound.get(2).getEnabled());
        assertEquals(user3.getApikey(), usersFound.get(2).getApikey());
        assertEquals(user3.getFirstname(), usersFound.get(2).getFirstname());
        assertEquals(user3.getLastname(), usersFound.get(2).getLastname());
        assertEquals(user3.getEmailadress(), usersFound.get(2).getEmailadress());
    }


    @Test
    void getUser() {
        when(userRepository.findById("user1")).thenReturn(Optional.of(user1));

        UserDto userDto = userService.getUser("user1");

        assertEquals("user@mail.com", userDto.getUsername());
        assertEquals("password", userDto.getPassword());
        assertEquals(true, userDto.getEnabled());
        assertEquals("apikey1", userDto.getApikey());
        assertEquals("user", userDto.getFirstname());
        assertEquals("user", userDto.getLastname());
        assertEquals("user@mail.com", userDto.getEmailadress());
    }


//    @Test
//    void createUser() {
//
//        when(SpringSecurityConfig.passwordEncoder().encode(userDto1.getPassword())).thenReturn(user1.getPassword());
//        when(userDto1.setPassword(userDto1.getPassword())).thenReturn(user1.getPassword());
//        when(passwordEncoder.encode(userDto1.getPassword())).thenReturn(user1.getPassword());
//        when(userRepository.save(user1)).thenReturn(user1);
//
//        userService.createUser(userDto1);
//        verify(userRepository, times(1)).save(captor.capture());
//        User captured = captor.getValue();
//
//        assertEquals(user1.getUsername(), captured.getUsername());
//    }




    @Test
    void deleteUser() {
        when(userRepository.findById("user@mail.com")).thenReturn(Optional.of(user1));
        userService.deleteUser("user@mail.com");

        verify(userRepository).deleteById("user@mail.com");
    }
}