package com.conti.scoreboard.repository;

import com.conti.scoreboard.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    public Optional<Match> findByUuid(String uuid);


    public Page<Match> findByPlayer1NameContainingOrPlayer2NameContaining(
            String player1, String player2, Pageable pageable);
}
