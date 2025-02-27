package com.example.app.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class HotelDTO {

    private Long identificador;

    @JsonProperty("hotelName")
    private String nombre;

    @JsonProperty("location")
    private String lugar;

    @JsonProperty("numberOfNights")
    private Integer numeroNoches;

    @JsonProperty("roomType")
    private String tipoHabitacion;

    @JsonProperty("pricePerNight")
    private Double precioPorNoche;

    @JsonProperty("numberOfGuests")
    private Double numeroPersonas;

    @JsonProperty("availableFrom")
    private String disponibleDesde;

    @JsonProperty("availableTo")
    private String disponibleHasta;

    @JsonProperty("isBooked")
    private Boolean reservado;

    @JsonManagedReference("hotel")
    @JsonProperty("reservas")
    private List<HotelReservaDTO> reservas = new ArrayList<>();
}
