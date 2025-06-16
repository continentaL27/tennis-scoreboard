package com.conti.scoreboard.service;

import com.conti.scoreboard.model.Match;
import com.conti.scoreboard.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Page<Match> findMatches(String playerName, Pageable pageable) {
        if (playerName != null && !playerName.isEmpty()) {
            return matchRepository.findByPlayer1NameContainingOrPlayer2NameContaining(playerName, playerName, pageable);
        }
        return matchRepository.findAll(pageable);
    }
}
