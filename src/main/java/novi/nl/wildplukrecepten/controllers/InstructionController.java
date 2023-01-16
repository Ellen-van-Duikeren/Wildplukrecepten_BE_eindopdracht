package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.dto.InstructionDto;
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

@RestController
public class InstructionController {

    private final InstructionService instructionService;

    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping("/instructions")
    public ResponseEntity<List<InstructionDto>> getAllInstructions() {
        return ResponseEntity.ok(instructionService.getAllInstructions());
    }

    @GetMapping("/instructions/{id}")
    public ResponseEntity<InstructionDto> getInstruction(@PathVariable Long id) {
        return ResponseEntity.ok(instructionService.getInstruction(id));
    }


    @PostMapping("/instructions")
    public ResponseEntity<String> createInstruction(@Valid @RequestBody InstructionDto instructionDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorString = getErrorString(br);
            return new ResponseEntity<>(errorString, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = instructionService.createInstruction(instructionDto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/instructions/" + createdId)
                    .toUriString());
            return ResponseEntity.created(uri).body("Instruction created.");
        }
    }

    @PutMapping("/instructions/{id}")
    public ResponseEntity<InstructionDto> updateInstruction(@PathVariable Long id, @Valid @RequestBody InstructionDto instructionDto) {
        InstructionDto instructionDto1 = instructionService.putInstruction(id, instructionDto);
        return ResponseEntity.ok().body(instructionDto1);
    }

    @PatchMapping("/instructions/{id}")
    public ResponseEntity<InstructionDto> updatePartOfInstruction(@PathVariable Long id, @Valid @RequestBody InstructionDto instructionDto) {
        InstructionDto instructionDto1 = instructionService.patchInstruction(id, instructionDto);
        return ResponseEntity.ok().body(instructionDto1);
    }

    @DeleteMapping("/instructions/{id}")
    public ResponseEntity<String> deleteInstruction(@PathVariable Long id) {
        instructionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

