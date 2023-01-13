package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.inputDto.IngredientInputDto;
import novi.nl.wildplukrecepten.dto.inputDto.InstructionInputDto;
import novi.nl.wildplukrecepten.dto.inputDto.RecipeInputDto;
import novi.nl.wildplukrecepten.dto.inputDto.UtensilInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.RecipeOutputDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.*;
import novi.nl.wildplukrecepten.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final InstructionRepository instructionRepository;
    private final UtensilRepository utensilRepository;
    private final FileUploadRepository uploadRepository;

    //    test
    public final EmailService emailService;


    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, UtensilRepository utensilRepository, InstructionRepository instructionRepository, FileUploadRepository uploadRepository, EmailService emailService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.utensilRepository = utensilRepository;
        this.instructionRepository = instructionRepository;
        this.uploadRepository = uploadRepository;
        this.emailService = emailService;
    }


    // GetMapping, function for getting all recipes
    public List<RecipeOutputDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeOutputDto> recipeOutputDtos = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeOutputDto recipeOutputDto = transferToDto(recipe);
            recipeOutputDtos.add(recipeOutputDto);
        }
        return recipeOutputDtos;
    }

    // GetMapping by id, function for getting a recipe by id
    public RecipeOutputDto getRecipe(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (!recipeRepository.existsById(id)) {
            throw new RecordNotFoundException("No recipe found with id: " + id + ".");
        } else {
            Recipe recipe1 = optionalRecipe.get();
            return transferToDto(recipe1);
        }
    }

    // PostMapping, function for adding a recipe
    public Long createRecipe(RecipeInputDto recipeInputDto) {
        Recipe newRecipe = new Recipe();
        newRecipe = transferToRecipe(recipeInputDto);
        Recipe savedRecipe1 = recipeRepository.save(newRecipe);

        // need to save in between in savedRecipe1, savedRecipe2 instead of using savedRecipe in every line; otherwise the last instructions will overwrite the first utensils and you will only see instructions and no utensils        addUtensilToRecipe(recipeInputDto, savedRecipe);
        addUtensilToRecipe(recipeInputDto, savedRecipe1);
        Recipe savedRecipe2 = recipeRepository.save(savedRecipe1);
        addIngredientToRecipe(recipeInputDto, savedRecipe2);
        Recipe savedRecipe3 = recipeRepository.save(savedRecipe2);
        addInstructionToRecipe(recipeInputDto, savedRecipe3);
        recipeRepository.save(savedRecipe3);

        // Automaticaly sending an email to admin when someone post a new recipe
//        EmailDetails test = new EmailDetails("e.vanduikeren@gmail.com", "Er is een nieuw recept toegevoegd", "nieuw recept toegevoegd", "C:/Users/evand/Novi/Eindopdracht/Onderliggend/Assets/bramenjam.jpg");
//        this.emailService.sendMailWithAttachment(test);

        return savedRecipe1.getId();
    }

    // PutMapping, function for changing a (whole) recipe
    public RecipeOutputDto putRecipe(Long id, RecipeInputDto recipeInputDto) {
        {
            if (recipeRepository.findById(id).isPresent()) {
                Recipe recipe = recipeRepository.findById(id).get();
                Recipe recipe1 = transferToRecipe(recipeInputDto);
                recipe1.setId(recipe.getId());
                recipeRepository.save(recipe1);
                return transferToDto(recipe1);
            } else {
                throw new RecordNotFoundException("No recipe found with id: " + id + ".");
            }
        }
    }

    // Patchmapping, function for changing parts of a recipe
    public RecipeOutputDto patchRecipe(Long id, RecipeInputDto recipeInputDto) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (recipeRepository.existsById(id)) {
            Recipe recipeToUpdate = optionalRecipe.get();
            if (recipeInputDto.getTitle() != null) {
                recipeToUpdate.setTitle(recipeInputDto.getTitle());
            }
            if (recipeInputDto.getSub_title() != null) {
                recipeToUpdate.setSub_title(recipeInputDto.getSub_title());
            }
            if (recipeInputDto.getPersons() != null) {
                recipeToUpdate.setPersons(recipeInputDto.getPersons());
            }
            if (recipeInputDto.getSource() != null) {
                recipeToUpdate.setSource(recipeInputDto.getSource());
            }
            if (recipeInputDto.getStory() != null) {
                recipeToUpdate.setStory(recipeInputDto.getStory());
            }
            if (recipeInputDto.getPrep_time() != null) {
                recipeToUpdate.setPrep_time(recipeInputDto.getPrep_time());
            }
            if (recipeInputDto.getCook_time() != null) {
                recipeToUpdate.setCook_time(recipeInputDto.getCook_time());
            }
            if (recipeInputDto.getIngredients() != null) {
                recipeToUpdate.setIngredients(recipeInputDto.getIngredients());
            }
            if (recipeInputDto.getInstructions() != null) {
                recipeToUpdate.setInstructions(recipeInputDto.getInstructions());
            }
            if (recipeInputDto.getUtensils() != null) {
                recipeToUpdate.setUtensils(recipeInputDto.getUtensils());
            }
            if (recipeInputDto.getMonths() != null) {
                recipeToUpdate.setMonths(recipeInputDto.getMonths());
            }
            if (recipeInputDto.getTags() != null) {
                recipeToUpdate.setTags(recipeInputDto.getTags());
            }

            Recipe savedRecipe = recipeRepository.save(recipeToUpdate);
            return transferToDto(savedRecipe);
        } else {
            throw new RecordNotFoundException("No recipe with id " + id);
        }
    }

    // DeleteMapping, function for deleting a recipe
    public String deleteById(Long id) {
        if (recipeRepository.existsById(id)) {
            Optional<Recipe> deletedRecipe = recipeRepository.findById(id);
            Recipe recipe1 = deletedRecipe.get();
            recipeRepository.delete(recipe1);
            return "Recipe with id: " + id + " deleted.";
        } else {
            throw new RecordNotFoundException("No recipe found with id: " + id + ".");
        }
    }


    // relations...........................................................................................................
    public void assignIngredientToRecipe(Long id, Long IngredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(IngredientId);
        if (optionalRecipe.isPresent() && optionalIngredient != null) {
            Recipe recipe = optionalRecipe.get();
            Ingredient ingredient = optionalIngredient.get();
            recipe.addIngredient(ingredient);
            recipeRepository.save(recipe);
        } else {
            throw new RecordNotFoundException();
        }
    }

    //assign photo to a recipe
    public void assignPhotoToRecipe(String fileName, Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        Optional<FileUpload> fileUploadResponse = uploadRepository.findByFileName(fileName);
        if (optionalRecipe.isPresent() && fileUploadResponse.isPresent()) {
            FileUpload photo = fileUploadResponse.get();
            Recipe recipe = optionalRecipe.get();
            recipe.setFile(photo);
            recipeRepository.save(recipe);
        }
    }


    // helper methods......................................................................................................
    // helper method from Recipe to Dto
    private RecipeOutputDto transferToDto(Recipe recipe) {
        RecipeOutputDto recipeOutputDto = new RecipeOutputDto();
        recipeOutputDto.setId(recipe.getId());
        recipeOutputDto.setTitle(recipe.getTitle());
        recipeOutputDto.setSub_title(recipe.getSub_title());
        recipeOutputDto.setPersons(recipe.getPersons());
        recipeOutputDto.setSource(recipe.getSource());
        recipeOutputDto.setStory(recipe.getStory());
        recipeOutputDto.setPrep_time(recipe.getPrep_time());
        recipeOutputDto.setCook_time(recipe.getCook_time());
        if (recipe.getIngredients() != null) {
            recipeOutputDto.setIngredients(recipe.getIngredients());
        }
        if (recipe.getInstructions() != null) {
            recipeOutputDto.setInstructions(recipe.getInstructions());
        }
        if (recipe.getUtensils() != null) {
            recipeOutputDto.setUtensils(recipe.getUtensils());
        }
        if (recipe.getMonths() != null) {
            recipeOutputDto.setMonths(recipe.getMonths());
        }
        if (recipe.getTags() != null) {
            recipeOutputDto.setTags(recipe.getTags());
        }

        if (recipe.getFile() != null) {
            recipeOutputDto.setFile(recipe.getFile());
        }
        return recipeOutputDto;
    }

    //helper method from Dto to Recipe
    public Recipe transferToRecipe(RecipeInputDto recipeInputDto) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeInputDto.getId());
        recipe.setTitle(recipeInputDto.getTitle());
        recipe.setSub_title(recipeInputDto.getSub_title());
        recipe.setPersons(recipeInputDto.getPersons());
        recipe.setSource(recipeInputDto.getSource());
        recipe.setStory(recipeInputDto.getStory());
        recipe.setPrep_time(recipeInputDto.getPrep_time());
        recipe.setCook_time(recipeInputDto.getCook_time());

        recipe.setMonths(recipeInputDto.getMonths());
        recipe.setTags(recipeInputDto.getTags());

        recipe.setFile(recipeInputDto.getFile());

        return recipe;
    }

