package com.conti.scoreboard.service;

import com.conti.scoreboard.dto.MatchDto;
import com.conti.scoreboard.dto.OngoingMatch;
import com.conti.scoreboard.error.MatchNotFoundException;
import com.conti.scoreboard.model.Match;
import com.conti.scoreboard.model.Player;
import com.conti.scoreboard.repository.MatchRepository;
import com.conti.scoreboard.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class FinishedMatchesPersistenceService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public MatchDto findByUuid(String uuid) {
        Optional<Match> match = matchRepository.findByUuid(uuid);
        if (match.isEmpty()) {
            throw new MatchNotFoundException("uuid not found: " + uuid);
        }
        return MatchDto.matchToDto(match.get());
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public Match saveOngoingMatch(OngoingMatch ongoingMatch) {
        String winner = ongoingMatch.getPlayer1Sets() > ongoingMatch.getPlayer2Sets() ?
                ongoingMatch.getPlayer1Name():
                ongoingMatch.getPlayer2Name();

        Player player1 = playerRepository.findByName(ongoingMatch.getPlayer1Name()).get();
        Player player2 = playerRepository.findByName(ongoingMatch.getPlayer2Name()).get();

        if (winner.equals(player1.getName()))
            return matchRepository.save(new Match(player1, player2, player1));
        else
            return matchRepository.save(new Match(player1, player2, player2));
    }

}
