package hu.autodb.service;

import hu.autodb.exception.ResourceNotFoundException;
import hu.autodb.model.Marka;
import hu.autodb.repository.MarkaRepository;
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
 * MarkaService unit tesztek.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MarkaService tesztek")
class MarkaServiceTest {

    @Mock
    private MarkaRepository markaRepository;

    @InjectMocks
    private MarkaService markaService;

    private Marka bmw;

    @BeforeEach
    void setUp() {
        bmw = new Marka("BMW", "Németország", 1916);
        bmw.setId(1L);
    }

    @Nested
    @DisplayName("getAll")
    class GetAllTest {

        @Test
        @DisplayName("Visszaadja az összes márkát")
        void visszaadja_az_osszes_markat() {
            when(markaRepository.findAll()).thenReturn(List.of(bmw));

            List<Marka> result = markaService.getAll();

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getNev()).isEqualTo("BMW");
        }

        @Test
        @DisplayName("Üres listát ad, ha nincs adat")
        void ures_lista_ha_nincs_adat() {
            when(markaRepository.findAll()).thenReturn(List.of());

            assertThat(markaService.getAll()).isEmpty();
        }
    }

    @Nested
    @DisplayName("getById")
    class GetByIdTest {

        @Test
        @DisplayName("Megtalálja a márkát ID alapján")
        void megtalal_id_alapjan() {
            when(markaRepository.findById(1L)).thenReturn(Optional.of(bmw));

            Marka result = markaService.getById(1L);

            assertThat(result.getNev()).isEqualTo("BMW");
        }

        @Test
        @DisplayName("ResourceNotFoundException-t dob, ha nem létezik")
        void kivetel_ha_nem_letezik() {
            when(markaRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> markaService.getById(99L))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("99");
        }
    }

    @Nested
    @DisplayName("create")
    class CreateTest {

        @Test
        @DisplayName("Új márkát ment el")
        void ment_uj_markat() {
            when(markaRepository.existsByNev("BMW")).thenReturn(false);
            when(markaRepository.save(bmw)).thenReturn(bmw);

            Marka result = markaService.create(bmw);

            assertThat(result.getNev()).isEqualTo("BMW");
            verify(markaRepository).save(bmw);
        }

        @Test
        @DisplayName("Duplikált névnél kivételt dob")
        void duplikat_nevnel_kivetel() {
            when(markaRepository.existsByNev("BMW")).thenReturn(true);

            assertThatThrownBy(() -> markaService.create(bmw))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("BMW");

            verify(markaRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateTest {

        @Test
        @DisplayName("Frissíti a marka adatait")
        void frissiti_az_adatokat() {
            Marka frissitett = new Marka("BMW AG", "Németország", 1916);
            when(markaRepository.findById(1L)).thenReturn(Optional.of(bmw));
            when(markaRepository.existsByNev("BMW AG")).thenReturn(false);
            when(markaRepository.save(any())).thenReturn(bmw);

            Marka result = markaService.update(1L, frissitett);

            verify(markaRepository).save(bmw);
            assertThat(bmw.getNev()).isEqualTo("BMW AG");
        }

        @Test
        @DisplayName("Nem létező ID esetén kivételt dob")
        void nem_letezo_id_kivetel() {
            when(markaRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> markaService.update(99L, bmw))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("delete")
    class DeleteTest {

        @Test
        @DisplayName("Törli a márkát, ha létezik")
        void torli_ha_letezik() {
            when(markaRepository.existsById(1L)).thenReturn(true);

            markaService.delete(1L);

            verify(markaRepository).deleteById(1L);
        }

        @Test
        @DisplayName("Kivételt dob, ha nem létezik")
        void kivetel_ha_nem_letezik() {
            when(markaRepository.existsById(99L)).thenReturn(false);

            assertThatThrownBy(() -> markaService.delete(99L))
                    .isInstanceOf(ResourceNotFoundException.class);

            verify(markaRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("search")
    class SearchTest {

        @Test
        @DisplayName("Névkeresésnél a repository-t hívja")
        void nevkereses_delegalja_repository() {
            when(markaRepository.findByNevContainingIgnoreCase("bm")).thenReturn(List.of(bmw));

            List<Marka> result = markaService.searchByNev("bm");

            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("Országkeresésnél a repository-t hívja")
        void orszagkereses_delegalja_repository() {
            when(markaRepository.findByOrszagContainingIgnoreCase("Német")).thenReturn(List.of(bmw));

            List<Marka> result = markaService.searchByOrszag("Német");

            assertThat(result).hasSize(1);
        }
    }
}
