package com.example.app.services;

import com.example.app.dtos.ClienteDTO;
import com.example.app.entities.Cliente;

import java.util.List;

public interface ClienteServiceInterface {

    ClienteDTO crear(ClienteDTO clienteDTO);

    List<ClienteDTO> listarClientes();


    ClienteDTO conversionADto(Cliente cliente);

    Cliente conersionAEntidad(ClienteDTO clienteDTO);
}
