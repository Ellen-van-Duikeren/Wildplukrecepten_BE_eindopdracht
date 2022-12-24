package novi.nl.wildplukrecepten.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found.");
    }
    public UserNotFoundException(String username) {
        super("Cannot find user " + username);
    }
}