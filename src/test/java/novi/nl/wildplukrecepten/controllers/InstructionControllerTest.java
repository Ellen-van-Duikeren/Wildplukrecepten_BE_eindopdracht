package novi.nl.wildplukrecepten.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novi.nl.wildplukrecepten.dto.InstructionDto;
import novi.nl.wildplukrecepten.filter.JwtRequestFilter;
import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.services.InstructionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InstructionController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class InstructionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtRequestFilter jwtRequestFilter;

    @MockBean
    private InstructionService instructionService;

    Instruction instruction1;
    Instruction instruction2;
    Instruction instruction3;
    InstructionDto instructionDto1;
    InstructionDto instructionDto2;
    InstructionDto instructionDto3;
    InstructionDto instructionDto4;

    InstructionDto instructionInputDto1;

    InstructionDto instructionInputDto2;

    @BeforeEach
    void setUp() {
        Recipe recipe1 = null;
        Recipe recipe2 = null;
        instruction1 = new Instruction(1L, "Do 1", recipe1);
        instruction2 = new Instruction(2L, "Do 2", recipe1);
        instruction3 = new Instruction(3L, "Do 3", recipe2);

        instructionDto1 = new InstructionDto(1L, "Do 1", recipe1);
        instructionDto2 = new InstructionDto(2L, "Do 2", recipe1);
        instructionDto3 = new InstructionDto(3L, "Do 3", recipe2);
        instructionDto4 = new InstructionDto(1L, "Do 2", recipe2);

        instructionInputDto1 = new InstructionDto(1L, "Do 1", recipe1);
        instructionInputDto2 = new InstructionDto(1L, "Do 2", recipe2);
    }


    @Test
    void getAllInstructions() throws Exception {
        given(instructionService.getAllInstructions()).willReturn(List.of(instructionDto1, instructionDto2, instructionDto3));

        mockMvc.perform(get("/instructions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].instruction").value("Do 1"))
//                .andExpect(jsonPath("$[0].recipe").value(null))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].instruction").value("Do 2"))
//                .andExpect(jsonPath("$[1].recipe").value(null))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].instruction").value("Do 3"))
//                .andExpect(jsonPath("$[2].recipe").value(null))
        ;
    }

    @Test
    void getInstruction() throws Exception {
        given(instructionService.getInstruction(anyLong())).willReturn(instructionDto1);

        mockMvc.perform(get("/instructions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("instruction").value("Do 1"))
//                .andExpect(jsonPath("recipe").value(null))
        ;
    }


    @Test
    void createInstruction() throws Exception {
        given(instructionService.createInstruction(instructionInputDto1)).willReturn(instructionDto1.getId());

        mockMvc.perform(post("/instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionInputDto1)))
                .andExpect(status().isCreated())
        ;
    }


    @Test
    void updateInstruction() throws Exception {
        given(instructionService.putInstruction(1L, instructionInputDto2)).willReturn(instructionDto4);

        mockMvc.perform(put("/instructions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionInputDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("instruction").value("Do 2"))
//                .andExpect(jsonPath("recipe").value(recipe1))
        ;
    }

    @Test
    void updatePartOfInstruction() {
//        given(instructionService.updateInstruction(instructionInputDto2, 1L)).willReturn(instructionDto4);
//
//        mockMvc.perform(put("/instructions/9789076174105")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(instructionInputDto2)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(1L))
//                .andExpect(jsonPath("instruction").value("Do 1"))
//                .andExpect(jsonPath("recipe").value("en de geheime kamer"))
//                ;
    }

    @Test
    void deleteInstruction() throws Exception {
        mockMvc.perform(delete("/instructions/1"))
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