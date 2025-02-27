package com.example.app.services;

import com.example.app.dtos.VueloDTO;
import com.example.app.dtos.VueloReservaDTO;
import com.example.app.entities.Vuelo;
import com.example.app.entities.VueloReserva;

import java.time.LocalDate;
import java.util.List;

public interface VueloReservaServiceInterface {

    List<VueloReservaDTO> listarVuelosReserva();

    VueloReservaDTO crearVueloReservaCliente(VueloReservaDTO vueloReservaDTO);

    double calcularPrecioTotal(String origen, String destino, String fechaIda, int cantidadPersonas);

    Boolean estaDisponible(LocalDate fechaVuelo, String flightCode);

    VueloReservaDTO conversionDTO (VueloReserva vueloReserva);

    VueloReserva conversionEntidad(VueloReservaDTO vueloReservaDTO);
}
