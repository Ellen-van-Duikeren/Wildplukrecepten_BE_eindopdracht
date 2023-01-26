package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dtos.IngredientDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.Ingredient;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.repositories.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class IngredientServiceTest {
    @Mock
    IngredientRepository ingredientRepository;

    @InjectMocks
    IngredientService ingredientService;

    @Captor
    ArgumentCaptor<Ingredient> captor;

    Ingredient ingredient1;
    Ingredient ingredient2;
    IngredientDto ingredientDto1;
    IngredientDto ingredientDto2;



    @BeforeEach
    void setUp() {
        Recipe recipe1 = null;
        Recipe recipe2 = null;
        ingredient1 = new Ingredient(1L, 500.0,"gram", "ingredient1", recipe1);
        ingredient2 = new Ingredient(2L, 500.0,"gram", "ingredient2", recipe1);

        ingredientDto1 = new IngredientDto(1L, 500.0,"gram", "ingredient1", recipe1);
        ingredientDto2 = new IngredientDto(2L, 500.0,"gram", "ingredient2", recipe1);
    }


    // testen..........................................................
    @Test
    void getAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient1, ingredient2));

        List<Ingredient> ingredientsFound = ingredientService.transferIngredientDtoListToIngredientList(ingredientService.getAllIngredients());

        assertEquals(ingredient1.getAmount(), ingredientsFound.get(0).getAmount());
        assertEquals(ingredient1.getUnit(), ingredientsFound.get(0).getUnit());
        assertEquals(ingredient1.getIngredient_name(), ingredientsFound.get(0).getIngredient_name());

        assertEquals(ingredient2.getAmount(), ingredientsFound.get(1).getAmount());
        assertEquals(ingredient2.getUnit(), ingredientsFound.get(1).getUnit());
        assertEquals(ingredient2.getIngredient_name(), ingredientsFound.get(1).getIngredient_name())
        ;
    }


    @Test
    void getIngredient() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        IngredientDto ingredientDto = ingredientService.getIngredient(1L);

        assertEquals(ingredient1.getId(), ingredientDto.getId());
    }

    @Test
    void getIngredientThrowsExceptionForIngredientTest() {
        assertThrows(RecordNotFoundException.class, () -> ingredientService.getIngredient(null));
    }


    @Test
    void createIngredient() {
        when(ingredientRepository.save(ingredient1)).thenReturn(ingredient1);

        ingredientService.createIngredient(ingredientDto1);
        verify(ingredientRepository, times(1)).save(captor.capture());
        Ingredient captured = captor.getValue();

        assertEquals(ingredient1.getId(), captured.getId());
    }


    @Test
    void putIngredient() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.existsById(1L)).thenReturn(true);
        when(ingredientRepository.save(any())).thenReturn(ingredient2);

        ingredientService.putIngredient(1L, ingredientDto1);

        verify(ingredientRepository, times(1)).save(captor.capture());
        Ingredient captured = captor.getValue();

        assertEquals(ingredient1.getId(), captured.getId());
        assertEquals(ingredient1.getAmount(), captured.getAmount());
        assertEquals(ingredient1.getUnit(), captured.getUnit());
        assertEquals(ingredient1.getIngredient_name(), captured.getIngredient_name());
        assertEquals(ingredient1.getRecipe(), captured.getRecipe());
    }

    @Test
    void putIngredientThrowsExceptionForIngredientTest() {
        assertThrows(RecordNotFoundException.class, () -> ingredientService.putIngredient(1L, new IngredientDto(3L, 500.0, "gram", "ingredient3", null)));
    }

    @Test
    void patchIngredient() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.existsById(1L)).thenReturn(true);
        when(ingredientRepository.save(any())).thenReturn(ingredient2);

        ingredientService.patchIngredient(1L, ingredientDto1);

        verify(ingredientRepository, times(1)).save(captor.capture());
        Ingredient captured = captor.getValue();

        assertEquals(ingredient1.getId(), captured.getId());
        assertEquals(ingredient1.getAmount(), captured.getAmount());
        assertEquals(ingredient1.getUnit(), captured.getUnit());
        assertEquals(ingredient1.getIngredient_name(), captured.getIngredient_name());
        assertEquals(ingredient1.getRecipe(), captured.getRecipe());
    }

    @Test
    void patchIngredientThrowsExceptionForIngredientTest() {
        assertThrows(RecordNotFoundException.class, () -> ingredientService.patchIngredient(1L, new IngredientDto(3L, 500.0, "gram", "ingredient3", null)));
    }

    @Test
    void deleteById() {
        when(ingredientRepository.existsById(1L)).thenReturn(true);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        ingredientService.deleteById(1L);

        verify(ingredientRepository).delete(ingredient1);
    }

    @Test
    void deleteIngredientThrowsExceptionForIngredientTest() {
        assertThrows(RecordNotFoundException.class, () -> ingredientService.deleteById(1L));
    }

}