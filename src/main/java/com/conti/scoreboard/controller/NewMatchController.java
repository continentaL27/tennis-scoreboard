package com.conti.scoreboard.controller;

import com.conti.scoreboard.service.OngoingMatchScoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/new-match")
@AllArgsConstructor
public class NewMatchController {

    private final OngoingMatchScoreService ongoingMatchScoreService;

    @GetMapping
    public String newMatch() {
        return "new-match";
    }

    @PostMapping
    public String postNewMatch(@RequestParam String firstPlayerName,
                               @RequestParam String secondPlayerName) {
        String uuid = ongoingMatchScoreService.registerNewMatch(
                firstPlayerName, secondPlayerName);
        return "redirect:/match-score?uuid=" + uuid;
    }

}
