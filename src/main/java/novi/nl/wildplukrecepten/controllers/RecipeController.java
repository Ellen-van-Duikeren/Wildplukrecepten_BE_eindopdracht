package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.inputDto.RecipeInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.RecipeOutputDto;
import novi.nl.wildplukrecepten.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static novi.nl.wildplukrecepten.utilities.Utilities.getErrorString;


//patchmapping nog toevoegen


@RestController
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeOutputDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeOutputDto> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }

    @PostMapping("/recipes")
    public ResponseEntity<String> createRecipe(@Valid @RequestBody RecipeInputDto recipeInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorString = getErrorString(br);
            return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = recipeService.createRecipe(recipeInputDto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/recipes/" + createdId)
                    .toUriString());
            return ResponseEntity.created(uri).body("Recipe created.");
        }
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<RecipeOutputDto> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeInputDto recipeInputDto) {
        RecipeOutputDto recipeOutputDto = recipeService.putRecipe(id, recipeInputDto);
        return ResponseEntity.ok().body(recipeOutputDto);
    }

//    @PatchMapping("/recipes/{id}")
//    public ResponseEntity<RecipeOutputDto> updatePartOfRecipe(@PathVariable Long id, @Valid @RequestBody RecipeInputDto recipeInputDto) {
//        RecipeOutputDto recipeOutputDto = recipeService.patchRecipe(id, recipeInputDto);
//        return ResponseEntity.ok().body(recipeOutputDto);
//    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

