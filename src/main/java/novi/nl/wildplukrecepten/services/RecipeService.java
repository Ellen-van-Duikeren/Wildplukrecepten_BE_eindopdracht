package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dtos.RecipeDto;
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
    public final EmailRepository emailService;


    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, UtensilRepository utensilRepository, InstructionRepository instructionRepository, FileUploadRepository uploadRepository, EmailRepository emailService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.utensilRepository = utensilRepository;
        this.instructionRepository = instructionRepository;
        this.uploadRepository = uploadRepository;
        this.emailService = emailService;
    }


    // GetMapping, function for getting all recipes
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDto> recipeDtos = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeDto recipeDto = transferRecipeToRecipeDto(recipe);
            recipeDtos.add(recipeDto);
        }
        return recipeDtos;
    }

    // GetMapping by id, function for getting a recipe by id
    public RecipeDto getRecipe(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe ingredient1 = optionalRecipe.get();
            return transferRecipeToRecipeDto(ingredient1);
        } else {
            throw new RecordNotFoundException("No recipe found with id: " + id + ".");
        }
    }

    // PostMapping, function for adding a recipe
    public Long createRecipe(RecipeDto recipeDto) {
        Recipe newRecipe;
        newRecipe = transferRecipeDtoToRecipe(recipeDto);
        Recipe savedRecipe = recipeRepository.save(newRecipe);

        addUtensilToRecipe(recipeDto, savedRecipe);
        addIngredientToRecipe(recipeDto, savedRecipe);
        addInstructionToRecipe(recipeDto, savedRecipe);

        // Automaticaly sending an email to my address, when someone post a new recipe
        EmailDetails email = new EmailDetails("e.vanduikeren@gmail.com", "Er is een nieuw recept toegevoegd.", "nieuw recept toegevoegd");
        this.emailService.sendSimpleMail(email);

        return savedRecipe.getId();
    }

    // PutMapping, function for changing a (whole) recipe
    public RecipeDto putRecipe(Long id, RecipeDto recipeDto) {
        {
            if (recipeRepository.findById(id).isPresent()) {
                Recipe recipe = recipeRepository.findById(id).get();
                Recipe recipe1 = transferRecipeDtoToRecipe(recipeDto);
                recipe1.setId(recipe.getId());
                recipeRepository.save(recipe1);
                return transferRecipeToRecipeDto(recipe1);
            } else {
                throw new RecordNotFoundException("No recipe found with id: " + id + ".");
            }
        }
    }

    // Patchmapping, function for changing parts of a recipe
    public RecipeDto patchRecipe(Long id, RecipeDto recipeDto) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (recipeRepository.existsById(id)) {
            Recipe recipeToUpdate = optionalRecipe.get();
            if (recipeDto.getTitle() != null) {
                recipeToUpdate.setTitle(recipeDto.getTitle());
            }
            if (recipeDto.getSub_title() != null) {
                recipeToUpdate.setSub_title(recipeDto.getSub_title());
            }
            if (recipeDto.getPersons() != null) {
                recipeToUpdate.setPersons(recipeDto.getPersons());
            }
            if (recipeDto.getSource() != null) {
                recipeToUpdate.setSource(recipeDto.getSource());
            }
            if (recipeDto.getStory() != null) {
                recipeToUpdate.setStory(recipeDto.getStory());
            }
            if (recipeDto.getPrep_time() != null) {
                recipeToUpdate.setPrep_time(recipeDto.getPrep_time());
            }
            if (recipeDto.getCook_time() != null) {
                recipeToUpdate.setCook_time(recipeDto.getCook_time());
            }
            if (recipeDto.getIngredients() != null) {
                recipeToUpdate.setIngredients(recipeDto.getIngredients());
            }
            if (recipeDto.getInstructions() != null) {
                recipeToUpdate.setInstructions(recipeDto.getInstructions());
            }
            if (recipeDto.getUtensils() != null) {
                recipeToUpdate.setUtensils(recipeDto.getUtensils());
            }
            if (recipeDto.getMonths() != null) {
                recipeToUpdate.setMonths(recipeDto.getMonths());
            }
            if (recipeDto.getTags() != null) {
                recipeToUpdate.setTags(recipeDto.getTags());
            }

            Recipe savedRecipe = recipeRepository.save(recipeToUpdate);
            return transferRecipeToRecipeDto(savedRecipe);
        } else {
            throw new RecordNotFoundException("No recipe with id " + id);
        }
    }

    // DeleteMapping, function for deleting a recipe
    public String deleteById(Long id) {
        if (recipeRepository.existsById(id)) {
            Optional<Recipe> deletedRecipe = recipeRepository.findById(id);
            Recipe recipe1 = deletedRecipe.get();
            // deleting utensils, ingredients and instructions first
            for (Utensil utensil : recipe1.getUtensils()) {
                utensilRepository.delete(utensil);
            }
            for (Ingredient ingredient : recipe1.getIngredients()) {
                ingredientRepository.delete(ingredient);
            }
            for (Instruction instruction : recipe1.getInstructions()) {
                instructionRepository.delete(instruction);
            }
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
    private RecipeDto transferRecipeToRecipeDto(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setSub_title(recipe.getSub_title());
        recipeDto.setPersons(recipe.getPersons());
        recipeDto.setSource(recipe.getSource());
        recipeDto.setStory(recipe.getStory());
        recipeDto.setPrep_time(recipe.getPrep_time());
        recipeDto.setCook_time(recipe.getCook_time());
        if (recipe.getIngredients() != null) {
            recipeDto.setIngredients(recipe.getIngredients());
        }
        if (recipe.getInstructions() != null) {
            recipeDto.setInstructions(recipe.getInstructions());
        }
        if (recipe.getUtensils() != null) {
            recipeDto.setUtensils(recipe.getUtensils());
        }
        if (recipe.getMonths() != null) {
            recipeDto.setMonths(recipe.getMonths());
        }
        if (recipe.getTags() != null) {
            recipeDto.setTags(recipe.getTags());
        }

        if (recipe.getFile() != null) {
            recipeDto.setFile(recipe.getFile());
        }
        return recipeDto;
    }


    //helper method from Dto to Recipe
    public Recipe transferRecipeDtoToRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setTitle(recipeDto.getTitle());
        recipe.setSub_title(recipeDto.getSub_title());
        recipe.setPersons(recipeDto.getPersons());
        recipe.setSource(recipeDto.getSource());
        recipe.setStory(recipeDto.getStory());
        recipe.setPrep_time(recipeDto.getPrep_time());
        recipe.setCook_time(recipeDto.getCook_time());

        recipe.setMonths(recipeDto.getMonths());
        recipe.setTags(recipeDto.getTags());

        recipe.setFile(recipeDto.getFile());

        return recipe;
    }

    public List<Recipe> transferRecipeDtoListToRecipeList(List<RecipeDto> ingredientsdtos) {
        List<Recipe> ingredients = new ArrayList<>();
        for (RecipeDto ingredientsdto : ingredientsdtos) {
            ingredients.add(transferRecipeDtoToRecipe(ingredientsdto));
        }
        return ingredients;
    }


    //    helper methods to add utensils, ingredients and instructions to these lists and connect to recipe
    public void addUtensilToRecipe(RecipeDto recipeDto, Recipe recipe) {
        for (Utensil utensil : recipeDto.getUtensils()) {
            utensil.setRecipe(recipe);
            utensilRepository.save(utensil);
        }
    }

    public void addIngredientToRecipe(RecipeDto recipeDto, Recipe recipe) {
        for (Ingredient ingredient : recipeDto.getIngredients()) {
            ingredient.setRecipe(recipe);
            ingredientRepository.save(ingredient);
        }
    }

    public void addInstructionToRecipe(RecipeDto recipeDto, Recipe recipe) {
        for (Instruction instruction : recipeDto.getInstructions()) {
            instruction.setRecipe(recipe);
            instructionRepository.save(instruction);
        }
    }


}





