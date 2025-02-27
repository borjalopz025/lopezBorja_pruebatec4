package com.example.app.reposities;

import com.example.app.entities.HotelReserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelReservaRepositoryInterface extends JpaRepository<HotelReserva, Long> {
}
