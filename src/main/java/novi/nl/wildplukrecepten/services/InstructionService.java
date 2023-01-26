package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dtos.InstructionDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.repositories.InstructionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructionService {
    private final InstructionRepository instructionRepository;

    public InstructionService(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }

    public List<InstructionDto> getAllInstructions() {
        List<Instruction> instructions = instructionRepository.findAll();
        ArrayList<InstructionDto> instructionDtos = new ArrayList<>();
        for (Instruction instruction : instructions) {
            InstructionDto instructionDto = transferInstructionToInstructionDto(instruction);
            instructionDtos.add(instructionDto);
        }
        return instructionDtos;
    }

    public InstructionDto getInstruction(Long id) {
        Optional<Instruction> optionalInstruction = instructionRepository.findById(id);
        if (optionalInstruction.isPresent()) {
            Instruction instruction1 = optionalInstruction.get();
            return transferInstructionToInstructionDto(instruction1);
        } else {
            throw new RecordNotFoundException("No instruction found with id: " + id + ".");
        }
    }

    public Long createInstruction(InstructionDto instructionDto) {
        Instruction newInstruction;
        newInstruction = transferInstructionDtoToInstruction(instructionDto);
        Instruction savedInstruction = instructionRepository.save(newInstruction);
        return savedInstruction.getId();
    }

    public InstructionDto putInstruction(Long id, InstructionDto instructionDto) {
        {
            if (instructionRepository.findById(id).isPresent()) {
                Instruction instruction = instructionRepository.findById(id).get();
                Instruction instruction1 = transferInstructionDtoToInstruction(instructionDto);
                instruction1.setId(instruction.getId());
                instructionRepository.save(instruction1);
                return transferInstructionToInstructionDto(instruction1);
            } else {
                throw new RecordNotFoundException("No instruction found with id: " + id + ".");
            }
        }
    }

    public InstructionDto patchInstruction(Long id, InstructionDto instructionDto) {
        Optional<Instruction> optionalInstruction = instructionRepository.findById(id);
        if (instructionRepository.existsById(id)) {
            Instruction instructionToUpdate = optionalInstruction.get();

            if (instructionDto.getInstruction() != null) {
                instructionToUpdate.setInstruction(instructionDto.getInstruction());
            }
            Instruction savedInstruction = instructionRepository.save(instructionToUpdate);
            return transferInstructionToInstructionDto(savedInstruction);
        } else {
            throw new RecordNotFoundException("No instruction with id " + id);
        }
    }

    public String deleteById(Long id) {
        if (instructionRepository.existsById(id)) {
            Optional<Instruction> deletedInstruction = instructionRepository.findById(id);
            Instruction instruction1 = deletedInstruction.get();
            instructionRepository.delete(instruction1);
            return "Instruction with id: " + id + " deleted.";
        } else {
            throw new RecordNotFoundException("No instruction found with id: " + id + ".");
        }
    }


    //    helper methods.......................................................
    public InstructionDto transferInstructionToInstructionDto(Instruction instruction) {
        InstructionDto instructionDto = new InstructionDto();
        instructionDto.setRecipe(instruction.getRecipe());
        instructionDto.setId(instruction.getId());
        instructionDto.setInstruction(instruction.getInstruction());
        return instructionDto;
    }

    public Instruction transferInstructionDtoToInstruction(InstructionDto instructionDto) {
        Instruction instruction = new Instruction();
        instruction.setRecipe(instructionDto.getRecipe());
        instruction.setId(instructionDto.getId());
        instruction.setInstruction(instructionDto.getInstruction());
        return instruction;
    }

    public List<Instruction> transferInstructionDtoListToInstructionList(List<InstructionDto> instructionsdtos) {
        List<Instruction> instructions = new ArrayList<>();
        for (InstructionDto instructionsdto : instructionsdtos) {
            instructions.add(transferInstructionDtoToInstruction(instructionsdto));
        }
        return instructions;
    }

}
