package novi.nl.wildplukrecepten.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InstructionController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class UtensilControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllUtensils() {
    }

    @Test
    void getUtensil() {
    }

    @Test
    void createUtensil() {
    }

    @Test
    void updateUtensil() {
    }

    @Test
    void updatePartOfUtensil() {
    }

    @Test
    void deleteUtensil() {
    }
}