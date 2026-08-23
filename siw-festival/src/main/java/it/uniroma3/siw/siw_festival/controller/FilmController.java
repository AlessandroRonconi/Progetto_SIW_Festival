package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.service.FilmService;

@Controller
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/film/{id}")
    public String getFilmDetail(@PathVariable Long id, Model model) {
        Film f = this.filmService.findById(id);
        model.addAttribute("film", f);
        model.addAttribute("regista", f.getRegista());
        model.addAttribute("festivalList", f.getFestival());
        model.addAttribute("proiezioniList", f.getProiezioni());
        model.addAttribute("recensioniList", f.getRecensioni());
        return "film/show";
    }
}
