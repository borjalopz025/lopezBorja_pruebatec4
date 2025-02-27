package com.example.app.controllers;

import com.example.app.dtos.HotelDTO;
import com.example.app.dtos.HotelReservaDTO;
import com.example.app.services.HotelReservaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class ReservasHotelController {

    @Autowired
    HotelReservaServiceInterface serviceReserva;

    // servicio de resrvas

    //    Listar todoas las reservas

    @GetMapping("/hotels/room")
    public ResponseEntity<List<HotelReservaDTO>> listarReservas(){
        List<HotelReservaDTO> listadoHoteles = serviceReserva.listarReservaHotel();
        System.out.println("esto es lo que llega al controlleer " + listadoHoteles);
        return ResponseEntity.ok(listadoHoteles);
    }

    //    filtrar reservas

    @PostMapping("/hotels/room-booking/new")
    public ResponseEntity<HotelReservaDTO> crearReservaCliente (@RequestBody HotelReservaDTO hotelReservaDTO) {
        HotelReservaDTO crearReserva = serviceReserva.crearReservaCliente(hotelReservaDTO);
        return ResponseEntity.ok(crearReserva);
    }
}
