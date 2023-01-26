package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dtos.UserDto;
import novi.nl.wildplukrecepten.models.Authority;
import novi.nl.wildplukrecepten.models.User;
import novi.nl.wildplukrecepten.repositories.UserRepository;
import novi.nl.wildplukrecepten.utilities.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto userDto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userDto = fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return userDto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }


    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        // zelf toegevoegd
        userDto.setEnabled(true);

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        user.setEnabled(newUser.getEnabled());
        user.setApikey(newUser.getApikey());
        user.setFirstname(newUser.getFirstname());
        user.setLastname(newUser.getLastname());
        user.setEmailadress(newUser.getEmailadress());
        userRepository.save(user);
    }

    public void patchUser(String username, UserDto changeUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        if (changeUser.getPassword() != "") {
            user.setPassword(passwordEncoder.encode(changeUser.getPassword()));
        }
        if (changeUser.getEnabled() != null) {
            user.setEnabled(changeUser.getEnabled());
        }
        if (changeUser.getApikey() != "") {
            user.setApikey(changeUser.getApikey());
        }
        if (changeUser.getFirstname() != "") {
            user.setFirstname(changeUser.getFirstname());
        }
        if (changeUser.getLastname() != "") {
            user.setLastname(changeUser.getLastname());
        }
        if (changeUser.getEmailadress() != "") {
            user.setEmailadress(changeUser.getEmailadress());
        }
        userRepository.save(user);
    }


    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user) {
        var userDto = new UserDto();
        userDto.username = user.getUsername();
        userDto.password = user.getPassword();
        userDto.enabled = user.isEnabled();
        userDto.apikey = user.getApikey();
        userDto.firstname = user.getFirstname();
        userDto.lastname = user.getLastname();
        userDto.emailadress = user.getEmailadress();
        userDto.authorities = user.getAuthorities();

        return userDto;
    }

    public User toUser(UserDto userDto) {
        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmailadress(userDto.getEmailadress());

        return user;
    }

}
