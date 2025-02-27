package com.example.app.dtos;

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

public class VueloDTO {

    @JsonProperty("flightId")
    private Long flightId;

    @JsonProperty("flightNumber")
    private String flightNumber;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("seatType")
    private String seatType;

    @JsonProperty("pricePerPerson")
    private Double pricePerPerson;

    @JsonProperty("departureDate")
    private String departureDate;

    @JsonProperty("returnDate")
    private String returnDate;

    @JsonProperty("reservations")
    private List<VueloReservaDTO> reservations = new ArrayList<>();
}
