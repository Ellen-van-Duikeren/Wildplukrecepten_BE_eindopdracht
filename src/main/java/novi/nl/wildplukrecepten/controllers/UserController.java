package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.UserDto;
import novi.nl.wildplukrecepten.exceptions.BadRequestException;
import novi.nl.wildplukrecepten.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        UserDto optionalUser = userService.getUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        String newUsername = userService.createUser(userDto);
        userService.addAuthority(newUsername, "ROLE_USER");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();
        return ResponseEntity.created(location).build();
    }


    @PutMapping("/users/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto userDto) {
        userService.updateUser(username, userDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{username}")
    public ResponseEntity<UserDto> patchUser(@PathVariable("username") String username, @RequestBody UserDto userDto) {
        userService.patchUser(username, userDto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/users/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping("/users/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/users/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}