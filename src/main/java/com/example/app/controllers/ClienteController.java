package com.example.app.controllers;

import com.example.app.dtos.ClienteDTO;
import com.example.app.services.ClienteServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteServiceInterface service;


    @GetMapping({"/",""})
    public ResponseEntity<List<ClienteDTO>> listar(){
        List<ClienteDTO> listadoClientes = service.listarClientes();
        return ResponseEntity.ok(listadoClientes);
    }

    @PostMapping({"/",""})
    public ResponseEntity<ClienteDTO> crearCliente (@RequestBody ClienteDTO clienteDTO) {

        System.out.println("Content-Type recibido: " + clienteDTO);
        System.out.println("Cuerpo recibido: " + clienteDTO);
        ClienteDTO crearCliente = service.crear(clienteDTO);
        return ResponseEntity.ok(crearCliente);
    }


}
