package com.example.app.services;

import com.example.app.dtos.HotelDTO;
import com.example.app.dtos.HotelReservaDTO;
import com.example.app.entities.Hotel;
import com.example.app.entities.HotelReserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface HotelReservaServiceInterface {

    HotelReservaDTO crearReservaCliente(HotelReservaDTO hotelReservaDTO);

    Double calcularTotalPrecio(LocalDate fechaEntrada, LocalDate fechaSalida, String tipoHabitacion);

    Boolean estaDisponible(LocalDate fechaEntrada, LocalDate fechaSalida, String tipoHabitacion, String codigoHotel);

    Integer obtenerPrecioPorNoche(String tipoHabitacion);

    List<HotelReservaDTO> listarReservaHotel();

    HotelReservaDTO conversionADto(HotelReserva hotelReserva);

    HotelReserva conersionAEntidad(HotelReservaDTO hotelReservaDTO);
}
