package novi.nl.wildplukrecepten.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import novi.nl.wildplukrecepten.dtos.UtensilDto;
import novi.nl.wildplukrecepten.models.Utensil;
import novi.nl.wildplukrecepten.repositories.UtensilRepository;
import novi.nl.wildplukrecepten.services.UtensilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@AutoConfigureMockMvc
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UtensilIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtensilService utensilService;

    @Autowired
    private UtensilRepository utensilRepository;

    Utensil utensil1;
    Utensil utensil2;
    UtensilDto utensilDto1;
    UtensilDto utensilDto2;
    UtensilDto utensilDto3;



    @BeforeEach
    void setUp() {
        utensil1 = new Utensil(1L, "Utensil1", null);
        utensil2 = new Utensil(2L, "Utensil2", null);

        utensilRepository.save(utensil1);
        utensilRepository.save(utensil2);

        //with utensil.getId
        utensilDto1 = new UtensilDto(utensil1.getId(), "Utensil1", null);
        utensilDto2 = new UtensilDto(utensil2.getId(), "Utensil2", null);
        //with hardcoded id
        utensilDto3 = new UtensilDto(3L, "Utensil3", null);
    }

    @Test
    void getAllUtensils() throws Exception {
        mockMvc.perform(get("/utensils"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(utensil1.getId().toString()))
                .andExpect(jsonPath("$[0].utensil").value("Utensil1"))
        ;
    }


    @Test
    void getUtensil() throws Exception {
        mockMvc.perform(get("/utensils/" + utensil1.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(utensil1.getId().toString()))
                .andExpect(jsonPath("utensil").value("Utensil1"))
        ;
    }

    @Test
    void createUtensil() throws Exception {
        mockMvc.perform(post("/utensils")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(utensilDto1)))
                .andExpect(status().isCreated())
        ;
    }

    @Test
    void putUtensil() throws Exception {
        mockMvc.perform(put("/utensils/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(utensilDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("utensil").value("Utensil2"))
        ;
    }

    @Test
    void patchUtensil() throws Exception {
        mockMvc.perform(put("/utensils/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(utensilDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("utensil").value("Utensil2"))
        ;
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/utensils/1"))
                .andExpect(status().isNoContent());
    }


    public static String asJsonString(final UtensilDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}