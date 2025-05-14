package com.example.librarymanager.service;

import com.example.librarymanager.dto.RentalRequest;
import com.example.librarymanager.dto.TopAuthor;
import com.example.librarymanager.entity.Book;
import com.example.librarymanager.entity.Rental;
import com.example.librarymanager.repository.BookRepository;
import com.example.librarymanager.repository.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final BookRepository bookRepository;
    private final RentalRepository rentalRepository;

    @Transactional
    public Rental rentBook(RentalRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Rental rental = Rental.builder()
                .book(book)
                .readerId(request.getReaderId())
                .rentedAt(LocalDate.now())
                .build();

        return rentalRepository.save(rental);
    }

    @Transactional
    public Rental returnBook(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        if (rental.getReturnedAt() != null) {
            return rental;
        }

        rental.setReturnedAt(LocalDate.now());

        Book book = rental.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return rentalRepository.save(rental);
    }
    public List<Rental> getRentalsByReader(Long readerId) {
        return rentalRepository.findByReaderIdOrderByRentedAtDesc(readerId);
    }

    public List<TopAuthor> getTopAuthors() {
        return rentalRepository.findAll().stream()
                .collect(Collectors.groupingBy(r -> r.getBook().getAuthor(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new TopAuthor(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Long.compare(b.getRentals(), a.getRentals()))
                .collect(Collectors.toList());
    }
}