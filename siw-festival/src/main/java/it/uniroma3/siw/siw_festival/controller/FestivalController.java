package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.siw_festival.model.Festival;
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

    @GetMapping("/festival/{id}")
    public String getFestivalDetail(@PathVariable Long id, Model model) {
        model.addAttribute("festival", this.festivalService.findById(id));
        return "festival/show";
    }

    @GetMapping("/festival/{id}/film")
    public String getFestivalFilm(@PathVariable Long id, Model model) {
        Festival f = this.festivalService.findById(id);
        model.addAttribute("festival", f);
        model.addAttribute("filmList", f.getFilm());
        return "film/list";
    }
    
    @GetMapping("/festival/{id}/proiezioni")
    public String getFestivalProiezioni(@PathVariable Long id, Model model) {
        Festival f = this.festivalService.findById(id);
        model.addAttribute("festival", f);
        model.addAttribute("proiezioniList", f.getProiezioni());
        return "proiezioni/list";
    }
}
