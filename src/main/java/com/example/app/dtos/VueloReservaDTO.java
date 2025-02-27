package com.example.app.dtos;

import com.example.app.entities.Vuelo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class VueloReservaDTO {

    private Long id_vueloReserva;

    @JsonProperty("travelDate")
    private String date;

    @JsonProperty("departureLocation")
    private String origin;

    @JsonProperty("arrivalLocation")
    private String destination;

    @JsonProperty("airlineCode")
    private String flightCode;

    @JsonProperty("passengerCount")
    private Integer peopleQuantity;

    @JsonProperty("classType")
    private String seatType;

    @JsonProperty("isBooked")
    private Boolean isBooked;

    @JsonBackReference("reservaVuelo")
    @JsonProperty("passengers")
    private ClienteDTO pasajeros;

    @JsonBackReference("")
    @JsonProperty("reservas")
    private VueloDTO vuelos;

    @JsonProperty("precioTotal")
    private Double precioFinal;
}
