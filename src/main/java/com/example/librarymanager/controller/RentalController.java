package com.example.librarymanager.controller;

import com.example.librarymanager.dto.RentalRequest;
import com.example.librarymanager.dto.TopAuthor;
import com.example.librarymanager.entity.Rental;
import com.example.librarymanager.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @PostMapping
    public Rental rentBook(@RequestBody RentalRequest request) {
        return rentalService.rentBook(request);
    }

    @PostMapping("/returns/{rentalId}")
    public Rental returnBook(@PathVariable Long rentalId) {
        return rentalService.returnBook(rentalId);
    }

    @GetMapping("/reader/{readerId}")
    public List<Rental> getReaderRentals(@PathVariable Long readerId) {
        return rentalService.getRentalsByReader(readerId);
    }

    @GetMapping("/statistics/top-authors")
    public List<TopAuthor> getTopAuthors() {
        return rentalService.getTopAuthors();
    }
}