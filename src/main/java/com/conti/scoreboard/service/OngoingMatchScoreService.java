package com.conti.scoreboard.service;

import com.conti.scoreboard.dto.OngoingMatch;
import com.conti.scoreboard.error.OngoingMatchNotFoundException;
import com.conti.scoreboard.model.Match;
import com.conti.scoreboard.model.Player;
import com.conti.scoreboard.repository.PlayerRepository;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OngoingMatchScoreService {

    @Autowired
    private final PlayerRepository playerRepository;
    @Autowired
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    @Autowired
    private final MatchScoreCalculationService matchScoreCalculationService;
    private final Map<String, OngoingMatch> ongoingMatchMap;


    public OngoingMatchScoreService(PlayerRepository playerRepository,
                                    FinishedMatchesPersistenceService finishedMatchesPersistenceService,
                                    MatchScoreCalculationService matchScoreCalculationService) {
        this.playerRepository = playerRepository;
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
        this.matchScoreCalculationService = matchScoreCalculationService;
        this.ongoingMatchMap = new HashMap<>();
    }

    public String registerNewMatch(String firstPlayerName,
                                 String secondPlayerName) {

        String newMatchId = UUID.randomUUID().toString();

        Optional<Player> firstPlayer = getOrSaveNewPlayer(firstPlayerName);
        Optional<Player> secondPlayer = getOrSaveNewPlayer(secondPlayerName);

        OngoingMatch newOngoingMatch = new OngoingMatch(
                firstPlayer.get().getName(),
                secondPlayer.get().getName());

        ongoingMatchMap.put(newMatchId, newOngoingMatch);

        return newMatchId;
    }

    private Optional<Player> getOrSaveNewPlayer(String playerName) {
        Optional<Player> player = playerRepository.findByName(playerName);
        if (player.isEmpty())
            player = Optional.of(playerRepository.save(new Player(playerName)));
        return player;
    }

    public OngoingMatch getOngoingMatchByUUID(String uuid) {
        if (!ongoingMatchMap.containsKey(uuid)) {
            throw new OngoingMatchNotFoundException("match not found");
        }

        return ongoingMatchMap.get(uuid);
    }

    public OngoingMatch updatePointWinner(String uuid,String pointWinnerName) {
        if (!ongoingMatchMap.containsKey(uuid)) {
            throw new OngoingMatchNotFoundException(uuid + " not found");
        }

        OngoingMatch ongoingMatch = ongoingMatchMap.get(uuid);

        // if immutable
        OngoingMatch updated = matchScoreCalculationService.updatePoint(ongoingMatch, pointWinnerName);
        ongoingMatchMap.put(uuid, updated);

        return ongoingMatch;
    };

    public void removeMatchByUUID(String uuid) {
        OngoingMatch finishedMatch = ongoingMatchMap.get(uuid);

        System.out.println("remove and save");
        String winnerName = finishedMatch.getPlayer1Sets() > finishedMatch.getPlayer2Sets() ?
                finishedMatch.getPlayer1Name():
                finishedMatch.getPlayer2Name();
        finishedMatchesPersistenceService.saveMatch(new Match(
                new Player(finishedMatch.getPlayer1Name()),
                new Player(finishedMatch.getPlayer2Name()),
                new Player(winnerName)));

        ongoingMatchMap.remove(uuid);
    }

}