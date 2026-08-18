package com.inmobivn.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LeaderboardEntryDto {

    private String scrId;
    private Integer score;
}
