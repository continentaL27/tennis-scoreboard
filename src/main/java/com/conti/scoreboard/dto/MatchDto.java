package com.conti.scoreboard.dto;

import com.conti.scoreboard.model.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MatchDto {

    private String uuid;
    private String playerOne;
    private String playerTwo;
    private String winner;

    public static MatchDto matchToDto(Match match) {
        return new MatchDto(match.getUuid(),
                match.getPlayer1().getName(),
                match.getPlayer2().getName(),
                match.getWinner().getName());
    }
}
