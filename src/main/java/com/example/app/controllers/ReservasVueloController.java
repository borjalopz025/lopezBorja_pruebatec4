package com.example.app.controllers;

import com.example.app.dtos.VueloReservaDTO;
import com.example.app.services.VueloReservaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class ReservasVueloController {

    @Autowired
    VueloReservaServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<VueloReservaDTO>> listar (){
        List<VueloReservaDTO> listado = service.listarVuelosReserva();
        return ResponseEntity.ok(listado);
    }


    @PostMapping("/flight-booking/new")
    public ResponseEntity<VueloReservaDTO> crearReserva (@RequestBody VueloReservaDTO vueloReservaDTO){
        VueloReservaDTO guardarVuelo = service.crearVueloReservaCliente(vueloReservaDTO);
        return ResponseEntity.ok(guardarVuelo);
    }
}