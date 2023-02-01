package novi.nl.wildplukrecepten.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novi.nl.wildplukrecepten.dtos.UtensilDto;
import novi.nl.wildplukrecepten.filter.JwtRequestFilter;
import novi.nl.wildplukrecepten.models.Utensil;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.services.UtensilService;
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
@WebMvcTest(UtensilController.class)
@AutoConfigureMockMvc(addFilters = false)
class UtensilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtRequestFilter jwtRequestFilter;

    @MockBean
    private UtensilService utensilService;

    Utensil utensil1;
    Utensil utensil2;
    Utensil utensil3;

    // dtos
    UtensilDto utensilDto1;
    UtensilDto utensilDto2;
    UtensilDto utensilDto3;
    UtensilDto utensilDto4;

    //input Dtos
    UtensilDto utensilDto5;
    UtensilDto utensilDto6;

    @BeforeEach
    void setUp() {
        Recipe recipe1 = null;
        Recipe recipe2 = null;
        utensil1 = new Utensil(1L, "Utensil1", recipe1);
        utensil2 = new Utensil(2L, "Utensil2", recipe1);
        utensil3 = new Utensil(3L, "Utensil3", recipe2);

        utensilDto1 = new UtensilDto(1L, "Utensil1", recipe1);
        utensilDto2 = new UtensilDto(2L, "Utensil2", recipe1);
        utensilDto3 = new UtensilDto(3L, "Utensil3", recipe2);
        utensilDto4 = new UtensilDto(1L, "Utensil2", recipe2);

        utensilDto5 = new UtensilDto(1L, "Utensil1", recipe1);
        utensilDto6 = new UtensilDto(1L, "Utensil2", recipe2);
    }


    @Test
    void getAllUtensils() throws Exception {
        given(utensilService.getAllUtensils()).willReturn(List.of(utensilDto1, utensilDto2, utensilDto3));

        mockMvc.perform(get("/utensils"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].utensil").value("Utensil1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].utensil").value("Utensil2"))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].utensil").value("Utensil3"))
        ;
    }

    @Test
    void getUtensil() throws Exception {
        given(utensilService.getUtensil(anyLong())).willReturn(utensilDto1);

        mockMvc.perform(get("/utensils/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("utensil").value("Utensil1"))
        ;
    }


    @Test
    void createUtensil() throws Exception {
        given(utensilService.createUtensil(utensilDto5)).willReturn(utensilDto1.getId());

        mockMvc.perform(post("/utensils")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(utensilDto5)))
                .andExpect(status().isCreated())
        ;
    }


    @Test
    void updateUtensil() throws Exception {
        given(utensilService.putUtensil(1L, utensilDto6)).willReturn(utensilDto4);

        mockMvc.perform(put("/utensils/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(utensilDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("utensil").value("Utensil2"))
        ;
    }


    @Test
    void updatePartOfUtensil() throws Exception {
        given(utensilService.patchUtensil(1L, utensilDto6)).willReturn(utensilDto4);

        mockMvc.perform(patch("/utensils/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(utensilDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("utensil").value("Utensil2"))
        ;
    }


    @Test
    void deleteUtensil() throws Exception {
        mockMvc.perform(delete("/utensils/1"))
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