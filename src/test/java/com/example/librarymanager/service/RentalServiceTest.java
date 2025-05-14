package com.example.librarymanager.service;

import com.example.librarymanager.dto.RentalRequest;
import com.example.librarymanager.entity.Book;
import com.example.librarymanager.entity.Rental;
import com.example.librarymanager.repository.BookRepository;
import com.example.librarymanager.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rentBook_success() {
        RentalRequest request = new RentalRequest();
        request.setBookId(1L);
        request.setReaderId(100L);

        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .availableCopies(2)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);
        when(rentalRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Rental rental = rentalService.rentBook(request);

        assertNotNull(rental);
        assertEquals(1L, rental.getBook().getId());
        assertEquals(100L, rental.getReaderId());
        verify(bookRepository).save(book);
        verify(rentalRepository).save(any());
    }

    @Test
    void rentBook_shouldThrowException_whenNoAvailableCopies() {
        RentalRequest request = new RentalRequest();
        request.setBookId(2L);
        request.setReaderId(200L);

        Book book = Book.builder()
                .id(2L)
                .title("Spring in Action")
                .availableCopies(0)
                .build();

        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rentalService.rentBook(request);
        });

        assertEquals("No available copies", exception.getMessage());
        verify(bookRepository, never()).save(any());
        verify(rentalRepository, never()).save(any());
    }

    @Test
    void returnBook_success() {
        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .availableCopies(0)
                .build();

        Rental rental = Rental.builder()
                .id(10L)
                .book(book)
                .readerId(100L)
                .rentedAt(LocalDate.now().minusDays(5))
                .returnedAt(null)
                .build();

        when(rentalRepository.findById(10L)).thenReturn(Optional.of(rental));
        when(bookRepository.save(any())).thenReturn(book);
        when(rentalRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Rental result = rentalService.returnBook(10L);

        assertNotNull(result.getReturnedAt());
        assertEquals(1, book.getAvailableCopies());
        verify(bookRepository).save(book);
        verify(rentalRepository).save(rental);
    }

    @Test
    void returnBook_shouldDoNothing_ifAlreadyReturned() {
        Book book = Book.builder()
                .id(1L)
                .title("Effective Java")
                .availableCopies(1)
                .build();

        Rental rental = Rental.builder()
                .id(11L)
                .book(book)
                .readerId(101L)
                .rentedAt(LocalDate.now().minusDays(10))
                .returnedAt(LocalDate.now().minusDays(2))
                .build();

        when(rentalRepository.findById(11L)).thenReturn(Optional.of(rental));

        Rental result = rentalService.returnBook(11L);

        assertEquals(rental.getReturnedAt(), result.getReturnedAt());
        verify(bookRepository, never()).save(any());
        verify(rentalRepository, never()).save(any());
    }

    @Test
    void returnBook_shouldThrowException_ifRentalNotFound() {
        Long rentalId = 999L;

        when(rentalRepository.findById(rentalId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rentalService.returnBook(rentalId);
        });

        assertEquals("Rental not found", exception.getMessage());
        verify(bookRepository, never()).save(any());
        verify(rentalRepository, never()).save(any());
    }
}
