package com.inmobivn.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserSummaryDto {

    private String scrId;
    private Integer score;
    private Integer turns;
}
