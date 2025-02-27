package com.example.app.dtos;

import com.example.app.entities.HotelReserva;
import com.example.app.entities.Vuelo;
import com.example.app.entities.VueloReserva;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ClienteDTO {

    private Long identificadorCliente;

    @JsonProperty("name")
    private String nombre;

    @JsonProperty("lastName")
    private String apellido;

    @JsonManagedReference("reservaHotel")
    @JsonProperty("hoteles")
    private List<HotelReservaDTO> hotelreserva = new ArrayList<>();

    @JsonManagedReference("reservaVuelo")
    @JsonProperty("pasajeros")
    private List<VueloReservaDTO> vueloReservas = new ArrayList<>();


}
