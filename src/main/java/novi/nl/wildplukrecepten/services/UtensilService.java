package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.UtensilDto;
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

    public List<UtensilDto> getAllUtensils() {
        List<Utensil> utensils = utensilRepository.findAll();
        ArrayList<UtensilDto> utensilDtos = new ArrayList<>();
        for (Utensil utensil : utensils) {
            UtensilDto utensilDto = transferUtensilToUtensilDto(utensil);
            utensilDtos.add(utensilDto);
        }
        return utensilDtos;
    }

    public UtensilDto getUtensil(Long id) {
        Optional<Utensil> optionalUtensil = utensilRepository.findById(id);
        if (optionalUtensil.isPresent()) {
            Utensil utensil1 = optionalUtensil.get();
            return transferUtensilToUtensilDto(utensil1);
        } else {
            throw new RecordNotFoundException("No utensil found with id: " + id + ".");
        }
    }

    public Long createUtensil(UtensilDto utensilDto) {
        Utensil newUtensil;
        newUtensil = transferUtensilDtoToUtensil(utensilDto);
        Utensil savedUtensil = utensilRepository.save(newUtensil);
        return savedUtensil.getId();
    }

    public UtensilDto putUtensil(Long id, UtensilDto utensilDto) {
        {
            if (utensilRepository.findById(id).isPresent()) {
                Utensil utensil = utensilRepository.findById(id).get();
                Utensil utensil1 = transferUtensilDtoToUtensil(utensilDto);
                utensil1.setId(utensil.getId());
                utensilRepository.save(utensil1);
                return transferUtensilToUtensilDto(utensil1);
            } else {
                throw new RecordNotFoundException("No utensil found with id: " + id + ".");
            }
        }
    }

    public UtensilDto patchUtensil(Long id, UtensilDto utensilDto) {
        Optional<Utensil> optionalUtensil = utensilRepository.findById(id);
        if (utensilRepository.existsById(id)) {
            Utensil utensilToUpdate = optionalUtensil.get();

            if (utensilDto.getUtensil() != null) {
                utensilToUpdate.setUtensil(utensilDto.getUtensil());
            }

            Utensil savedUtensil = utensilRepository.save(utensilToUpdate);
            return transferUtensilToUtensilDto(savedUtensil);
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
    private UtensilDto transferUtensilToUtensilDto(Utensil utensil) {
        UtensilDto utensilDto = new UtensilDto();
        utensilDto.setRecipe(utensil.getRecipe());
        utensilDto.setId(utensil.getId());
        utensilDto.setUtensil(utensil.getUtensil());
        return utensilDto;
    }

    private Utensil transferUtensilDtoToUtensil(UtensilDto utensilDto) {
        Utensil utensil = new Utensil();
        utensil.setRecipe(utensilDto.getRecipe());
        utensil.setId(utensilDto.getId());
        utensil.setUtensil(utensilDto.getUtensil());
        return utensil;
    }

    public List<Utensil> transferUtensilDtoListToUtensilList(List<UtensilDto> utensilstos) {
        List<Utensil> utensils = new ArrayList<>();
        for (UtensilDto utensilsto : utensilstos) {
            utensils.add(transferUtensilDtoToUtensil(utensilsto));
        }
        return utensils;
    }
}
