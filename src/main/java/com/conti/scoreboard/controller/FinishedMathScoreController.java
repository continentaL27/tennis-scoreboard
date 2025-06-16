package com.conti.scoreboard.controller;

import com.conti.scoreboard.dto.OngoingMatch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/finished-match")
public class FinishedMathScoreController {

    @GetMapping
    public String showFinishedMatch(Model model) {
        // Атрибут будет доступен через flash-механизм
        if (!model.containsAttribute("finishedMatch")) {
            // Обработка случая, когда атрибута нет (например, прямой доступ к URL)
            return "redirect:/matches";
        }
        return "finished-match";
    }
}
