package com.example.app.services;

import com.example.app.dtos.*;
import com.example.app.entities.*;
import com.example.app.reposities.VueloRepositoryInterface;
import com.example.app.reposities.VueloReservaRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class VueloReservaService implements VueloReservaServiceInterface{

    @Autowired
    VueloReservaRepositoryInterface repository;

    @Autowired
    VueloRepositoryInterface repoVuelo;

    @Override
    public List<VueloReservaDTO> listarVuelosReserva() {
        List<VueloReserva> reservas = repository.findAll();
        if (reservas.isEmpty()) {
            throw new RuntimeException("No se encontraron reservas de vuelos.");
        }
        return reservas.stream().map(this::conversionDTO).toList();
    }


    @Override
    public VueloReservaDTO crearVueloReservaCliente(VueloReservaDTO vueloReservaDTO) {
        if (vueloReservaDTO.getDate() == null || vueloReservaDTO.getOrigin() == null || vueloReservaDTO.getDestination() == null) {
            throw new IllegalArgumentException("La fecha, el origen y el destino son obligatorios.");
        }

        LocalDate fechaVuelo = LocalDate.parse(vueloReservaDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Verificar disponibilidad del vuelo
        boolean disponible = this.estaDisponible(fechaVuelo, vueloReservaDTO.getFlightCode());

        if (!disponible) {
            throw new IllegalStateException("No hay disponibilidad para el vuelo seleccionado.");
        }

        // Calcular precio total
        double montoTotal = this.calcularPrecioTotal(
                vueloReservaDTO.getOrigin(),
                vueloReservaDTO.getDestination(),
                vueloReservaDTO.getDate(),
                vueloReservaDTO.getPeopleQuantity()
        );

        vueloReservaDTO.setIsBooked(true);
        vueloReservaDTO.setPrecioFinal(montoTotal);
        // Convertir DTO a entidad y guardar en la base de datos
        VueloReserva vueloReserva = this.conversionEntidad(vueloReservaDTO);
        vueloReserva = repository.save(vueloReserva);

        // Convertir de nuevo a DTO para retornar la respuesta
        VueloReservaDTO reservaRealizadaDTO = this.conversionDTO(vueloReserva);
        reservaRealizadaDTO.setIsBooked(true);
        reservaRealizadaDTO.setPeopleQuantity(vueloReservaDTO.getPeopleQuantity());

        return reservaRealizadaDTO;
    }

    @Override
    public double calcularPrecioTotal(String origen, String destino, String fechaIda, int cantidadPersonas) {
        List<Vuelo> vuelos = repoVuelo.findAll();

        Vuelo vueloEncontrado = vuelos.stream()
                .filter(v -> v.getOrigen().equalsIgnoreCase(origen))
                .filter(v -> v.getDestino().equalsIgnoreCase(destino))
                .filter(v -> v.getFechaIda().equals(fechaIda))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un vuelo con los datos proporcionados"));

        return vueloEncontrado.getPrecioPorPersona() * cantidadPersonas;
    }


    @Override
    public Boolean estaDisponible(LocalDate fechaVuelo, String flightCode) {
        List<Vuelo> vuelosDisponibles = repoVuelo.findAll(); // Obtener todos los vuelos

        return vuelosDisponibles.stream()
                .anyMatch(vuelo -> vuelo.getNroVuelo().equalsIgnoreCase(flightCode) &&
                        LocalDate.parse(vuelo.getFechaIda(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                .equals(fechaVuelo));
    }

    @Override
    public VueloReservaDTO conversionDTO(VueloReserva vueloReserva) {

        if (vueloReserva == null) {
            throw new IllegalArgumentException("La reserva del vuelo no puede ser nula.");
        }

        // Crear DTOs vacíos para evitar errores
        ClienteDTO clienteDTO = new ClienteDTO();
        VueloDTO vueloDTO = new VueloDTO();

        // Verificar si hay un cliente asociado
        if (vueloReserva.getClientes() != null) {
            Cliente cliente = vueloReserva.getClientes();
            clienteDTO = new ClienteDTO(
                    cliente.getId_cliente(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    null, null
            );
        }

        // Verificar si hay un hotel asociado
        if (vueloReserva.getVuelo() != null) {
            Vuelo vuelo = vueloReserva.getVuelo();
            vueloDTO = new VueloDTO(
                    vuelo.getId_vuelo(),
                    vuelo.getNroVuelo(),
                    vuelo.getOrigen(),
                    vuelo.getDestino(),
                    vuelo.getTipoAsiento(),
                    vuelo.getPrecioPorPersona(),
                    vuelo.getFechaIda(),
                    vuelo.getFechaVuelta(),
                    null
            );
        }

        // Retornar DTO con datos ya validados
        return new VueloReservaDTO(
                vueloReserva.getId_vueloReserva(),
                vueloReserva.getDate(),
                vueloReserva.getOrigin(),
                vueloReserva.getDestination(),
                vueloReserva.getVuelo().getNroVuelo(),
                vueloReserva.getPeopleQ(),
                vueloReserva.getSeatType(),
                vueloReserva.getReservado(),
                clienteDTO,
                vueloDTO,
                vueloReserva.getPrecioFinal()
        );
    }

    @Override
    public VueloReserva conversionEntidad(VueloReservaDTO vueloReservaDTO) {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(vueloReservaDTO.getPasajeros().getIdentificadorCliente());

        Vuelo vuelo = new Vuelo();
        vuelo.setId_vuelo(vueloReservaDTO.getVuelos().getFlightId());

        return new VueloReserva(
                null,
                vueloReservaDTO.getDate(),
                vueloReservaDTO.getOrigin(),
                vueloReservaDTO.getDestination(),
                vueloReservaDTO.getFlightCode(),
                vueloReservaDTO.getPeopleQuantity(),
                vueloReservaDTO.getSeatType(),
                vueloReservaDTO.getIsBooked(),
                cliente, vuelo,
                vueloReservaDTO.getPrecioFinal()
        );
    }
}
