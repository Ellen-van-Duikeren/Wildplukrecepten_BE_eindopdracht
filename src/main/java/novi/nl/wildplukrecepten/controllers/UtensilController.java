package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.UtensilDto;
import novi.nl.wildplukrecepten.services.UtensilService;
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
public class UtensilController {
    private final UtensilService utensilService;
    public UtensilController(UtensilService utensilService) {
        this.utensilService = utensilService;
    }

    @GetMapping("/utensils")
    public ResponseEntity<List<UtensilDto>> getAllUtensils() {
        return ResponseEntity.ok(utensilService.getAllUtensils());
    }

    @GetMapping("/utensils/{id}")
    public ResponseEntity<UtensilDto> getUtensil(@PathVariable Long id) {
        return ResponseEntity.ok(utensilService.getUtensil(id));
    }

    @PostMapping("/utensils")
    public ResponseEntity<String> createUtensil(@Valid @RequestBody UtensilDto utensilDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorString = getErrorString(br);
            return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = utensilService.createUtensil(utensilDto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/utensils/" + createdId)
                    .toUriString());
            return ResponseEntity.created(uri).body("Utensil created.");
        }
    }

    @PutMapping("/utensils/{id}")
    public ResponseEntity<UtensilDto> updateUtensil(@PathVariable Long id, @Valid @RequestBody UtensilDto utensilDto) {
        UtensilDto utensilDto1 = utensilService.putUtensil(id, utensilDto);
        return ResponseEntity.ok().body(utensilDto1);
    }

    @PatchMapping("/utensils/{id}")
    public ResponseEntity<UtensilDto> updatePartOfUtensil(@PathVariable Long id, @Valid @RequestBody UtensilDto utensilDto) {
        UtensilDto utensilDto1 = utensilService.patchUtensil(id, utensilDto);
        return ResponseEntity.ok().body(utensilDto1);
    }

    @DeleteMapping("/utensils/{id}")
    public ResponseEntity<String> deleteUtensil(@PathVariable Long id) {
        utensilService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


