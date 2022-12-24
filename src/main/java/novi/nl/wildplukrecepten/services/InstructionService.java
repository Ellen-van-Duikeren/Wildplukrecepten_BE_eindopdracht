package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.inputDto.InstructionInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.InstructionOutputDto;
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

        public List<InstructionOutputDto> getAllInstructions() {
            List<Instruction> instructions = instructionRepository.findAll();
            ArrayList<InstructionOutputDto> instructionOutputDtos = new ArrayList<>();
            for (Instruction instruction : instructions) {
                InstructionOutputDto instructionOutputDto = transferInstructionToDto(instruction);
                instructionOutputDtos.add(instructionOutputDto);
            }
            return instructionOutputDtos;
        }

        public InstructionOutputDto getInstruction(Long id) {
            Optional<Instruction> optionalInstruction = instructionRepository.findById(id);
            if (optionalInstruction.isPresent()) {
                Instruction instruction1 = optionalInstruction.get();
                return transferInstructionToDto(instruction1);
            } else {
                throw new RecordNotFoundException("No instruction found with id: " + id + ".");
            }
        }

        public Long createInstruction(InstructionInputDto instructionInputDto) {
            Instruction newInstruction = new Instruction();
            newInstruction = transferDtoToInstruction(instructionInputDto);
            Instruction savedInstruction = instructionRepository.save(newInstruction);
            return savedInstruction.getId();
        }

        public InstructionOutputDto putInstruction(Long id, InstructionInputDto instructionInputDto) {
            {
                if (instructionRepository.findById(id).isPresent()) {
                    Instruction instruction = instructionRepository.findById(id).get();
                    Instruction instruction1 = transferDtoToInstruction(instructionInputDto);
                    instruction1.setId(instruction.getId());
                    instructionRepository.save(instruction1);
                    return transferInstructionToDto(instruction1);
                } else {
                    throw new RecordNotFoundException("No instruction found with id: " + id + ".");
                }
            }
        }

        public InstructionOutputDto patchInstruction(Long id, InstructionInputDto instructionInputDto) {
            Optional<Instruction> optionalInstruction = instructionRepository.findById(id);
            if (instructionRepository.existsById(id)) {
                Instruction instructionToUpdate = optionalInstruction.get();

                if (instructionInputDto.getInstruction() != null) {
                    instructionToUpdate.setInstruction(instructionInputDto.getInstruction());
                }

                Instruction savedInstruction = instructionRepository.save(instructionToUpdate);
                return transferInstructionToDto(savedInstruction);
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
        private InstructionOutputDto transferInstructionToDto(Instruction instruction) {
            InstructionOutputDto instructionOutputDto = new InstructionOutputDto();
            instructionOutputDto.setRecipe(instruction.getRecipe());
            instructionOutputDto.setId(instruction.getId());
            instructionOutputDto.setInstruction(instruction.getInstruction());
            return instructionOutputDto;
        }

        private Instruction transferDtoToInstruction(InstructionInputDto instructionInputDto) {
            Instruction instruction = new Instruction();
            instruction.setRecipe(instructionInputDto.getRecipe());
            instruction.setId(instructionInputDto.getId());
            instruction.setInstruction(instructionInputDto.getInstruction());
            return instruction;
        }
    }
