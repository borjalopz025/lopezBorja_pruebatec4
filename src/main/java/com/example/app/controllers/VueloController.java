package com.example.app.controllers;

import com.example.app.dtos.VueloDTO;
import com.example.app.dtos.VueloReservaDTO;
import com.example.app.services.VueloServicioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class VueloController {

    @Autowired
    VueloServicioInterface service;

    @GetMapping("/flight")
    public ResponseEntity<List<VueloDTO>> listar (){
        List<VueloDTO> listado = service.listarVuelos();
        return ResponseEntity.ok(listado);
    }


    @GetMapping("/flights")
    public ResponseEntity<List<VueloDTO>> filtrarVuelos(
            @RequestParam( required = false) LocalDate fechaIda,
            @RequestParam( required = false) LocalDate fechaVuelta,
            @RequestParam( required = false) String origen,
            @RequestParam( required = false) String destino) {

        List<VueloDTO> vuelos = service.filtrarVuelos(fechaIda, fechaVuelta, origen, destino);
        return ResponseEntity.ok(vuelos);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<VueloDTO> buscarVueloId(@PathVariable Long id){
        VueloDTO vueloEncontrado = service.buscarPorIdVuelo(id);
        return ResponseEntity.ok(vueloEncontrado);
    }

    @PostMapping("/flights/new")
    public ResponseEntity<VueloDTO> crear (@RequestBody VueloDTO vueloDTO){

        VueloDTO guardarVuelo = service.crearVuelo(vueloDTO);
        return ResponseEntity.ok(guardarVuelo);
    }

    @PutMapping("/flights/edit/{id}")
    public ResponseEntity<VueloDTO> actualizarVuelo(@PathVariable Long id, @RequestBody VueloDTO vueloDTO){
        VueloDTO vueloDTOActualizado = service.actualizarVuelo(id, vueloDTO);
        return ResponseEntity.ok(vueloDTOActualizado);
    }

    @DeleteMapping("/flights/delete/{id}")
    public ResponseEntity<List<VueloDTO>> eliminarVuelo(@PathVariable Long id) {
        List<VueloDTO> eliminar = service.eliminarVuelo(id);
        return ResponseEntity.ok(eliminar);
    }

}
