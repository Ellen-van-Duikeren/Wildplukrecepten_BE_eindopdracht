package novi.nl.wildplukrecepten.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novi.nl.wildplukrecepten.dtos.UserDto;
import novi.nl.wildplukrecepten.filter.JwtRequestFilter;
import novi.nl.wildplukrecepten.models.User;
import novi.nl.wildplukrecepten.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtRequestFilter jwtRequestFilter;

    @MockBean
    private UserService userService;

    User user1;
    User user2;
    User user3;

    // dtos
    UserDto userDto1;
    UserDto userDto2;
    UserDto userDto3;
    UserDto userDto4;

    //input Dtos
    UserDto userDto5;
    UserDto userDto6;

    @BeforeEach
    void setUp() {
        user1 = new User("user@mail.com", "password", true, "apikey1", "user", "user", "user@mail.com", null);
        user2 = new User("admin@mail.com", "password", true, "apikey2", "admin", "admin", "admin@mail.com", null);
        user3 = new User("superUser@mail.com", "password", true, "apikey3", "superUser", "superUser", "superUser@mail.com", null);

        userDto1 = new UserDto("user@mail.com", "password", true, "apikey1", "user", "user", "user@mail.com", null);
        userDto2 = new UserDto("admin@mail.com", "password", true, "apikey2", "admin", "admin", "admin@mail.com", null);
        userDto3 = new UserDto("superUser@mail.com", "password", true, "apikey3", "superUser", "superUser", "superUser@mail.com", null);
        userDto4 = new UserDto("user@mail.com", "password2", true, "apikey4", "user", "user", "user@mail.com", null);
        userDto5 = new UserDto("user@mail.com", "password", true, "apikey1", "user", "user", "user@mail.com", null);
        userDto6 = new UserDto("user@mail.com", "password2", true, "apikey4", "user", "user", "user@mail.com", null);
    }


    @Test
    void getAllUsers() throws Exception {
        given(userService.getUsers()).willReturn(List.of(userDto1, userDto2, userDto3));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user@mail.com"))
                .andExpect(jsonPath("$[0].password").value("password"))
                .andExpect(jsonPath("$[0].enabled").value(true))
                .andExpect(jsonPath("$[0].apikey").value("apikey1"))
                .andExpect(jsonPath("$[0].firstname").value("user"))
                .andExpect(jsonPath("$[0].lastname").value("user"))
                .andExpect(jsonPath("$[0].emailadress").value("user@mail.com"))

                .andExpect(jsonPath("$[1].username").value("admin@mail.com"))
                .andExpect(jsonPath("$[1].password").value("password"))
                .andExpect(jsonPath("$[1].enabled").value(true))
                .andExpect(jsonPath("$[1].apikey").value("apikey2"))
                .andExpect(jsonPath("$[1].firstname").value("admin"))
                .andExpect(jsonPath("$[1].lastname").value("admin"))
                .andExpect(jsonPath("$[1].emailadress").value("admin@mail.com"))

                .andExpect(jsonPath("$[2].username").value("superUser@mail.com"))
                .andExpect(jsonPath("$[2].password").value("password"))
                .andExpect(jsonPath("$[2].enabled").value(true))
                .andExpect(jsonPath("$[2].apikey").value("apikey3"))
                .andExpect(jsonPath("$[2].firstname").value("superUser"))
                .andExpect(jsonPath("$[2].lastname").value("superUser"))
                .andExpect(jsonPath("$[2].emailadress").value("superUser@mail.com"))
        ;
    }

    @Test
    void getUser() throws Exception {
        given(userService.getUser(anyString())).willReturn(userDto1);

        mockMvc.perform(get("/users/user@mail.com"))
                .andExpect(jsonPath("username").value("user@mail.com"))
                .andExpect(jsonPath("password").value("password"))
                .andExpect(jsonPath("enabled").value(true))
                .andExpect(jsonPath("apikey").value("apikey1"))
                .andExpect(jsonPath("firstname").value("user"))
                .andExpect(jsonPath("lastname").value("user"))
                .andExpect(jsonPath("emailadress").value("user@mail.com"))
        ;
    }


    @Test
    void createUser() throws Exception {
        given(userService.createUser(userDto5)).willReturn(userDto1.getUsername());

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto5)))
                .andExpect(status().isCreated());
    }


    @Test
    void updateUser() throws Exception {
        mockMvc.perform(put("/users/user@mail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto4)))
                .andExpect(status().isNoContent());
    }


    @Test
    void patchUser() throws Exception {
        mockMvc.perform(patch("/users/user@mail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto4)))
                .andExpect(status().isNoContent());
    }


    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/user@mail.com"))
                .andExpect(status().isNoContent());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}