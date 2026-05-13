package hu.autodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.autodb.exception.ResourceNotFoundException;
import hu.autodb.model.Marka;
import hu.autodb.service.MarkaService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MarkaController MockMvc tesztek.
 */
@WebMvcTest(MarkaController.class)
@DisplayName("MarkaController tesztek")
class MarkaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarkaService markaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Marka bmw;

    @BeforeEach
    void setUp() {
        bmw = new Marka("BMW", "Németország", 1916);
        bmw.setId(1L);
    }

    @Nested
    @DisplayName("GET /api/markak")
    class GetAllTest {

        @Test
        @DisplayName("200 OK és visszaadja a márkák listáját")
        void visszaadja_a_markak_listajat() throws Exception {
            when(markaService.getAll()).thenReturn(List.of(bmw));

            mockMvc.perform(get("/api/markak"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nev").value("BMW"))
                    .andExpect(jsonPath("$[0].orszag").value("Németország"));
        }
    }

    @Nested
    @DisplayName("GET /api/markak/{id}")
    class GetByIdTest {

        @Test
        @DisplayName("200 OK visszaad egy márkát")
        void visszaad_egy_markat() throws Exception {
            when(markaService.getById(1L)).thenReturn(bmw);

            mockMvc.perform(get("/api/markak/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nev").value("BMW"));
        }

        @Test
        @DisplayName("404 ha nem létezik")
        void negyvenegy_ha_nem_letezik() throws Exception {
            when(markaService.getById(99L))
                    .thenThrow(new ResourceNotFoundException("Márka nem található, id: 99"));

            mockMvc.perform(get("/api/markak/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /api/markak")
    class CreateTest {

        @Test
        @DisplayName("201 Created létrehoz egy márkát")
        void letrehoz_egy_markat() throws Exception {
            when(markaService.create(any())).thenReturn(bmw);

            mockMvc.perform(post("/api/markak")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(bmw)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nev").value("BMW"));
        }

        @Test
        @DisplayName("400 ha hiányzik a kötelező mező")
        void negyvenes_ha_hianyzik_mezo() throws Exception {
            Marka invalid = new Marka("", "Németország", 1916);

            mockMvc.perform(post("/api/markak")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalid)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("PUT /api/markak/{id}")
    class UpdateTest {

        @Test
        @DisplayName("200 OK frissíti a márkát")
        void frissiti_a_markat() throws Exception {
            when(markaService.update(eq(1L), any())).thenReturn(bmw);

            mockMvc.perform(put("/api/markak/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(bmw)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nev").value("BMW"));
        }
    }

    @Nested
    @DisplayName("DELETE /api/markak/{id}")
    class DeleteTest {

        @Test
        @DisplayName("204 No Content sikeres törlés esetén")
        void sikeres_torles() throws Exception {
            mockMvc.perform(delete("/api/markak/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("404 ha nem létezik")
        void negyvenegy_ha_nem_letezik() throws Exception {
            doThrow(new ResourceNotFoundException("Márka nem található, id: 99"))
                    .when(markaService).delete(99L);

            mockMvc.perform(delete("/api/markak/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET /api/markak/kereses")
    class SearchTest {

        @Test
        @DisplayName("Névkeresés visszaad találatokat")
        void nevkereses_talalatok() throws Exception {
            when(markaService.searchByNev("bm")).thenReturn(List.of(bmw));

            mockMvc.perform(get("/api/markak/kereses/nev").param("q", "bm"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nev").value("BMW"));
        }

        @Test
        @DisplayName("Országkeresés visszaad találatokat")
        void orszagkereses_talalatok() throws Exception {
            when(markaService.searchByOrszag("Német")).thenReturn(List.of(bmw));

            mockMvc.perform(get("/api/markak/kereses/orszag").param("q", "Német"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].orszag").value("Németország"));
        }
    }
}
