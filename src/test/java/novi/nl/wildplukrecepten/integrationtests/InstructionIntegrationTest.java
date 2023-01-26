package novi.nl.wildplukrecepten.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import novi.nl.wildplukrecepten.dtos.InstructionDto;
import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.repositories.InstructionRepository;
import novi.nl.wildplukrecepten.services.InstructionService;
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
class InstructionIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstructionService instructionService;

    @Autowired
    private InstructionRepository instructionRepository;

    Instruction instruction1;
    Instruction instruction2;
    InstructionDto instructionDto1;
    InstructionDto instructionDto2;
    InstructionDto instructionDto3;


    @BeforeEach
    void setUp() {
        instruction1 = new Instruction(1L, "Do 1", null);
        instruction2 = new Instruction(2L, "Do 2", null);

        instructionRepository.save(instruction1);
        instructionRepository.save(instruction2);

        //with instruction.getId
        instructionDto1 = new InstructionDto(instruction1.getId(), "Do 1", null);
        instructionDto2 = new InstructionDto(instruction2.getId(), "Do 2", null);
        //with hardcoded id
        instructionDto3 = new InstructionDto(3L, "Do 3", null);
    }

    @Test
    void getAllInstructions() throws Exception {
        mockMvc.perform(get("/instructions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(instruction1.getId().toString()))
                .andExpect(jsonPath("$[0].instruction").value("Do 1"))
        ;
    }


    @Test
    void getInstruction() throws Exception {
        mockMvc.perform(get("/instructions/" + instruction1.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(instruction1.getId().toString()))
                .andExpect(jsonPath("instruction").value("Do 1"))
        ;
    }

    @Test
    void createInstruction() throws Exception {
        mockMvc.perform(post("/instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionDto1)))
                .andExpect(status().isCreated())
        ;
    }

    @Test
    void putInstruction() throws Exception {
        mockMvc.perform(put("/instructions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("instruction").value("Do 2"))
        ;
    }

    @Test
    void patchInstruction() throws Exception {
        mockMvc.perform(put("/instructions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instructionDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("instruction").value("Do 2"))
        ;
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/instructions/1"))
                .andExpect(status().isNoContent());
    }


    public static String asJsonString(final InstructionDto obj) {
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