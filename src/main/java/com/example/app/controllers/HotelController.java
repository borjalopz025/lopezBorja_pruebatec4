package com.example.app.controllers;

import com.example.app.dtos.HotelDTO;
import com.example.app.dtos.HotelReservaDTO;
import com.example.app.services.HotelReservaServiceInterface;
import com.example.app.services.HotelServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    HotelServiceInterface service;

// servicio de hotel
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDTO>> listar(){
        List<HotelDTO> listadoHoteles = service.listarHoteles();
        return ResponseEntity.ok(listadoHoteles);
    }

    @GetMapping("/hotels/rooms")
    public ResponseEntity<List<HotelDTO>> filtrarPorFechaDestino (
            @RequestParam( required = false) LocalDate fechaComienzo ,
            @RequestParam( required = false)  LocalDate fechaFin,
            @RequestParam( required = false)  String destino) {
        List<HotelDTO> listado = service.filtrarFechaDestino(fechaComienzo, fechaFin, destino);
        return ResponseEntity.ok(listado);
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDTO> buscarPorId(@PathVariable Long id){
        HotelDTO buscarHotel = service.encontrarHotelPorId(id);
        return ResponseEntity.ok(buscarHotel);
    }

    @PostMapping("/hotels/new")
    public ResponseEntity<HotelDTO> crearHotel (@RequestBody HotelDTO hotelDTO) {
        HotelDTO crearHotel = service.crearHoteles(hotelDTO);
        return ResponseEntity.ok(crearHotel);
    }

    @PutMapping("/hotels/edit/{id}")
    public ResponseEntity<HotelDTO> actualizarHotel(@PathVariable Long id , @RequestBody HotelDTO hotelDTO){
        HotelDTO hotelActualizado = service.actualizarHotel(id, hotelDTO);
        return ResponseEntity.ok(hotelActualizado);
    }

    @DeleteMapping("/hotels/delete/{id}")
    public ResponseEntity<List<HotelDTO>> eliminarHotel (@PathVariable Long id){
        List<HotelDTO> eliminarHotel = service.eliminarHotel(id);
        return ResponseEntity.ok(eliminarHotel);
    }

}
