package com.conti.scoreboard.controller;

import com.conti.scoreboard.dto.OngoingMatch;
import com.conti.scoreboard.model.Match;
import com.conti.scoreboard.model.Player;
import com.conti.scoreboard.service.FinishedMatchesPersistenceService;
import com.conti.scoreboard.service.MatchScoreCalculationService;
import com.conti.scoreboard.service.OngoingMatchScoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class MatchScoreController {

    private OngoingMatchScoreService ongoingMatchScoreService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    private MatchScoreCalculationService matchScoreCalculationService;

    @GetMapping("/match-score")
    public String getNewMatchScore(@RequestParam String uuid, Model model) {

        OngoingMatch match = ongoingMatchScoreService.getOngoingMatchByUUID(uuid);
        model.addAttribute("ongoingMatch", match);
        model.addAttribute("uuid", uuid);
        model.addAttribute("matchEnded", matchScoreCalculationService.ongoingMatchIsEnded(match));
        return "match-score";
    }

    @PostMapping("/match-score")
    public String updatePointWinner(@RequestParam String matchUuid,
                                    @RequestParam String pointWinnerName,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        OngoingMatch ongoingMatch = ongoingMatchScoreService.updatePointWinner(matchUuid, pointWinnerName);
        boolean isEnded = matchScoreCalculationService.ongoingMatchIsEnded(ongoingMatch);


        if (!isEnded) {
            model.addAttribute("ongoingMatch", ongoingMatch);
            model.addAttribute("uuid", matchUuid);
            model.addAttribute("matchEnded", isEnded);
            return "match-score";
        } else {
            finishedMatchesPersistenceService.saveOngoingMatch(ongoingMatch);
            System.out.println("save ongm");
            redirectAttributes.addFlashAttribute("finishedMatch", ongoingMatch);
            return "redirect:/finished-match";
        }
    }
}
