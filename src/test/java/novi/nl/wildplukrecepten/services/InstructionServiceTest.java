package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.InstructionDto;
import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.repositories.InstructionRepository;
import org.junit.jupiter.api.AfterEach;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class InstructionServiceTest {
    @Mock
    InstructionRepository instructionRepository;

    @InjectMocks
    InstructionService instructionService;

    @Captor
    ArgumentCaptor<Instruction> captor;

    Instruction instruction1;
    Instruction instruction2;
    InstructionDto instructionDto1;
    InstructionDto instructionDto2;


    @BeforeEach
    void setUp() {
        Recipe recipe1 = null;
        instruction1 = new Instruction(1L, "Doe 1", recipe1);
        instruction2 = new Instruction(2L, "Doe 2", recipe1);
        instructionDto1 = new InstructionDto(1L, "Doe 1", null);
        instructionDto2 = new InstructionDto(2L, "Doe 2", null);
    }

    @AfterEach
    void tearDown() {
    }



    // testen..........................................................
    @Test
    void getAllInstructions() {
        //Arrange
        // Act
        when(instructionRepository.findAll()).thenReturn(List.of(instruction1, instruction2));

        List<Instruction> instructionsFound = instructionService.transferInstructionDtoListToInstructionList(instructionService.getAllInstructions());

        //Assert
        assertEquals(instruction1.getInstruction(), instructionsFound.get(0).getInstruction());
        assertEquals(instruction2.getInstruction(), instructionsFound.get(1).getInstruction());
    }


    @Test
    void getInstruction() {
        //Arrange
        //Act
        when(instructionRepository.findById(1L)).thenReturn(Optional.of(instruction1));
        InstructionDto instructionDto = instructionService.getInstruction(1L);

        //Assert
        assertEquals(instruction1.getId(), instructionDto.getId());
    }



    @Test
    void createInstruction() {
        //Arrange
        //Act
        when(instructionRepository.save(instruction1)).thenReturn(instruction1);

        instructionService.createInstruction(instructionDto1);
        verify(instructionRepository, times(1)).save(captor.capture());
        Instruction instruction = captor.getValue();

        assertEquals(instruction1.getId(), instruction.getId());
    }


    @Test
    void putInstruction() {

    }

    @Test
    void patchInstruction() {
    }

    @Test
    void deleteById() {
    }
}