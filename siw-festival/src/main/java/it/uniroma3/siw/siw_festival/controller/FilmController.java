package it.uniroma3.siw.siw_festival.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Recensione;
import it.uniroma3.siw.siw_festival.service.FilmService;
import it.uniroma3.siw.siw_festival.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
public class FilmController {

    private final RecensioneService recensioneService;
    private final FilmService filmService;

    public FilmController(FilmService filmService, RecensioneService recensioneService) {
        this.filmService = filmService;
        this.recensioneService = recensioneService;
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

    @GetMapping("/film/{id}/recensioni/new")
    public String getRecensioneForm(@PathVariable Long id, Model model) {
        model.addAttribute("recensione", new Recensione());
        model.addAttribute("film", this.filmService.findById(id));
        return "recensioni/form";
    }

    @PostMapping("/film/{id}/recensioni/new")
    public String postRecensione(@PathVariable Long id,
            @Valid @ModelAttribute("recensione") Recensione recensioneForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("film", this.filmService.findById(id));
            return "recensioni/form";
        }
        this.recensioneService.creaRecensione(id, userDetails.getUsername(), recensioneForm.getTesto(),
                recensioneForm.getVoto());

        return "redirect:/film/" + id;
    }

}