//    helper methods to add utensils, ingredients and instructions to these lists and connect to recipe
    public void addUtensilToRecipe(RecipeInputDto recipeInputDto, Recipe recipe) {
        for (Utensil utensil : recipeInputDto.getUtensils()) {
            if (utensilRepository.existsByUtensil(utensil.getUtensil())) {
                continue;
            } else {
                utensil.setRecipe(recipe);
                utensilRepository.save(utensil);
            }
        }
    }

    // onderstaande werkt nog niet, want ingredients heeft 3 inputfields nl unit amount en ingredient_name
    public void addIngredientToRecipe(RecipeInputDto recipeInputDto, Recipe recipe) {
//        for (Ingredient ingredient : recipeInputDto.getIngredients()) {
//            if (ingredientRepository.existsByIngredient(ingredient.getIngredient_name())) {
//                continue;
//            } else {
//                ingredient.setRecipe(recipe);
//                ingredientRepository.save(ingredient);
//            }
//        }
    }

    public void addInstructionToRecipe(RecipeInputDto recipeInputDto, Recipe recipe) {
        for (Instruction instruction : recipeInputDto.getInstructions()) {
            if (instructionRepository.existsByInstruction(instruction.getInstruction())) {
                continue;
            } else {
                instruction.setRecipe(recipe);
                instructionRepository.save(instruction);
            }
        }
    }


}





