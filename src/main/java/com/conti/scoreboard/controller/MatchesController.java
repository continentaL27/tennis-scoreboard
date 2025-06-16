package com.conti.scoreboard.controller;

import com.conti.scoreboard.model.Match;
import com.conti.scoreboard.service.MatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

@RequestMapping("/matches")
public class MatchesController {

    private final MatchService matchService;

    public MatchesController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public String matches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filterByPlayerName,
            Model model) {

        Page<Match> matches = matchService.findMatches(filterByPlayerName, PageRequest.of(page, 5));

        model.addAttribute("matches", matches);
        model.addAttribute("filterByPlayerName", filterByPlayerName);

        return "matches";
    }
}
