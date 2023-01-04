package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.outputDto.UserOutputDto;
import novi.nl.wildplukrecepten.models.Authority;
import novi.nl.wildplukrecepten.models.User;
import novi.nl.wildplukrecepten.repositories.UserRepository;
import novi.nl.wildplukrecepten.utilities.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;

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

    public String createUser(UserOutputDto userOutputDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userOutputDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userOutputDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserOutputDto newUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

//    van Paul, extra toevoeging binnen bovenstaande functie om password te checken icm functie die onderaan staat
//    public void updateUser(String username, UserDto userDto) {
//        String password = userDto.getPassword();
//        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
//        if(validatePassword(password)){
//            User user = userRepository.findById(username).get();
//            user.setPassword(userDto.getPassword());
//            userRepository.save(user);
//        }else {
//            throw new InvalidPasswordException("Your password must contain:\n At least 6 characters, 1 uppercase letter, 1 lowercase letter, 1 special character and may not contain any whitespaces");
//        }
//    }


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

    public User toUser(UserOutputDto userOutputDto) {
        var user = new User();
        user.setUsername(userOutputDto.getUsername());
        user.setPassword(userOutputDto.getPassword());
        user.setEnabled(userOutputDto.getEnabled());
        user.setApikey(userOutputDto.getApikey());
        user.setFirstname(userOutputDto.getFirstname());
        user.setLastname(userOutputDto.getLastname());
        user.setEmailadress(userOutputDto.getEmailadress());

        return user;
    }

//    van Paul, om password te checken, icm hierboven
//    public Boolean validatePassword(String password){
//        if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?=])(?=\\S+$).{6,}$")){
//            return true;
//        } else {
//            return false;
//        }
//    }

/*  ^                 - start-of-string
    (?=.*[0-9])       - a digit must occur at least once
    (?=.*[a-z])       - a lower case letter must occur at least once
    (?=.*[A-Z])       - an upper case letter must occur at least once
    (?=.*[@#$%^&+=])  - a special character must occur at least once
    (?=\S+$)          - no whitespace allowed in the entire string
    .{6,}             - anything, at least six places though
    $                 # end-of-string*/


}
