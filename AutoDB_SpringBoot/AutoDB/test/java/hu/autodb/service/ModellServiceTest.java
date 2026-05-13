package hu.autodb.service;

import hu.autodb.exception.ResourceNotFoundException;
import hu.autodb.model.Marka;
import hu.autodb.model.Modell;
import hu.autodb.repository.ModellRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * ModellService unit tesztek.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ModellService tesztek")
class ModellServiceTest {

    @Mock
    private ModellRepository modellRepository;

    @Mock
    private MarkaService markaService;

    @InjectMocks
    private ModellService modellService;

    private Marka bmw;
    private Modell m3;

    @BeforeEach
    void setUp() {
        bmw = new Marka("BMW", "Németország", 1916);
        bmw.setId(1L);
        m3 = new Modell(bmw, "3-as sorozat", 2020, "Szedán", 1998);
        m3.setId(10L);
    }

    @Nested
    @DisplayName("getById")
    class GetByIdTest {

        @Test
        @DisplayName("Visszaadja a modellt ID alapján")
        void visszaadja_modellt() {
            when(modellRepository.findById(10L)).thenReturn(Optional.of(m3));

            Modell result = modellService.getById(10L);

            assertThat(result.getNev()).isEqualTo("3-as sorozat");
        }

        @Test
        @DisplayName("Kivételt dob, ha nem létezik")
        void kivetel_ha_nem_letezik() {
            when(modellRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> modellService.getById(99L))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("getByMarkaId")
    class GetByMarkaIdTest {

        @Test
        @DisplayName("Visszaadja a márka modelljeit")
        void visszaadja_marka_modelljeit() {
            when(markaService.getById(1L)).thenReturn(bmw);
            when(modellRepository.findByMarkaId(1L)).thenReturn(List.of(m3));

            List<Modell> result = modellService.getByMarkaId(1L);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getNev()).isEqualTo("3-as sorozat");
        }

        @Test
        @DisplayName("Nem létező márka esetén kivételt dob")
        void kivetel_nem_letezo_markara() {
            when(markaService.getById(99L))
                    .thenThrow(new ResourceNotFoundException("Márka nem található, id: 99"));

            assertThatThrownBy(() -> modellService.getByMarkaId(99L))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("create")
    class CreateTest {

        @Test
        @DisplayName("Létrehozza a modellt a megfelelő márkához")
        void letrehozza_modellt() {
            Modell uj = new Modell(null, "X5", 2022, "SUV", 2998);
            when(markaService.getById(1L)).thenReturn(bmw);
            when(modellRepository.save(any())).thenReturn(uj);

            Modell result = modellService.create(1L, uj);

            assertThat(uj.getMarka()).isEqualTo(bmw);
            verify(modellRepository).save(uj);
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateTest {

        @Test
        @DisplayName("Frissíti a modell adatait")
        void frissiti_az_adatokat() {
            Modell frissitett = new Modell(bmw, "3-as sorozat facelift", 2021, "Szedán", 1998);
            when(modellRepository.findById(10L)).thenReturn(Optional.of(m3));
            when(modellRepository.save(any())).thenReturn(m3);

            modellService.update(10L, frissitett);

            assertThat(m3.getNev()).isEqualTo("3-as sorozat facelift");
            assertThat(m3.getEv()).isEqualTo(2021);
            verify(modellRepository).save(m3);
        }

        @Test
        @DisplayName("Kivételt dob, ha a modell nem létezik")
        void kivetel_ha_nem_letezik() {
            when(modellRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> modellService.update(99L, m3))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("delete")
    class DeleteTest {

        @Test
        @DisplayName("Törli a modellt, ha létezik")
        void torli_ha_letezik() {
            when(modellRepository.existsById(10L)).thenReturn(true);

            modellService.delete(10L);

            verify(modellRepository).deleteById(10L);
        }

        @Test
        @DisplayName("Kivételt dob, ha nem létezik")
        void kivetel_ha_nem_letezik() {
            when(modellRepository.existsById(99L)).thenReturn(false);

            assertThatThrownBy(() -> modellService.delete(99L))
                    .isInstanceOf(ResourceNotFoundException.class);

            verify(modellRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("search")
    class SearchTest {

        @Test
        @DisplayName("Kategória alapján keres")
        void kategoria_kereses() {
            when(modellRepository.findByKategoriaIgnoreCase("SUV")).thenReturn(List.of(m3));

            List<Modell> result = modellService.getByKategoria("SUV");

            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("Névben keres")
        void nev_kereses() {
            when(modellRepository.findByNevContainingIgnoreCase("3-as")).thenReturn(List.of(m3));

            List<Modell> result = modellService.searchByNev("3-as");

            assertThat(result).hasSize(1);
        }
    }
}
