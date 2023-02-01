package novi.nl.wildplukrecepten.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novi.nl.wildplukrecepten.dtos.RecipeDto;
import novi.nl.wildplukrecepten.filter.JwtRequestFilter;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.services.FileUploadService;
import novi.nl.wildplukrecepten.services.RecipeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipeController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtRequestFilter jwtRequestFilter;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    FileUploadController fileUploadController;

    @MockBean
    FileUploadService fileUploadService;

    Recipe recipe1;
    Recipe recipe2;

    // Dtos
    RecipeDto recipeDto1;
    RecipeDto recipeDto2;
    RecipeDto recipeDto3;
    RecipeDto recipeDto4;

    //input Dtos
    RecipeDto recipeDto5;
    RecipeDto recipeDto6;


    @BeforeEach
    void setUp() {
        recipe1 = new Recipe(1L, "title1", "subtitle1", 4, "source1", "story1", "preptime1", "cooktime1", null, null, null, null, null, null);
        recipe2 = new Recipe(2L, "title2", "subtitle2", 4, "source2", "story2", "preptime2", "cooktime2", null, null, null, null, null, null);

        recipeDto1 = new RecipeDto(1L, "title1", "subtitle1", 4, "source1", "story1", "preptime1", "cooktime1", null, null, null, null, null, null);
        recipeDto2 = new RecipeDto(2L, "title2", "subtitle2", 4, "source2", "story2", "preptime2", "cooktime2", null, null, null, null, null, null);
        recipeDto3 = new RecipeDto(3L, "title3", "subtitle3", 4, "source3", "story3", "preptime3", "cooktime3", null, null, null, null, null, null);
        recipeDto4 = new RecipeDto(1L, "title2", "subtitle2", 4, "source2", "story2", "preptime2", "cooktime2", null, null, null, null, null, null);

        recipeDto5 = new RecipeDto(1L, "title1", "subtitle1", 4, "source1", "story1", "preptime1", "cooktime1", null, null, null, null, null, null);
        recipeDto6 = new RecipeDto(1L, "title2", "subtitle2", 4, "source2", "story2", "preptime2", "cooktime2", null, null, null, null, null, null);
    }


    @Test
    void getAllRecipes() throws Exception {
        given(recipeService.getAllRecipes()).willReturn(List.of(recipeDto1, recipeDto2, recipeDto3));

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andExpect(jsonPath("$[0].sub_title").value("subtitle1"))
                .andExpect(jsonPath("$[0].persons").value(4))
                .andExpect(jsonPath("$[0].source").value("source1"))
                .andExpect(jsonPath("$[0].story").value("story1"))
                .andExpect(jsonPath("$[0].prep_time").value("preptime1"))
                .andExpect(jsonPath("$[0].cook_time").value("cooktime1"))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("title2"))
                .andExpect(jsonPath("$[1].sub_title").value("subtitle2"))
                .andExpect(jsonPath("$[1].persons").value(4))
                .andExpect(jsonPath("$[1].source").value("source2"))
                .andExpect(jsonPath("$[1].story").value("story2"))
                .andExpect(jsonPath("$[1].prep_time").value("preptime2"))
                .andExpect(jsonPath("$[1].cook_time").value("cooktime2"))

                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].title").value("title3"))
                .andExpect(jsonPath("$[2].sub_title").value("subtitle3"))
                .andExpect(jsonPath("$[2].persons").value(4))
                .andExpect(jsonPath("$[2].source").value("source3"))
                .andExpect(jsonPath("$[2].story").value("story3"))
                .andExpect(jsonPath("$[2].prep_time").value("preptime3"))
                .andExpect(jsonPath("$[2].cook_time").value("cooktime3"))
        ;
    }


    @Test
    void getRecipe() throws Exception {
        given(recipeService.getRecipe(anyLong())).willReturn(recipeDto1);

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("title1"))
                .andExpect(jsonPath("sub_title").value("subtitle1"))
                .andExpect(jsonPath("persons").value(4))
                .andExpect(jsonPath("source").value("source1"))
                .andExpect(jsonPath("story").value("story1"))
                .andExpect(jsonPath("prep_time").value("preptime1"))
                .andExpect(jsonPath("cook_time").value("cooktime1"))
        ;
    }


    @Test
    void createRecipe() throws Exception {
        given(recipeService.createRecipe(recipeDto5)).willReturn(recipeDto1.getId());

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipeDto5)))
                .andExpect(status().isCreated())
        ;
    }


    @Test
    void updateRecipe() throws Exception {
        given(recipeService.putRecipe(1L, recipeDto6)).willReturn(recipeDto4);

        mockMvc.perform(put("/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipeDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("title2"))
                .andExpect(jsonPath("sub_title").value("subtitle2"))
                .andExpect(jsonPath("persons").value(4))
                .andExpect(jsonPath("source").value("source2"))
                .andExpect(jsonPath("story").value("story2"))
                .andExpect(jsonPath("prep_time").value("preptime2"))
                .andExpect(jsonPath("cook_time").value("cooktime2"))
        ;
    }

    @Test
    void updatePartOfRecipe() throws Exception {
        given(recipeService.patchRecipe(1L, recipeDto6)).willReturn(recipeDto4);

        mockMvc.perform(patch("/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipeDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("title2"))
                .andExpect(jsonPath("sub_title").value("subtitle2"))
                .andExpect(jsonPath("persons").value(4))
                .andExpect(jsonPath("source").value("source2"))
                .andExpect(jsonPath("story").value("story2"))
                .andExpect(jsonPath("prep_time").value("preptime2"))
                .andExpect(jsonPath("cook_time").value("cooktime2"))
        ;
    }


    @Test
    void deleteRecipe() throws Exception {
        mockMvc.perform(delete("/recipes/1"))
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