package com.example.librarymanager.repository;

import com.example.librarymanager.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByReaderIdOrderByRentedAtDesc(Long readerId);
}