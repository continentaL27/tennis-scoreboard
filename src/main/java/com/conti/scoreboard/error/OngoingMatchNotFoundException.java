package com.conti.scoreboard.error;

public class OngoingMatchNotFoundException extends RuntimeException {
    public OngoingMatchNotFoundException(String message) {
        super(message);
    }
}
