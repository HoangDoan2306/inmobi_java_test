package com.inmobivn.javatest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GuessRequest {

    @NotNull(message = "Guess is required")
    @Min(value = 1, message = "Guess must be between 1 and 5")
    @Max(value = 5, message = "Guess must be between 1 and 5")
    private Integer guess;
}
