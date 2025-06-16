package com.conti.scoreboard.service;

import com.conti.scoreboard.model.Player;
import com.conti.scoreboard.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findByName(String name) {
        return playerRepository.findByName(name);
    }

}
