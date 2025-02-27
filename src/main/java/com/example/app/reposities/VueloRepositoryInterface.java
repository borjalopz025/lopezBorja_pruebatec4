package com.example.app.reposities;

import com.example.app.entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VueloRepositoryInterface extends JpaRepository<Vuelo, Long> {
}
