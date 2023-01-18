package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.InstructionDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.repositories.InstructionRepository;
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
        instruction1 = new Instruction(1L, "Do 1", null);
        instruction2 = new Instruction(2L, "Do 2", null);
        instructionDto1 = new InstructionDto(1L, "Do 1", null);
        instructionDto2 = new InstructionDto(2L, "Do 2", null);
    }


    // testen..........................................................
    @Test
    void getAllInstructions() {
        when(instructionRepository.findAll()).thenReturn(List.of(instruction1, instruction2));

        List<Instruction> instructionsFound = instructionService.transferInstructionDtoListToInstructionList(instructionService.getAllInstructions());

        assertEquals(instruction1.getInstruction(), instructionsFound.get(0).getInstruction());
        assertEquals(instruction2.getInstruction(), instructionsFound.get(1).getInstruction());
    }


    @Test
    void getInstruction() {
        when(instructionRepository.findById(1L)).thenReturn(Optional.of(instruction1));
        InstructionDto instructionDto = instructionService.getInstruction(1L);

        assertEquals(instruction1.getId(), instructionDto.getId());
    }

    @Test
    void getInstructionThrowsExceptionForInstructionTest() {
        assertThrows(RecordNotFoundException.class, () -> instructionService.getInstruction(null));
    }


    @Test
    void createInstruction() {
        when(instructionRepository.save(instruction1)).thenReturn(instruction1);

        instructionService.createInstruction(instructionDto1);
        verify(instructionRepository, times(1)).save(captor.capture());
        Instruction captured = captor.getValue();

        assertEquals(instruction1.getId(), captured.getId());
    }


    @Test
    void putInstruction() {
        when(instructionRepository.findById(1L)).thenReturn(Optional.of(instruction1));
        when(instructionRepository.existsById(1L)).thenReturn(true);
        when(instructionRepository.save(any())).thenReturn(instruction2);

        instructionService.putInstruction(1L, instructionDto1);

        verify(instructionRepository, times(1)).save(captor.capture());
        Instruction captured = captor.getValue();

        assertEquals(instruction1.getId(), captured.getId());
        assertEquals(instruction1.getInstruction(), captured.getInstruction());
        assertEquals(instruction1.getRecipe(), captured.getRecipe());
    }

    @Test
    void putInstructionThrowsExceptionForInstructionTest() {
        assertThrows(RecordNotFoundException.class, () -> instructionService.putInstruction(1L, new InstructionDto(3L, "Doe 3", null)));
    }

    @Test
    void patchInstruction() {
        when(instructionRepository.findById(1L)).thenReturn(Optional.of(instruction1));
        when(instructionRepository.existsById(1L)).thenReturn(true);
        when(instructionRepository.save(any())).thenReturn(instruction2);

        instructionService.patchInstruction(1L, instructionDto1);

        verify(instructionRepository, times(1)).save(captor.capture());
        Instruction captured = captor.getValue();

        assertEquals(instruction1.getId(), captured.getId());
        assertEquals(instruction1.getInstruction(), captured.getInstruction());
        assertEquals(instruction1.getRecipe(), captured.getRecipe());
    }

    @Test
    void patchInstructionThrowsExceptionForInstructionTest() {
        assertThrows(RecordNotFoundException.class, () -> instructionService.patchInstruction(1L, new InstructionDto(3L, "Doe 3", null)));
    }

    @Test
    void deleteById() {
        when(instructionRepository.existsById(1L)).thenReturn(true);
        when(instructionRepository.findById(1L)).thenReturn(Optional.of(instruction1));
        instructionService.deleteById(1L);

        verify(instructionRepository).delete(instruction1);
    }

    @Test
    void deleteInstructionThrowsExceptionForInstructionTest() {
        assertThrows(RecordNotFoundException.class, () -> instructionService.deleteById(1L));
    }

}