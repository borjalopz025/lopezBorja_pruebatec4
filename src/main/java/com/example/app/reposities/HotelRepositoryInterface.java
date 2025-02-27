package com.example.app.reposities;

import com.example.app.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepositoryInterface extends JpaRepository<Hotel, Long> {
}
