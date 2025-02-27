package com.example.app.reposities;

import com.example.app.entities.VueloReserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VueloReservaRepositoryInterface extends JpaRepository<VueloReserva, Long> {
}
