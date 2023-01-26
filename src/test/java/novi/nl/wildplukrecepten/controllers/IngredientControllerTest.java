package novi.nl.wildplukrecepten.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novi.nl.wildplukrecepten.dtos.IngredientDto;
import novi.nl.wildplukrecepten.filter.JwtRequestFilter;
import novi.nl.wildplukrecepten.models.Ingredient;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IngredientController.class)
@AutoConfigureMockMvc(addFilters = false)
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtRequestFilter jwtRequestFilter;

    @MockBean
    private IngredientService ingredientService;

    Ingredient ingredient1;
    Ingredient ingredient2;
    Ingredient ingredient3;

    // dtos
    IngredientDto ingredientDto1;
    IngredientDto ingredientDto2;
    IngredientDto ingredientDto3;
    IngredientDto ingredientDto4;

    //input Dtos
    IngredientDto ingredientDto5;
    IngredientDto ingredientDto6;

    @BeforeEach
    void setUp() {
        Recipe recipe1 = null;
        Recipe recipe2 = null;
        ingredient1 = new Ingredient(1L, 500.0,"gram", "ingredient1", recipe1);
        ingredient2 = new Ingredient(2L, 500.0,"gram", "ingredient2", recipe1);
        ingredient3 = new Ingredient(3L, 500.0,"gram", "ingredient3", recipe1);

        ingredientDto1 = new IngredientDto(1L, 500.0,"gram", "ingredient1", recipe1);
        ingredientDto2 = new IngredientDto(2L, 500.0,"gram", "ingredient2", recipe1);
        ingredientDto3 = new IngredientDto(3L, 500.0,"gram", "ingredient3", recipe2);
        ingredientDto4 = new IngredientDto(1L, 500.0,"gram", "ingredient2", recipe2);

        ingredientDto5 = new IngredientDto(1L, 500.0,"gram", "ingredient1", recipe1);
        ingredientDto6 = new IngredientDto(1L, 500.0,"gram", "ingredient2", recipe2);
    }


    @Test
    void getAllIngredients() throws Exception {
        given(ingredientService.getAllIngredients()).willReturn(List.of(ingredientDto1, ingredientDto2, ingredientDto3));

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].amount").value("500.0"))
                .andExpect(jsonPath("$[0].unit").value("gram"))
                .andExpect(jsonPath("$[0].ingredient_name").value("ingredient1"))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].amount").value("500.0"))
                .andExpect(jsonPath("$[1].unit").value("gram"))
                .andExpect(jsonPath("$[1].ingredient_name").value("ingredient2"))

                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].amount").value("500.0"))
                .andExpect(jsonPath("$[2].unit").value("gram"))
                .andExpect(jsonPath("$[2].ingredient_name").value("ingredient3"))
        ;
    }

    @Test
    void getIngredient() throws Exception {
        given(ingredientService.getIngredient(anyLong())).willReturn(ingredientDto1);

        mockMvc.perform(get("/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("amount").value("500.0"))
                .andExpect(jsonPath("unit").value("gram"))
                .andExpect(jsonPath("ingredient_name").value("ingredient1"))
        ;
    }


    @Test
    void createIngredient() throws Exception {
        given(ingredientService.createIngredient(ingredientDto5)).willReturn(ingredientDto1.getId());

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ingredientDto5)))
                .andExpect(status().isCreated())
        ;
    }


    @Test
    void updateIngredient() throws Exception {
        given(ingredientService.putIngredient(1L, ingredientDto6)).willReturn(ingredientDto4);

        mockMvc.perform(put("/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ingredientDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("amount").value("500.0"))
                .andExpect(jsonPath("unit").value("gram"))
                .andExpect(jsonPath("ingredient_name").value("ingredient2"))
        ;
    }

    @Test
    void updatePartOfIngredient() throws Exception {
        given(ingredientService.putIngredient(1L, ingredientDto6)).willReturn(ingredientDto4);

        mockMvc.perform(put("/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ingredientDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("amount").value("500.0"))
                .andExpect(jsonPath("unit").value("gram"))
                .andExpect(jsonPath("ingredient_name").value("ingredient2"))
        ;
    }

    @Test
    void deleteIngredient() throws Exception {
        mockMvc.perform(delete("/ingredients/1"))
                .andExpect(status().isNoContent());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}