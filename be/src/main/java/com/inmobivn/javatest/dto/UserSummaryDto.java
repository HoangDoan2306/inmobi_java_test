package com.inmobivn.javatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryDto {

    private String scrId;
    private Integer score;
    private Integer turns;
}
