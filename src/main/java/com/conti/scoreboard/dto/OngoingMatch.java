package com.conti.scoreboard.dto;


import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class OngoingMatch {

    private String player1Name;
    private String player2Name;
    private int player1Points;
    private int player2Points;
    private int player1Games;
    private int player2Games;
    private int player1Sets;
    private int player2Sets;
    private boolean tieBreak;



    public OngoingMatch(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Points = 0;
        this.player2Points = 0;
        this.player1Games = 0;
        this.player2Games = 0;
        this.player1Sets = 0;
        this.player2Sets = 0;
        this.tieBreak = false;
    }

    public void player1Scores() {
        scorePoint(true);
    }

    public void player2Scores() {
        scorePoint(false);
    }

    private void scorePoint(boolean isPlayer1) {
        if (tieBreak) {
            handleTieBreakPoint(isPlayer1);
        } else {
            handleRegularPoint(isPlayer1);
        }
        checkGameWin();
        checkSetWin();
    }

    private void handleRegularPoint(boolean isPlayer1) {
        if (isPlayer1) {
            player1Points++;
        } else {
            player2Points++;
        }
    }

    private void handleTieBreakPoint(boolean isPlayer1) {
        if (isPlayer1) {
            player1Points++;
        } else {
            player2Points++;
        }
    }

    public boolean isMatchEnds() {
        return player1Sets >= 2 || player2Sets >= 2;
    }

    private void checkGameWin() {
        if (tieBreak) {
            if (player1Points >= 7 && player1Points - player2Points >= 2) {
                player1Games++;
                resetPoints();
                tieBreak = false;
            } else if (player2Points >= 7 && player2Points - player1Points >= 2) {
                player2Games++;
                resetPoints();
                tieBreak = false;
            }
        } else {
            if (player1Points >= 4 && player1Points - player2Points >= 2) {
                player1Games++;
                resetPoints();
            } else if (player2Points >= 4 && player2Points - player1Points >= 2) {
                player2Games++;
                resetPoints();
            } else if (player1Points >= 3 && player2Points >= 3 && player1Points == player2Points) {
                // Deuce - no action needed
            }
        }
    }

    private void checkSetWin() {
        if (player1Games >= 6 && player1Games - player2Games >= 2) {
            player1Sets++;
            resetGames();
        } else if (player2Games >= 6 && player2Games - player1Games >= 2) {
            player2Sets++;
            resetGames();
        } else if (player1Games == 6 && player2Games == 6) {
            tieBreak = true;
        } else if (player1Games == 7) {
            player1Sets++;
            resetGames();
        } else if (player2Games == 7) {
            player2Sets++;
            resetGames();
        }
    }

    private void resetPoints() {
        player1Points = 0;
        player2Points = 0;
    }

    private void resetGames() {
        player1Games = 0;
        player2Games = 0;
        resetPoints();
        tieBreak = false;
    }

    public String getCurrentScore() {
        if (tieBreak) {
            return String.format("Sets: %d-%d, Games: %d-%d, TieBreak: %d-%d",
                    player1Sets, player2Sets, player1Games, player2Games, player1Points, player2Points);
        } else {
            return String.format("Sets: %d-%d, Games: %d-%d, Points: %s-%s",
                    player1Sets, player2Sets, player1Games, player2Games,
                    translatePoint(player1Points), translatePoint(player2Points));
        }
    }

    private String translatePoint(int points) {
        return switch (points) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            case 4 -> "AD";
            default -> String.valueOf(points);
        };
    }
}
