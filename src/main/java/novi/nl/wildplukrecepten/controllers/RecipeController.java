package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.inputDto.RecipeInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.RecipeOutputDto;
import novi.nl.wildplukrecepten.models.FileUploadResponse;
import novi.nl.wildplukrecepten.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static novi.nl.wildplukrecepten.utilities.Utilities.getErrorString;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class RecipeController {
    private final RecipeService recipeService;
    private final PhotoController photoController;
    public RecipeController(RecipeService recipeService, PhotoController photoController) {
        this.recipeService = recipeService;
        this.photoController = photoController;
    }

    @GetMapping("/recipes")
    @Transactional
    // transactional to link a photo to a recipe
    public ResponseEntity<List<RecipeOutputDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/recipes/{id}")
    @Transactional
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

    @PatchMapping("/recipes/{id}")
    public ResponseEntity<RecipeOutputDto> updatePartOfRecipe(@PathVariable Long id, @Valid @RequestBody RecipeInputDto recipeInputDto) {
        RecipeOutputDto recipeOutputDto = recipeService.patchRecipe(id, recipeInputDto);
        return ResponseEntity.ok().body(recipeOutputDto);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

//  to link a photo to a recipe
    @PostMapping("/recipes/{id}/photo")
    public void assignPhotoToRecipe(@PathVariable("id") Long id, @RequestBody MultipartFile file) {
        FileUploadResponse photo = photoController.singleFileUpload(file);
        recipeService.assignPhotoToRecipe(photo.getFileName(), id);
    }
}

