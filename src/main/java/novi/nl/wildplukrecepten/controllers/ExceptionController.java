package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String recordNotFoundException(Exception exception) {
        return exception.getMessage();
    }
}
