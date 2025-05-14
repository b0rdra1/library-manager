package com.example.librarymanager.service;

import com.example.librarymanager.dto.BookDto;
import com.example.librarymanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDto> getAvailableBooks() {
        return bookRepository.findAvailableBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .availableCopies(book.getAvailableCopies())
                        .build())
                .collect(toList());
    }
}