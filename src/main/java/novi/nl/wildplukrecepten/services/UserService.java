package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.inputDto.UserInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.UserOutputDto;
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

    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserOutputDto getUser(String username) {
        UserOutputDto userOutputDto = new UserOutputDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userOutputDto = fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return userOutputDto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }


    public String createUser(UserInputDto userInputDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDto.setApikey(randomString);
        // zelf toegevoegd
        userInputDto.setEnabled(true);

        userInputDto.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        User newUser = userRepository.save(toUser(userInputDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserOutputDto newUser) {
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

    public void patchUser(String username, UserOutputDto changeUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        if (changeUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(changeUser.getPassword()));
        }
        if (changeUser.getEnabled() != null) {
            user.setEnabled(changeUser.getEnabled());
        }
        if (changeUser.getApikey() != null) {
            user.setApikey(changeUser.getApikey());
        }
        if (changeUser.getFirstname() != null) {
            user.setFirstname(changeUser.getFirstname());
        }
        if (changeUser.getLastname() != null) {
            user.setLastname(changeUser.getLastname());
        }
        if (changeUser.getEmailadress() != null) {
            user.setEmailadress(changeUser.getEmailadress());
        }
        userRepository.save(user);
    }


    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserOutputDto userOutputDto = fromUser(user);
        return userOutputDto.getAuthorities();
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

    public static UserOutputDto fromUser(User user) {
        var userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.password = user.getPassword();
        userOutputDto.enabled = user.isEnabled();
        userOutputDto.apikey = user.getApikey();
        userOutputDto.firstname = user.getFirstname();
        userOutputDto.lastname = user.getLastname();
        userOutputDto.emailadress = user.getEmailadress();
        userOutputDto.authorities = user.getAuthorities();

        return userOutputDto;
    }

    public User toUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEnabled(userInputDto.getEnabled());
        user.setApikey(userInputDto.getApikey());
        user.setFirstname(userInputDto.getFirstname());
        user.setLastname(userInputDto.getLastname());
        user.setEmailadress(userInputDto.getEmailadress());

        return user;
    }

}
