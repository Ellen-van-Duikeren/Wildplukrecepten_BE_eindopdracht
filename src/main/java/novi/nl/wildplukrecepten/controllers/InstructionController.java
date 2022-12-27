package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.inputDto.InstructionInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.InstructionOutputDto;
import novi.nl.wildplukrecepten.services.InstructionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static novi.nl.wildplukrecepten.utilities.Utilities.getErrorString;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class InstructionController {

    private final InstructionService instructionService;

    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping("/instructions")
    public ResponseEntity<List<InstructionOutputDto>> getAllInstructions() {
        return ResponseEntity.ok(instructionService.getAllInstructions());
    }

    @GetMapping("/instructions/{id}")
    public ResponseEntity<InstructionOutputDto> getInstruction(@PathVariable Long id) {
        return ResponseEntity.ok(instructionService.getInstruction(id));
    }

    @PostMapping("/instructions")
    public ResponseEntity<String> createInstruction(@Valid @RequestBody InstructionInputDto instructionInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorString = getErrorString(br);
            return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = instructionService.createInstruction(instructionInputDto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/instructions/" + createdId)
                    .toUriString());
            return ResponseEntity.created(uri).body("Instruction created.");
        }
    }

    @PutMapping("/instructions/{id}")
    public ResponseEntity<InstructionOutputDto> updateInstruction(@PathVariable Long id, @Valid @RequestBody InstructionInputDto instructionInputDto) {
        InstructionOutputDto instructionDto = instructionService.putInstruction(id, instructionInputDto);
        return ResponseEntity.ok().body(instructionDto);
    }

    @PatchMapping("/instructions/{id}")
    public ResponseEntity<InstructionOutputDto> updatePartOfInstruction(@PathVariable Long id, @Valid @RequestBody InstructionInputDto instructionInputDto) {
        InstructionOutputDto instructionDto = instructionService.patchInstruction(id, instructionInputDto);
        return ResponseEntity.ok().body(instructionDto);
    }

    @DeleteMapping("/instructions/{id}")
    public ResponseEntity<String> deleteInstruction(@PathVariable Long id) {
        instructionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

