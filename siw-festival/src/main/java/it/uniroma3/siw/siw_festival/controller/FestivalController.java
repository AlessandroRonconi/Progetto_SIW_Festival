package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.siw_festival.service.FestivalService;

@Controller
public class FestivalController {

    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @GetMapping("/festival")
    public String getFestivalList(Model model) {
        model.addAttribute("festivalList", this.festivalService.findAll());
        return "festival/list";
    }

}
