package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.inputDto.UtensilInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.UtensilOutputDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.Utensil;
import novi.nl.wildplukrecepten.repositories.UtensilRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtensilService {
    private final UtensilRepository utensilRepository;

    public UtensilService(UtensilRepository utensilRepository) {
        this.utensilRepository = utensilRepository;
    }

    public List<UtensilOutputDto> getAllUtensils() {
        List<Utensil> utensils = utensilRepository.findAll();
        ArrayList<UtensilOutputDto> utensilOutputDtos = new ArrayList<>();
        for (Utensil utensil : utensils) {
            UtensilOutputDto utensilOutputDto = transferUtensilToDto(utensil);
            utensilOutputDtos.add(utensilOutputDto);
        }
        return utensilOutputDtos;
    }

    public UtensilOutputDto getUtensil(Long id) {
        Optional<Utensil> optionalUtensil = utensilRepository.findById(id);
        if (optionalUtensil.isPresent()) {
            Utensil utensil1 = optionalUtensil.get();
            return transferUtensilToDto(utensil1);
        } else {
            throw new RecordNotFoundException("No utensil found with id: " + id + ".");
        }
    }

    public Long createUtensil(UtensilInputDto utensilInputDto) {
        Utensil newUtensil = new Utensil();
        newUtensil = transferDtoToUtensil(utensilInputDto);
        Utensil savedUtensil = utensilRepository.save(newUtensil);
        return savedUtensil.getId();
    }

    public UtensilOutputDto putUtensil(Long id, UtensilInputDto utensilInputDto) {
        {
            if (utensilRepository.findById(id).isPresent()) {
                Utensil utensil = utensilRepository.findById(id).get();
                Utensil utensil1 = transferDtoToUtensil(utensilInputDto);
                utensil1.setId(utensil.getId());
                utensilRepository.save(utensil1);
                return transferUtensilToDto(utensil1);
            } else {
                throw new RecordNotFoundException("No utensil found with id: " + id + ".");
            }
        }
    }

    public UtensilOutputDto patchUtensil(Long id, UtensilInputDto utensilInputDto) {
        Optional<Utensil> optionalUtensil = utensilRepository.findById(id);
        if (utensilRepository.existsById(id)) {
            Utensil utensilToUpdate = optionalUtensil.get();

            if (utensilInputDto.getUtensil() != null) {
                utensilToUpdate.setUtensil(utensilInputDto.getUtensil());
            }

            Utensil savedUtensil = utensilRepository.save(utensilToUpdate);
            return transferUtensilToDto(savedUtensil);
        } else {
            throw new RecordNotFoundException("No utensil with id " + id);
        }
    }

    public String deleteById(Long id) {
        if (utensilRepository.existsById(id)) {
            Optional<Utensil> deletedUtensil = utensilRepository.findById(id);
            Utensil utensil1 = deletedUtensil.get();
            utensilRepository.delete(utensil1);
            return "Utensil with id: " + id + " deleted.";
        } else {
            throw new RecordNotFoundException("No utensil found with id: " + id + ".");
        }
    }


    //    helper methods.......................................................
    private UtensilOutputDto transferUtensilToDto(Utensil utensil) {
        UtensilOutputDto utensilOutputDto = new UtensilOutputDto();
        utensilOutputDto.setRecipe(utensil.getRecipe());
        utensilOutputDto.setId(utensil.getId());
        utensilOutputDto.setUtensil(utensil.getUtensil());
        return utensilOutputDto;
    }

    private Utensil transferDtoToUtensil(UtensilInputDto utensilInputDto) {
        Utensil utensil = new Utensil();
        utensil.setRecipe(utensilInputDto.getRecipe());
        utensil.setId(utensilInputDto.getId());
        utensil.setUtensil(utensilInputDto.getUtensil());
        return utensil;
    }
}
