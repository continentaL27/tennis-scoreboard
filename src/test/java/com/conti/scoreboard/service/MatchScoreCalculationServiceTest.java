package com.conti.scoreboard.service;

import com.conti.scoreboard.dto.OngoingMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MatchScoreCalculationServiceTest {

    MatchScoreCalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new MatchScoreCalculationService();
    }

    @DisplayName("When update 1 point")
    @Test
    public void shouldUpdatePointForPlayer1() {
        OngoingMatch match1 = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .build();

        OngoingMatch expected = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .player1Points(1)
                .build();

        OngoingMatch actual = calculationService.updatePoint(match1, "jb");

        assertEquals(expected, actual);

    }

    @DisplayName("When update 1 point state change")
    @Test
    public void shouldUpdatePointForPlayer1StateChanged() {
        OngoingMatch match1 = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .build();

        OngoingMatch expected = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .player1Points(0)
                .build();

        OngoingMatch actual = calculationService.updatePoint(match1, "jb");

        assertNotEquals(expected, actual);

    }

    @DisplayName("When update 4 point set wins")
    @Test
    public void shouldUpdatePoint4ForPlayer1() {
        OngoingMatch match1 = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .build();

        OngoingMatch expected = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .player1Points(4)
                .build();

        calculationService.updatePoint(match1, "jb");
        calculationService.updatePoint(match1, "jb");
        calculationService.updatePoint(match1, "jb");
        OngoingMatch actual = calculationService.updatePoint(match1, "jb");

        assertNotEquals(expected, actual);
    }

    @DisplayName("When update 4 point game wins")
    @Test
    public void shouldGameWinsForPlayer1() {
        OngoingMatch match1 = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .build();

        OngoingMatch expected = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .player1Points(1)
                .player1Games(1)
                .build();

        calculationService.updatePoint(match1, "jb");
        calculationService.updatePoint(match1, "jb");
        calculationService.updatePoint(match1, "jb");
        calculationService.updatePoint(match1, "jb");
        OngoingMatch actual = calculationService.updatePoint(match1, "jb");

        assertEquals(expected, actual);
    }

    @DisplayName("When player1 wins game")
    @Test
    public void shouldPlayer1Game() {
        OngoingMatch match1 = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .build();

        OngoingMatch expected = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .player1Points(1)
                .player2Games(0)
                .player1Games(1)
                .build();

        calculationService.updatePoint(match1, "jb");      // jb 15
        calculationService.updatePoint(match1, "rb");      // rb 15
        calculationService.updatePoint(match1, "jb");      // jb 30
        calculationService.updatePoint(match1, "rb");       // rb 30
        calculationService.updatePoint(match1, "jb");     // jb 40
        calculationService.updatePoint(match1, "jb");     // jb 40
        OngoingMatch actual = calculationService.updatePoint(match1, "jb");      // rb 40
        //        OngoingMatch actual = calculationService.updatePoint(match1, "jb");

        assertEquals(expected, actual);
    }

    @DisplayName("When player1 wins set")
    @Test
    public void shouldSetWinsForPlayer1() {
        OngoingMatch match1 = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .player1Games(6)
                .player1Points(4)
                .build();

        OngoingMatch expected = OngoingMatch.builder().player1Name("jb")
                .player2Name("rb")
                .player1Sets(1)
                .build();

        OngoingMatch actual = calculationService.updatePoint(match1, "jb");      // rb 40
        //        OngoingMatch actual = calculationService.updatePoint(match1, "jb");

        assertEquals(expected, actual);
    }

}