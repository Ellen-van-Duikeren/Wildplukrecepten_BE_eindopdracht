package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.IngredientDto;
import novi.nl.wildplukrecepten.services.IngredientService;
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
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getIngredient(id));
    }

    @PostMapping("/ingredients")
    public ResponseEntity<String> createIngredient(@Valid @RequestBody IngredientDto ingredientDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorString = getErrorString(br);
            return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = ingredientService.createIngredient(ingredientDto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/ingredients/" + createdId)
                    .toUriString());
            return ResponseEntity.created(uri).body("Ingredient created.");
        }
    }

    @PutMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable Long id, @Valid @RequestBody IngredientDto ingredientDto) {
        IngredientDto ingredientDto1 = ingredientService.putIngredient(id, ingredientDto);
        return ResponseEntity.ok().body(ingredientDto1);
    }

    @PatchMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDto> updatePartOfIngredient(@PathVariable Long id, @Valid @RequestBody IngredientDto ingredientDto) {
        IngredientDto ingredientDto1 = ingredientService.patchIngredient(id, ingredientDto);
        return ResponseEntity.ok().body(ingredientDto1);
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

