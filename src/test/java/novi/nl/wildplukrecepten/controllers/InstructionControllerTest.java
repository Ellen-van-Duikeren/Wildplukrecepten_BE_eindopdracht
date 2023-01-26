package novi.nl.wildplukrecepten.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novi.nl.wildplukrecepten.dtos.InstructionDto;
import novi.nl.wildplukrecepten.filter.JwtRequestFilter;
import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.services.InstructionService;
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
@WebMvcTest(InstructionController.class)
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

    // dtos
    InstructionDto instructionDto1;
    InstructionDto instructionDto2;
    InstructionDto instructionDto3;
    InstructionDto instructionDto4;

    //input Dtos
    InstructionDto instructionDto5;
    InstructionDto instructionDto6;

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

        instructionDto5 = new InstructionDto(1L, "Do 1", recipe1);
        instructionDto6 = new InstructionDto(1L, "Do 2", recipe2);
    }


    @Test
    void getAllInstructions() throws Exception {
        given(instructionService.getAllInstructions()).willReturn(List.of(instructionDto1, instructionDto2, instructionDto3));

        mockMvc.perform(get("/instructions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].instruction").value("Do 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].instruction").value("Do 2"))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].instruction").value("Do 3"))
        ;
    }

    @Test
    void getInstruction() throws Exception {
        given(instructionService.getInstruction(anyLong())).willReturn(instructionDto1);

        mockMvc.perform(get("/instructions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("instruction").value("Do 1"))
        ;
    }


    @Test
    void createInstruction() throws Exception {
        given(instructionService.createInstruction(instructionDto5)).willReturn(instructionDto1.getId());

        mockMvc.perform(post("/instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionDto5)))
                .andExpect(status().isCreated())
        ;
    }


    @Test
    void updateInstruction() throws Exception {
        given(instructionService.putInstruction(1L, instructionDto6)).willReturn(instructionDto4);

        mockMvc.perform(put("/instructions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("instruction").value("Do 2"))
        ;
    }

    @Test
    void updatePartOfInstruction() throws Exception {
        given(instructionService.putInstruction(1L, instructionDto6)).willReturn(instructionDto4);

        mockMvc.perform(put("/instructions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionDto6)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("instruction").value("Do 2"))
        ;
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