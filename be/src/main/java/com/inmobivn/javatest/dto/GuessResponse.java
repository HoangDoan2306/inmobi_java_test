package com.inmobivn.javatest.dto;

public class GuessResponse {

    private boolean correct;
    private Integer guess;
    private Integer serverNumber;
    private Integer score;
    private Integer turns;

    public GuessResponse() {
    }

    public GuessResponse(boolean correct, Integer guess, Integer serverNumber, Integer score, Integer turns) {
        this.correct = correct;
        this.guess = guess;
        this.serverNumber = serverNumber;
        this.score = score;
        this.turns = turns;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Integer getGuess() {
        return guess;
    }

    public void setGuess(Integer guess) {
        this.guess = guess;
    }

    public Integer getServerNumber() {
        return serverNumber;
    }

    public void setServerNumber(Integer serverNumber) {
        this.serverNumber = serverNumber;
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
