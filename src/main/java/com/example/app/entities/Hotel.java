package com.example.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hotel;

    private String codigoHotel;

    private String nombre;

    private String lugar;

    private Integer numeroNoches;

    private String tipoHabitacion;

    private Double precioPorNoche;

    private double numeroPersonas;

    private String disponibleDesde;

    private String disponibleHasta;

    private boolean reservado;

    @JsonManagedReference("reservaHotel")
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<HotelReserva> reservasHotel;


}
