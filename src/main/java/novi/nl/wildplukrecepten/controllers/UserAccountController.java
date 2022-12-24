package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.inputDto.UserAccountInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.UserAccountOutputDto;
import novi.nl.wildplukrecepten.dto.outputDto.UserAccountOutputDto;
import novi.nl.wildplukrecepten.models.UserAccount;
import novi.nl.wildplukrecepten.services.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static novi.nl.wildplukrecepten.utilities.Utilities.getErrorString;


@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class UserAccountController {
    private final UserAccountService userAccountService;
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/useraccounts")
    public ResponseEntity<List<UserAccountOutputDto>> getAllUserAccounts() {
        return ResponseEntity.ok(userAccountService.getAllUserAccounts());
    }

    @GetMapping("/useraccounts/{id}")
    public ResponseEntity<UserAccountOutputDto> getUserAccount(@PathVariable Long id) {
        return ResponseEntity.ok(userAccountService.getUserAccount(id));
    }

    @PostMapping("/useraccounts")
    public ResponseEntity<String> createUserAccount(@Valid @RequestBody UserAccountInputDto userAccountInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorString = getErrorString(br);
            return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = userAccountService.createUserAccount(userAccountInputDto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/userAccounts/" + createdId)
                    .toUriString());
            return ResponseEntity.created(uri).body("UserAccount created.");
        }
    }

    @PutMapping("/useraccounts/{id}")
    public ResponseEntity<UserAccountOutputDto> updateUserAccount(@PathVariable Long id, @Valid @RequestBody UserAccountInputDto userAccountInputDto) {
        UserAccountOutputDto userAccountDto = userAccountService.putUserAccount(id, userAccountInputDto);
        return ResponseEntity.ok().body(userAccountDto);
    }

    @PatchMapping("/useraccounts/{id}")
    public ResponseEntity<UserAccountOutputDto> updatePartOfUserAccount(@PathVariable Long id, @Valid @RequestBody UserAccountInputDto userAccountInputDto) {
        UserAccountOutputDto userAccountDto = userAccountService.patchUserAccount(id, userAccountInputDto);
        return ResponseEntity.ok().body(userAccountDto);
    }

    @DeleteMapping("/useraccounts/{id}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable Long id) {
        userAccountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}