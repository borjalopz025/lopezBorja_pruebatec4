package com.example.app.services;

import com.example.app.dtos.HotelDTO;
import com.example.app.entities.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface HotelServiceInterface {

    List<HotelDTO> listarHoteles();

    List<HotelDTO> filtrarFechaDestino(LocalDate fechaIncio, LocalDate fechaFin, String destino);

    HotelDTO encontrarHotelPorId(Long id);

    HotelDTO crearHoteles(HotelDTO hotelDTO);

    HotelDTO actualizarHotel(Long id, HotelDTO hotelDTO);

    List<HotelDTO> eliminarHotel(Long id);

    HotelDTO conversionADto(Hotel hotel);

    Hotel conversionAEntidad(HotelDTO hotelDTO);
}
