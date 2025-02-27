package com.example.app.services;

import com.example.app.dtos.ClienteDTO;
import com.example.app.dtos.HotelDTO;
import com.example.app.dtos.HotelReservaDTO;
import com.example.app.entities.Cliente;
import com.example.app.entities.Hotel;
import com.example.app.entities.HotelReserva;
import com.example.app.reposities.HotelRepositoryInterface;
import com.example.app.reposities.HotelReservaRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HotelReservaService implements HotelReservaServiceInterface{

    @Autowired
    HotelReservaRepositoryInterface repository;

    @Autowired
    HotelServiceInterface serviceHotel;


    @Override
    public HotelReservaDTO crearReservaCliente(HotelReservaDTO hotelReservaDTO) {

        if (hotelReservaDTO.getDateFrom() == null || hotelReservaDTO.getDateTo() == null) {
            throw new IllegalArgumentException("Las fechas de entrada y salida son obligatorias.");
        }

        LocalDate fechaEntrada = LocalDate.parse(hotelReservaDTO.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate fechaSalida = LocalDate.parse(hotelReservaDTO.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (!fechaSalida.isAfter(fechaEntrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }

        if (!this.estaDisponible(fechaEntrada, fechaSalida, hotelReservaDTO.getRoomType(), hotelReservaDTO.getHotelCode())) {
            throw new IllegalStateException("El hotel no está disponible.");
        }

        // Calcular el precio total
        Double montoTotal = this.calcularTotalPrecio(fechaEntrada, fechaSalida, hotelReservaDTO.getRoomType());
        hotelReservaDTO.setPrecioFinal(montoTotal);
        hotelReservaDTO.setReservado(true);

        // Convertir a entidad y guardar en la base de datos
        HotelReserva hotelReserva = this.conersionAEntidad(hotelReservaDTO);
        HotelReserva guaradarReservaHotel = repository.save(hotelReserva);

        return this.conversionADto(guaradarReservaHotel);

    }
    @Override
    public Double calcularTotalPrecio(LocalDate fechaEntrada, LocalDate fechaSalida, String tipoHabitacion) {
        if (fechaEntrada == null || fechaSalida == null || !fechaSalida.isAfter(fechaEntrada)) {
            throw new IllegalArgumentException("Las fechas son inválidas.");
        }
//      metodo encontrado por internet para calcular los dias que hay entre dos fechas
        long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        Double precioPorNoche = obtenerPrecioPorNoche(tipoHabitacion).doubleValue();

        return precioPorNoche * noches;
    }

    @Override
    public Boolean estaDisponible( LocalDate fechaEntrada, LocalDate fechaSalida, String tipoHabitacion, String codigoHotel) {
        if (fechaEntrada.isAfter(fechaSalida)) {
            throw new IllegalArgumentException("La fecha de entrada no puede ser después de la fecha de salida.");
        }

        List<HotelDTO> hoteles = serviceHotel.listarHoteles();

        return hoteles.stream()
                .filter(hotel -> hotel.getTipoHabitacion() != null && hotel.getTipoHabitacion().equalsIgnoreCase(tipoHabitacion))
                .filter(hotel -> hotel.getDisponibleDesde() != null && hotel.getDisponibleHasta() != null)
                .filter(hotel -> {
                    LocalDate disponibleDesde = LocalDate.parse(hotel.getDisponibleDesde(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate disponibleHasta = LocalDate.parse(hotel.getDisponibleHasta(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return !fechaEntrada.isBefore(disponibleDesde) && !fechaSalida.isAfter(disponibleHasta);
                })
                .noneMatch(hotel -> Boolean.TRUE.equals(hotel.getReservado()));
    }


    @Override
    public Integer obtenerPrecioPorNoche(String reservaHabitacion) {
        if (reservaHabitacion == null || reservaHabitacion.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de habitación no puede estar vacío.");
        }

        if (reservaHabitacion.equalsIgnoreCase("individual")) {
            return 80;
        } else if (reservaHabitacion.equalsIgnoreCase("doble")) {
            return 120;
        } else if (reservaHabitacion.equalsIgnoreCase("suite")) {
            return 200;
        } else {
            throw new IllegalArgumentException("Tipo de habitación no reconocido: " + reservaHabitacion);
        }
    }





    @Override
    public List<HotelReservaDTO> listarReservaHotel() {
        List<HotelReserva> listadoReservas = repository.findAll();
        if (listadoReservas.isEmpty()) {
            throw new RuntimeException("No se encontraron reservas.");
        }
        return listadoReservas.stream().map(this::conversionADto).toList();
    }

    @Override
    public HotelReservaDTO conversionADto(HotelReserva hotelReserva) {
        if (hotelReserva == null) {
            throw new IllegalArgumentException("La reserva del hotel no puede ser nula.");
        }

        // Crear DTOs vacíos para evitar errores
        ClienteDTO clienteDTO = new ClienteDTO();
        HotelDTO hotelDTO = new HotelDTO();

        // Verificar si hay un cliente asociado
        if (hotelReserva.getClientes() != null) {
            Cliente cliente = hotelReserva.getClientes();
            clienteDTO = new ClienteDTO(
                    cliente.getId_cliente(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    null, null
            );
        }

        // Verificar si hay un hotel asociado
        if (hotelReserva.getHotel() != null) {
            Hotel hotel = hotelReserva.getHotel();
            hotelDTO = new HotelDTO(
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
                    null
            );
        }

        // Retornar DTO con datos ya validados
        return new HotelReservaDTO(
                hotelReserva.getId_hotelReserva(),
                hotelReserva.getDateFrom(),
                hotelReserva.getDateTo(),
                hotelReserva.getNights(),
                hotelReserva.getPlace(),
                hotelReserva.getHotelCode(),
                hotelReserva.getPeopleQ(),
                hotelReserva.getRoomType(),
                hotelReserva.getReservado(),
                clienteDTO,
                hotelDTO,
                hotelReserva.getPrecioFinal()
        );
    }

    @Override
    public HotelReserva conersionAEntidad(HotelReservaDTO hotelReservaDTO) {

        Cliente cliente = new Cliente();
        cliente.setId_cliente(hotelReservaDTO.getClientes().getIdentificadorCliente());

        Hotel hotel = new Hotel();
        hotel.setId_hotel(hotelReservaDTO.getReservas().getIdentificador());

        return new HotelReserva(
                null,
                hotelReservaDTO.getDateFrom(),
                hotelReservaDTO.getDateTo(),
                hotelReservaDTO.getNights(),
                hotelReservaDTO.getPlace(),
                hotelReservaDTO.getHotelCode(),
                hotelReservaDTO.getPeopleQuantity(),
                hotelReservaDTO.getRoomType(),
                hotelReservaDTO.getReservado(),
                cliente, hotel,
                hotelReservaDTO.getPrecioFinal()
        );

    }
}
