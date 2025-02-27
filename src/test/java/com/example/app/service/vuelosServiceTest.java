package com.example.app.service;

import com.example.app.dtos.VueloDTO;
import com.example.app.entities.Vuelo;
import com.example.app.reposities.VueloRepositoryInterface;
import com.example.app.services.VueloServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class vuelosServiceTest {


    @Mock
    private VueloRepositoryInterface repository;

    @InjectMocks
    VueloServicio service;

    @Test
    @DisplayName("User History 4")
    void encontrarTodosLosVuelos() {

        List<Vuelo> VuelosMock = List.of(
                new Vuelo(1l, "Bc1244", "Malaga", "Madrid", "economico", 100.0,
                        "2025-03-03", "2025-03-09", new ArrayList<>()),
                new Vuelo(2l, "Bc1244", "Madrid", "Londres", "economico", 100.0,
                        "2025-03-03", "2025-03-09", new ArrayList<>())
        );

        when(repository.findAll()).thenReturn(VuelosMock);

        List<VueloDTO> vueloServicio = service.listarVuelos();

        assertThat(vueloServicio.get(0).getFlightId()).isEqualTo(1L);
        assertThat(vueloServicio.get(0).getFlightNumber()).isEqualTo("Bc1244");
        assertThat(vueloServicio.get(0).getOrigin()).isEqualTo("Malaga");
        assertThat(vueloServicio.get(0).getDestination()).isEqualTo("Madrid");
        assertThat(vueloServicio.get(0).getSeatType()).isEqualTo("economico");
        assertThat(vueloServicio.get(0).getDepartureDate()).isEqualTo("2025-03-03");
        assertThat(vueloServicio.get(0).getReturnDate()).isEqualTo("2025-03-09");
        assertThat(vueloServicio.get(0).getReservations()).size().isEqualTo(0);




    }
}



