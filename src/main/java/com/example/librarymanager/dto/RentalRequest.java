package com.example.librarymanager.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class RentalRequest {
    @NotNull
    @Min(1)
    private Long bookId;

    @NotNull
    @Min(1)
    private Long readerId;
}