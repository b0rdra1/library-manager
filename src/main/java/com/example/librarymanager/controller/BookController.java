package com.example.librarymanager.controller;

import com.example.librarymanager.dto.BookDto;
import com.example.librarymanager.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/available")
    public List<BookDto> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }
}