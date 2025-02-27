package com.example.app.reposities;

import com.example.app.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositoryInterface extends JpaRepository<Cliente, Long> {
}
