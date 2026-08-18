package com.inmobivn.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GuessResponse {

    private boolean correct;
    private Integer guess;
    private Integer serverNumber;
    private Integer score;
    private Integer turns;
}
