package com.conti.scoreboard.service;

import com.conti.scoreboard.dto.OngoingMatch;
import org.springframework.stereotype.Service;

@Service
public class MatchScoreCalculationService {

    public OngoingMatch updatePoint(OngoingMatch ongoingMatch,String pointWinnerName) {
        if (pointWinnerName.equals(ongoingMatch.getPlayer1Name())) {
            ongoingMatch.player1Scores();
        } else if (pointWinnerName.equals(ongoingMatch.getPlayer2Name())) {
            ongoingMatch.player2Scores();
        } else {
            // do nothing
        }
        return ongoingMatch;
    }

    public boolean ongoingMatchIsEnded(OngoingMatch ongoingMatch) {
        return ongoingMatch.isMatchEnds();
    }
}
