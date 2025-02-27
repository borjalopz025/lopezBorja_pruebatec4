package com.example.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HotelReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hotelReserva;
    private String dateFrom;
    private String dateTo;
    private int nights;
    private String place;
    private String hotelCode;
    private Integer peopleQ;
    private String roomType;
    private Boolean reservado;


    @JsonBackReference("reservasHotel")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente clientes;

    @JsonBackReference("reservaHotel")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    private Double precioFinal;
}
