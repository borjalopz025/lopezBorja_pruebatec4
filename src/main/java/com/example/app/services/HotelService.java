package com.example.app.services;

import com.example.app.dtos.ClienteDTO;
import com.example.app.dtos.HotelDTO;
import com.example.app.dtos.HotelReservaDTO;
import com.example.app.dtos.VueloDTO;
import com.example.app.entities.Cliente;
import com.example.app.entities.Hotel;
import com.example.app.entities.HotelReserva;
import com.example.app.entities.Vuelo;
import com.example.app.reposities.HotelRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements HotelServiceInterface{

    @Autowired
    HotelRepositoryInterface repository;

    @Override
    public List<HotelDTO> listarHoteles() {
        List<Hotel> hoteles = repository.findAll();
        if (hoteles.isEmpty()) {
            throw new RuntimeException("No se encontraron hoteles disponibles.");
        }
        return hoteles.stream().map(this::conversionADto).toList();
    }

    @Override
    public List<HotelDTO> filtrarFechaDestino(LocalDate fechaInicio, LocalDate fechaFin, String destino) {
        // formato de las fechas que recibimos como String
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<HotelDTO> hotelesFiltrados = this.listarHoteles().stream()
                .filter(reserva -> destino == null || destino.isEmpty() || reserva.getLugar().equalsIgnoreCase(destino))
                .filter(reserva -> fechaInicio == null ||
                        LocalDate.parse(reserva.getDisponibleDesde(), formatoFecha).isAfter(fechaInicio) ||
                        LocalDate.parse(reserva.getDisponibleDesde(), formatoFecha).isEqual(fechaInicio))

                .filter(reserva -> fechaFin == null ||
                        LocalDate.parse(reserva.getDisponibleHasta(), formatoFecha).isBefore(fechaInicio) ||
                        LocalDate.parse(reserva.getDisponibleHasta(), formatoFecha).isEqual(fechaFin))
                .toList();

        if (hotelesFiltrados.isEmpty()) {
            throw new RuntimeException("No se encontraron hoteles para los criterios especificados.");
        }

        return hotelesFiltrados;

    }

    @Override
    public HotelDTO encontrarHotelPorId(Long id) {
        try{
            Optional<Hotel> hotel = repository.findById(id);
            return hotel.map(this::conversionADto).orElse(new HotelDTO(null,null,null,null,null,null,null,null,null, null, null));
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido encontra el id "+ e.getMessage());
        }
    }

    @Override
    public HotelDTO crearHoteles(HotelDTO hotelDTO) {
        Hotel hotel = this.conversionAEntidad(hotelDTO);
        Hotel crearHotel = repository.save(hotel);
        return this.conversionADto(crearHotel);
    }

    @Override
    public HotelDTO actualizarHotel(Long id, HotelDTO hotelDTO) {
        Optional<Hotel> encontrarHotel = repository.findById(id);

        if (encontrarHotel.isPresent()) {
            Hotel hotelOptional = encontrarHotel.get();
            hotelOptional.setLugar(hotelDTO.getLugar());
            hotelOptional.setNombre(hotelDTO.getNombre());
            hotelOptional.setTipoHabitacion(hotelDTO.getTipoHabitacion());
            hotelOptional.setNumeroNoches(hotelDTO.getNumeroNoches());
            hotelOptional.setDisponibleDesde(hotelDTO.getDisponibleDesde());
            hotelOptional.setDisponibleHasta(hotelDTO.getDisponibleHasta());
            hotelOptional.setNumeroPersonas(hotelDTO.getNumeroPersonas());
            hotelOptional.setNumeroNoches(hotelDTO.getNumeroNoches());
            hotelOptional.setPrecioPorNoche(hotelDTO.getPrecioPorNoche());


            Hotel hotelActualizado = repository.save(hotelOptional);

            return this.conversionADto(hotelActualizado);

        }else{
            throw new RuntimeException("hotel no encontrado con el id: " + id);
        }
    }

    @Override
    public List<HotelDTO> eliminarHotel(Long id) {
        try{
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.listarHoteles();
    }


    @Override
    public HotelDTO conversionADto(Hotel hotel) {
        if (hotel.getReservasHotel() == null) {
            throw  new RuntimeException("Error: la entidad hotel es null.");
        }

        List<HotelReservaDTO> hotelReservaDTOS = hotel.getReservasHotel().stream()
                .map(hotelRe -> new HotelReservaDTO(
                        hotelRe.getId_hotelReserva(),
                        hotelRe.getDateFrom(),
                        hotelRe.getDateTo(),
                        hotelRe.getNights(),
                        hotelRe.getPlace(),
                        hotelRe.getHotelCode(),
                        hotelRe.getPeopleQ(),
                        hotelRe.getRoomType(),
                        hotelRe.getReservado(),
                        null, null,
                        hotelRe.getPrecioFinal()))
                .toList();


        return new HotelDTO(
                hotel.getId_hotel(),
                hotel.getNombre(),
                hotel.getLugar(),
                hotel.getNumeroNoches(),
                hotel.getTipoHabitacion(),
                hotel.getPrecioPorNoche(),
                hotel.getNumeroPersonas(),
                hotel.getDisponibleDesde(),
                hotel.getDisponibleHasta(),
                hotel.isReservado(),
                hotelReservaDTOS
        );
    }


    @Override
    public Hotel conversionAEntidad(HotelDTO hotelDTO) {
        List<HotelReserva> hotelReserva = hotelDTO.getReservas().stream()
                .map( hotelRe -> new HotelReserva(
                        hotelRe.getIdentificador(),
                        hotelRe.getDateFrom(),
                        hotelRe.getDateTo(),
                        hotelRe.getNights(),
                        hotelRe.getPlace(),
                        hotelRe.getHotelCode(),
                        hotelRe.getPeopleQuantity(),
                        hotelRe.getRoomType(),
                        hotelRe.getReservado(),
                        new Cliente(),
                        new Hotel(),
                        hotelRe.getPrecioFinal()
                ))
                .toList();

        return new Hotel(
                null, null,
                hotelDTO.getNombre(),
                hotelDTO.getLugar(),
                hotelDTO.getNumeroNoches(),
                hotelDTO.getTipoHabitacion(),
                hotelDTO.getPrecioPorNoche(),
                hotelDTO.getNumeroPersonas(),
                hotelDTO.getDisponibleDesde(),
                hotelDTO.getDisponibleHasta(),
                hotelDTO.getReservado(),
                hotelReserva
        );
    }



}
