package com.inmobivn.javatest.dto;

public class UserSummaryDto {

    private String username;
    private Integer score;
    private Integer turns;

    public UserSummaryDto() {
    }

    public UserSummaryDto(String username, Integer score, Integer turns) {
        this.username = username;
        this.score = score;
        this.turns = turns;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTurns() {
        return turns;
    }

    public void setTurns(Integer turns) {
        this.turns = turns;
    }
}
