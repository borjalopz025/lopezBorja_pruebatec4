package com.example.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class VueloReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vueloReserva;

    private String date;
    private String origin;
    private String destination;
    private String flightCode;
    private int peopleQ;
    private String seatType;
    private Boolean reservado;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente clientes;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_vuelo")
    private Vuelo vuelo;

    private Double precioFinal;
}
