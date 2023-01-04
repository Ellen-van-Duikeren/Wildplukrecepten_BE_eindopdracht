package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.inputDto.IngredientInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.IngredientOutputDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.Ingredient;
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

    public List<IngredientOutputDto> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        ArrayList<IngredientOutputDto> ingredientOutputDtos = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            IngredientOutputDto ingredientOutputDto = transferIngredientToDto(ingredient);
            ingredientOutputDtos.add(ingredientOutputDto);
        }
        return ingredientOutputDtos;
    }

    public IngredientOutputDto getIngredient(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient1 = optionalIngredient.get();
            return transferIngredientToDto(ingredient1);
        } else {
            throw new RecordNotFoundException("No ingredient found with id: " + id + ".");
        }
    }

    public Long createIngredient(IngredientInputDto ingredientInputDto) {
        Ingredient newIngredient = new Ingredient();
        newIngredient = transferDtoToIngredient(ingredientInputDto);
        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        return savedIngredient.getId();
    }

    public IngredientOutputDto putIngredient(Long id, IngredientInputDto ingredientInputDto) {
        {
            if (ingredientRepository.findById(id).isPresent()) {
                Ingredient ingredient = ingredientRepository.findById(id).get();
                Ingredient ingredient1 = transferDtoToIngredient(ingredientInputDto);
                ingredient1.setId(ingredient.getId());
                ingredientRepository.save(ingredient1);
                return transferIngredientToDto(ingredient1);
            } else {
                throw new RecordNotFoundException("No ingredient found with id: " + id + ".");
            }
        }
    }

    public IngredientOutputDto patchIngredient(Long id, IngredientInputDto ingredientInputDto) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (ingredientRepository.existsById(id)) {
            Ingredient ingredientToUpdate = optionalIngredient.get();

            if (ingredientInputDto.getAmount() != null) {
                ingredientToUpdate.setAmount(ingredientInputDto.getAmount());
            }
            if (ingredientInputDto.getUnit() != null) {
                ingredientToUpdate.setUnit(ingredientInputDto.getUnit());
            }
            if (ingredientInputDto.getIngredient_name() != null) {
                ingredientToUpdate.setIngredient_name(ingredientInputDto.getIngredient_name());
            }

            Ingredient savedIngredient = ingredientRepository.save(ingredientToUpdate);
            return transferIngredientToDto(savedIngredient);
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
    private IngredientOutputDto transferIngredientToDto(Ingredient ingredient) {
        IngredientOutputDto ingredientOutputDto = new IngredientOutputDto();
        ingredientOutputDto.setRecipe(ingredient.getRecipe());
        ingredientOutputDto.setId(ingredient.getId());
        ingredientOutputDto.setAmount(ingredient.getAmount());
        ingredientOutputDto.setUnit(ingredient.getUnit());
        ingredientOutputDto.setIngredient_name(ingredient.getIngredient_name());
        return ingredientOutputDto;
    }

    private Ingredient transferDtoToIngredient(IngredientInputDto ingredientInputDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(ingredientInputDto.getRecipe());
        ingredient.setId(ingredientInputDto.getId());
        ingredient.setUnit(ingredientInputDto.getUnit());
        ingredient.setAmount(ingredientInputDto.getAmount());
        ingredient.setIngredient_name(ingredientInputDto.getIngredient_name());

        return ingredient;
    }
}