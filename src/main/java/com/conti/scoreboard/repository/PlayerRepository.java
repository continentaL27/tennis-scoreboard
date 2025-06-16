package com.conti.scoreboard.repository;

import com.conti.scoreboard.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    public Optional<Player> findByName(String name);

}
