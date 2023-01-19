package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.IngredientDto;
import novi.nl.wildplukrecepten.dto.InstructionDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.Ingredient;
import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientDto> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        ArrayList<IngredientDto> ingredientDtos = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            IngredientDto ingredientDto = transferIngredientToIngredientDto(ingredient);
            ingredientDtos.add(ingredientDto);
        }
        return ingredientDtos;
    }

    public IngredientDto getIngredient(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient1 = optionalIngredient.get();
            return transferIngredientToIngredientDto(ingredient1);
        } else {
            throw new RecordNotFoundException("No ingredient found with id: " + id + ".");
        }
    }

    public Long createIngredient(IngredientDto ingredientDto) {
        Ingredient newIngredient = new Ingredient();
        newIngredient = transferIngredientDtoToIngredient(ingredientDto);
        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        return savedIngredient.getId();
    }

    public IngredientDto putIngredient(Long id, IngredientDto ingredientDto) {
        {
            if (ingredientRepository.findById(id).isPresent()) {
                Ingredient ingredient = ingredientRepository.findById(id).get();
                Ingredient ingredient1 = transferIngredientDtoToIngredient(ingredientDto);
                ingredient1.setId(ingredient.getId());
                ingredientRepository.save(ingredient1);
                return transferIngredientToIngredientDto(ingredient1);
            } else {
                throw new RecordNotFoundException("No ingredient found with id: " + id + ".");
            }
        }
    }

    public IngredientDto patchIngredient(Long id, IngredientDto ingredientDto) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (ingredientRepository.existsById(id)) {
            Ingredient ingredientToUpdate = optionalIngredient.get();

            if (ingredientDto.getAmount() != null) {
                ingredientToUpdate.setAmount(ingredientDto.getAmount());
            }
            if (ingredientDto.getUnit() != null) {
                ingredientToUpdate.setUnit(ingredientDto.getUnit());
            }
            if (ingredientDto.getIngredient_name() != null) {
                ingredientToUpdate.setIngredient_name(ingredientDto.getIngredient_name());
            }

            Ingredient savedIngredient = ingredientRepository.save(ingredientToUpdate);
            return transferIngredientToIngredientDto(savedIngredient);
        } else {
            throw new RecordNotFoundException("No ingredient with id " + id);
        }
    }

    public String deleteById(Long id) {
        if (ingredientRepository.existsById(id)) {
            Optional<Ingredient> deletedIngredient = ingredientRepository.findById(id);
            Ingredient ingredient1 = deletedIngredient.get();
            ingredientRepository.delete(ingredient1);
            return "Ingredient with id: " + id + " deleted.";
        } else {
            throw new RecordNotFoundException("No ingredient found with id: " + id + ".");
        }
    }


    //    helper methods.......................................................
    private IngredientDto transferIngredientToIngredientDto(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setRecipe(ingredient.getRecipe());
        ingredientDto.setId(ingredient.getId());
        ingredientDto.setAmount(ingredient.getAmount());
        ingredientDto.setUnit(ingredient.getUnit());
        ingredientDto.setIngredient_name(ingredient.getIngredient_name());
        return ingredientDto;
    }

    private Ingredient transferIngredientDtoToIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(ingredientDto.getRecipe());
        ingredient.setId(ingredientDto.getId());
        ingredient.setUnit(ingredientDto.getUnit());
        ingredient.setAmount(ingredientDto.getAmount());
        ingredient.setIngredient_name(ingredientDto.getIngredient_name());

        return ingredient;
    }

    public List<Ingredient> transferIngredientDtoListToIngredientList(List<IngredientDto> ingredientsdtos) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto ingredientsdto : ingredientsdtos) {
            ingredients.add(transferIngredientDtoToIngredient(ingredientsdto));
        }
        return ingredients;
    }

}