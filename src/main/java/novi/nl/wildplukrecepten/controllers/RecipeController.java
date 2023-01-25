package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.RecipeDto;
import novi.nl.wildplukrecepten.services.FileUploadService;
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
import java.util.Objects;

import static novi.nl.wildplukrecepten.utilities.Utilities.getErrorString;

@RestController
public class RecipeController {
    private final RecipeService recipeService;
    //    private final FileUploadController fileUploadController;
    private final FileUploadService fileUploadService;

    public RecipeController(RecipeService recipeService, FileUploadController fileUploadController, FileUploadService fileUploadService) {
        this.recipeService = recipeService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/recipes")
    @Transactional
    // transactional to link a photo to a recipe
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/recipes/{id}")
    @Transactional
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }

    @PostMapping("/recipes")
    public ResponseEntity<?> createRecipe(@Valid @RequestBody RecipeDto recipeDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorString = getErrorString(br);
            return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = recipeService.createRecipe(recipeDto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/recipes/" + createdId)
                    .toUriString());
            return ResponseEntity.created(uri).body(createdId);
        }
    }


    @PutMapping("/recipes/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDto recipeDto) {
        RecipeDto recipeDto1 = recipeService.putRecipe(id, recipeDto);
        return ResponseEntity.ok().body(recipeDto1);
    }

    @PatchMapping("/recipes/{id}")
    public ResponseEntity<RecipeDto> updatePartOfRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDto recipeDto) {
        RecipeDto recipeDto1 = recipeService.patchRecipe(id, recipeDto);
        return ResponseEntity.ok().body(recipeDto1);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //  to link a photo to a recipe
    @PostMapping("/recipes/{id}/photo")
    public ResponseEntity<Object> assignPhotoToRecipe(@PathVariable("id") Long id, @RequestBody MultipartFile file) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
        String photo = fileUploadService.storeFile(file, url);
        recipeService.assignPhotoToRecipe(photo, id);
        return ResponseEntity.noContent().build();
    }
}

