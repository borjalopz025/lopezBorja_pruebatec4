package com.example.app.services;

import com.example.app.dtos.VueloDTO;
import com.example.app.dtos.VueloReservaDTO;
import com.example.app.entities.Cliente;
import com.example.app.entities.Vuelo;
import com.example.app.entities.VueloReserva;
import com.example.app.reposities.VueloRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VueloServicio implements VueloServicioInterface{

    @Autowired
    VueloRepositoryInterface repository;

    @Override
    public List<VueloDTO> listarVuelos() {
        List<Vuelo> vuelos = repository.findAll();
        if (vuelos.isEmpty()) {
            throw new RuntimeException("No se encontraron vuelos disponibles.");
        }
        return vuelos.stream().map(this::conversioDTO).toList();
    }

    @Override
    public VueloDTO buscarPorIdVuelo(Long id) {
        try{
            Optional<Vuelo> vuelo = repository.findById(id);
            return vuelo.map(this::conversioDTO).orElse(new VueloDTO(null,null,null,null,null,null,null,null,null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VueloDTO> filtrarVuelos (LocalDate fechaIda, LocalDate fechaVuelta, String origen, String destino) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<VueloDTO> vuelos = this.listarVuelos();

        return vuelos.stream()
                .filter(reserva -> destino == null || destino.isEmpty() || reserva.getDestination().equalsIgnoreCase(destino))
                .filter(reserva -> origen == null || origen.isEmpty() || reserva.getOrigin().equalsIgnoreCase(origen))

                .filter(reserva -> fechaIda == null ||
                        LocalDate.parse(reserva.getDepartureDate(), formatoFecha).isAfter(fechaIda) ||
                        LocalDate.parse(reserva.getDepartureDate(), formatoFecha).isEqual(fechaIda))

                .filter(reserva -> fechaVuelta == null ||
                        LocalDate.parse(reserva.getReturnDate(), formatoFecha).isBefore(fechaVuelta) ||
                        LocalDate.parse(reserva.getReturnDate(), formatoFecha).isEqual(fechaVuelta))
                .toList();
    }

    @Override
    public VueloDTO crearVuelo(VueloDTO vueloDTO) {
        Vuelo vuelo = this.conversioEntidad(vueloDTO);
        Vuelo guardarVuelo = repository.save(vuelo);
        return this.conversioDTO(guardarVuelo);
    }

    @Override
    public VueloDTO actualizarVuelo(Long id, VueloDTO vueloDTO) {
        Optional<Vuelo> encontrarVuelo = repository.findById(id);

        if (encontrarVuelo.isPresent()) {
            Vuelo vueloOptional = encontrarVuelo.get();
            vueloOptional.setNroVuelo(vueloDTO.getFlightNumber());
            vueloOptional.setOrigen(vueloDTO.getOrigin());
            vueloOptional.setDestino(vueloDTO.getDestination());
            vueloOptional.setPrecioPorPersona(vueloDTO.getPricePerPerson());
            vueloOptional.setFechaIda(vueloDTO.getDepartureDate());
            vueloOptional.setFechaVuelta(vueloDTO.getReturnDate());
            vueloOptional.setTipoAsiento(vueloDTO.getSeatType());

            Vuelo vueloActualizado = repository.save(vueloOptional);

            return this.conversioDTO(vueloActualizado);

        }else{
            throw new RuntimeException("vuelo no encontrado con el id: " + id);
        }

    }

    @Override
    public List<VueloDTO> eliminarVuelo(Long id) {
        try{
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.listarVuelos();
    }

    @Override
    public VueloDTO conversioDTO(Vuelo vuelo) {
        VueloReservaDTO vueloReservaDTO = new VueloReservaDTO();

        if ( vuelo.getReservas() == null){
            vueloReservaDTO = null;
            return null;
        } else {
            List<VueloReservaDTO> vueloReservaDTOS = vuelo.getReservas().stream()
                    .map( vuel -> new VueloReservaDTO(
                            vuel.getId_vueloReserva(),
                            vuel.getDate(),
                            vuel.getOrigin(),// date
                            vuel.getDestination(),
                            vuel.getFlightCode(),
                            vuel.getPeopleQ(),
                            vuel.getSeatType(),
                            vuel.getReservado(),
                            null, null,
                            vuel.getPrecioFinal()))
                    .toList();

            return new VueloDTO(
                    vuelo.getId_vuelo(),
                    vuelo.getNroVuelo(),
                    vuelo.getOrigen(),
                    vuelo.getDestino(),
                    vuelo.getTipoAsiento(),
                    vuelo.getPrecioPorPersona(),
                    vuelo.getFechaIda(),
                    vuelo.getFechaVuelta(),
                    vueloReservaDTOS
            );
        }



    }

    @Override
    public Vuelo conversioEntidad(VueloDTO vueloDTO) {
        List<VueloReserva> vueloReserva = vueloDTO.getReservations().stream()
                .map( vuelosRe -> new VueloReserva(
                        vuelosRe.getId_vueloReserva(),
                        vuelosRe.getDate(),
                        vuelosRe.getOrigin(),
                        vuelosRe.getDestination(),
                        vuelosRe.getFlightCode(),
                        vuelosRe.getPeopleQuantity(),
                        vuelosRe.getSeatType(),
                        vuelosRe.getIsBooked(),
                        new Cliente(),new Vuelo(),
                        vuelosRe.getPrecioFinal()
                ))
                .toList();

        return new Vuelo(
                null,
                vueloDTO.getFlightNumber(),
                vueloDTO.getOrigin(),
                vueloDTO.getDestination(),
                vueloDTO.getSeatType(),
                vueloDTO.getPricePerPerson(),
                vueloDTO.getDepartureDate(),
                vueloDTO.getReturnDate(),
                vueloReserva
        );
    }
}
