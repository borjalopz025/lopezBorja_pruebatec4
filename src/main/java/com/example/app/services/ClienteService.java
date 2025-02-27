package com.example.app.services;

import com.example.app.dtos.*;
import com.example.app.entities.*;
import com.example.app.reposities.ClienteRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements ClienteServiceInterface {

    @Autowired
    ClienteRepositoryInterface repository;

    @Override
    public ClienteDTO crear(ClienteDTO clienteDTO) {
        Cliente cliente = this.conersionAEntidad(clienteDTO);
        Cliente guardarCliente = repository.save(cliente);
        return this.conversionADto(guardarCliente);
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        List<Cliente> liostado = repository.findAll();
        return liostado.stream().map(this::conversionADto).toList();
    };

    @Override
    public ClienteDTO conversionADto(Cliente cliente) {
        HotelReservaDTO hotelReservaDTO = new HotelReservaDTO();
        VueloReservaDTO vueloReservaDTO = new VueloReservaDTO();

        if (cliente.getReservasHotel() == null || cliente.getReservasVuelo() == null) {
           hotelReservaDTO = null;
           vueloReservaDTO = null;
           return  null;

        }else{
            //obtener los deportistas
            List<HotelReservaDTO> hotelReservaDTOS = cliente.getReservasHotel().stream()
            .map( client -> new HotelReservaDTO(
                    client.getId_hotelReserva(),  // Identificador
                    client.getDateFrom(),         // Fecha inicio
                    client.getDateTo(),           // Fecha fin
                    client.getNights(),           // Número de noches
                    client.getPlace(),            // Lugar del hotel
                    client.getHotelCode(),        // Código del hotel
                    client.getPeopleQ(),          // Cantidad de personas
                    client.getRoomType(),
                    client.getReservado(),
                    null, null,
                    client.getPrecioFinal()))
            .toList();

            List<VueloReservaDTO> vueloReservaDTOS = cliente.getReservasVuelo().stream()
                    .map( client -> new VueloReservaDTO(
                            client.getId_vueloReserva(),         // id_vueloReserva
                            client.getDate(),                    // date
                            client.getOrigin(),                  // origin
                            client.getDestination(),             // destination
                            client.getFlightCode(),              // flightCode
                            client.getPeopleQ(),                 // peopleQuantity
                            client.getSeatType(),
                            client.getReservado(),
                            null, null,
                            client.getPrecioFinal()))
                    .toList();


            //hacer la conversion al dto
            return new ClienteDTO(cliente.getId_cliente(), cliente.getNombre(), cliente.getApellido(), hotelReservaDTOS, vueloReservaDTOS);


    }


    }

    @Override
    public Cliente conersionAEntidad(ClienteDTO clienteDTO) {
        List<HotelReserva> hotelReserva = clienteDTO.getHotelreserva().stream()
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

        List<VueloReserva> vueloReserva = clienteDTO.getVueloReservas().stream()
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

        return new Cliente(null, clienteDTO.getNombre(), clienteDTO.getApellido(), hotelReserva, vueloReserva);

    }
}
