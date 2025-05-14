package com.example.librarymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopAuthor {
    private String author;
    private Long rentals;
}