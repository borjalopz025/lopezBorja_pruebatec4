package com.example.app.dtos;

import com.example.app.entities.Hotel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

public class HotelReservaDTO {

    private Long identificador;

    @JsonProperty("startDate")
    private String dateFrom;

    @JsonProperty("endDate")
    private String dateTo;

    @JsonProperty("stayDuration")
    private Integer nights;

    @JsonProperty("location")
    private String place;

    @JsonProperty("hotelId")
    private String hotelCode;

    @JsonProperty("guestCount")
    private Integer peopleQuantity;

    @JsonProperty("accommodationType")
    private String roomType;

    @JsonProperty("isReserved")
    private Boolean reservado;

    @JsonBackReference("reservaHotel")
    @JsonProperty("customer")
    private ClienteDTO clientes;

    @JsonBackReference("hotel")
    @JsonProperty("hotels")
    private HotelDTO reservas;

    private Double precioFinal;
}
