package com.example.app.services;

import com.example.app.dtos.VueloDTO;
import com.example.app.entities.Vuelo;

import java.time.LocalDate;
import java.util.List;

public interface VueloServicioInterface {

    List<VueloDTO> listarVuelos();

    VueloDTO buscarPorIdVuelo (Long id);

    List<VueloDTO> filtrarVuelos(LocalDate fechaIda, LocalDate fechaVuelta, String origen, String destino);

    VueloDTO crearVuelo(VueloDTO vueloDTO);

    VueloDTO actualizarVuelo(Long id, VueloDTO vueloDTO);

    List<VueloDTO> eliminarVuelo(Long id);

    VueloDTO conversioDTO (Vuelo vuelo);

    Vuelo conversioEntidad(VueloDTO vueloDTO);
}
