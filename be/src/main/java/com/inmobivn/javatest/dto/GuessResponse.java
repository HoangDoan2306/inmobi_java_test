package com.inmobivn.javatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuessResponse {

    private boolean correct;
    private Integer guess;
    private Integer serverNumber;
    private Integer score;
    private Integer turns;
}
