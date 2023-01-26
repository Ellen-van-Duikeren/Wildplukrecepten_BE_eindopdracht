package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dtos.UtensilDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.Utensil;
import novi.nl.wildplukrecepten.repositories.UtensilRepository;
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
class UtensilServiceTest {

    @Mock
    UtensilRepository utensilRepository;

    @InjectMocks
    UtensilService utensilService;

    @Captor
    ArgumentCaptor<Utensil> captor;

    Utensil utensil1;
    Utensil utensil2;
    UtensilDto utensilDto1;
    UtensilDto utensilDto2;


    @BeforeEach
    void setUp() {
        utensil1 = new Utensil(1L, "Utensil 1", null);
        utensil2 = new Utensil(2L, "Utensil 2", null);
        utensilDto1 = new UtensilDto(1L, "Utensil 1", null);
        utensilDto2 = new UtensilDto(2L, "Utensil 2", null);
    }


// testen..........................................................
    @Test
    void getAllUtensils() {
        //Arrange
        // Act
        when(utensilRepository.findAll()).thenReturn(List.of(utensil1, utensil2));

        List<Utensil> utensilsFound = utensilService.transferUtensilDtoListToUtensilList(utensilService.getAllUtensils());

        //Assert
        assertEquals(utensil1.getUtensil(), utensilsFound.get(0).getUtensil());
        assertEquals(utensil2.getUtensil(), utensilsFound.get(1).getUtensil());
    }


    @Test
    void getUtensil() {
        //Arrange
        //Act
        when(utensilRepository.findById(1L)).thenReturn(Optional.of(utensil1));
        UtensilDto utensilDto = utensilService.getUtensil(1L);

        //Assert
        assertEquals(utensil1.getId(), utensilDto.getId());
    }

    @Test
    void getUtensilThrowsExceptionForUtensilTest() {
        assertThrows(RecordNotFoundException.class, () -> utensilService.getUtensil(null));
    }


    @Test
    void createUtensil() {
        when(utensilRepository.save(utensil1)).thenReturn(utensil1);

        utensilService.createUtensil(utensilDto1);
        verify(utensilRepository, times(1)).save(captor.capture());
        Utensil captured = captor.getValue();

        assertEquals(utensil1.getId(), captured.getId());
    }

    @Test
    void putUtensil() {
        when(utensilRepository.findById(1L)).thenReturn(Optional.of(utensil1));
        when(utensilRepository.existsById(1L)).thenReturn(true);
        when(utensilRepository.save(any())).thenReturn(utensil2);

        utensilService.putUtensil(1L, utensilDto1);

        verify(utensilRepository, times(1)).save(captor.capture());
        Utensil captured = captor.getValue();

        assertEquals(utensil1.getId(), captured.getId());
        assertEquals(utensil1.getUtensil(), captured.getUtensil());
        assertEquals(utensil1.getRecipe(), captured.getRecipe());
    }

    @Test
    void putUtensilThrowsExceptionForUtensilTest() {
        assertThrows(RecordNotFoundException.class, () -> utensilService.putUtensil(1L, new UtensilDto(3L, "Doe 3", null)));
    }

    @Test
    void patchUtensil() {
        when(utensilRepository.findById(1L)).thenReturn(Optional.of(utensil1));
        when(utensilRepository.existsById(1L)).thenReturn(true);
        when(utensilRepository.save(any())).thenReturn(utensil2);

        utensilService.patchUtensil(1L, utensilDto1);

        verify(utensilRepository, times(1)).save(captor.capture());
        Utensil captured = captor.getValue();

        assertEquals(utensil1.getId(), captured.getId());
        assertEquals(utensil1.getUtensil(), captured.getUtensil());
        assertEquals(utensil1.getRecipe(), captured.getRecipe());
    }

    @Test
    void patchUtensilThrowsExceptionForUtensilTest() {
        assertThrows(RecordNotFoundException.class, () -> utensilService.patchUtensil(1L, new UtensilDto(3L, "Doe 3", null)));
    }

    @Test
    void deleteById() {
        when(utensilRepository.existsById(1L)).thenReturn(true);
        when(utensilRepository.findById(1L)).thenReturn(Optional.of(utensil1));
        utensilService.deleteById(1L);

        verify(utensilRepository).delete(utensil1);
    }

    @Test
    void deleteUtensilThrowsExceptionForUtensilTest() {
        assertThrows(RecordNotFoundException.class, () -> utensilService.deleteById(1L));
    }
}