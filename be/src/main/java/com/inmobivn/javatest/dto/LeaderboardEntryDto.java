package com.inmobivn.javatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaderboardEntryDto {

    private String username;
    private Integer score;
}
