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
    public String getFilmDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Film f = this.filmService.findById(id);
        model.addAttribute("film", f);
        model.addAttribute("regista", f.getRegista());
        model.addAttribute("festivalList", f.getFestival());
        model.addAttribute("proiezioniList", f.getProiezioni());
        model.addAttribute("recensioniList", f.getRecensioni());

        if (userDetails != null) {
            model.addAttribute("currentUsername", userDetails.getUsername());
        }
        return "film/show";
    }

    @GetMapping("/film/{id}/recensioni/new")
    public String getRecensioneForm(@PathVariable Long id, Model model) {
        model.addAttribute("recensione", new Recensione());
        model.addAttribute("film", this.filmService.findById(id));
        return "recensioni/form";
    }

    @PostMapping("/film/{id}/recensioni/new")
    public String postRecensioneForm(@PathVariable Long id,
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

    @GetMapping("/film/{fId}/recensioni/{rId}/edit")
    public String getRecensioneEdit(@PathVariable Long fId, @PathVariable Long rId, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        Recensione r = this.recensioneService.findById(rId);
        if (!this.recensioneService.isOwner(r, userDetails.getUsername()))
            return "redirect:/film/" + fId;
        model.addAttribute("recensione", r);
        model.addAttribute("fId", fId);
        return "recensioni/editForm";
    }

    @PostMapping("/film/{fId}/recensioni/{rId}/edit")
    public String postRecensioneEdit(@PathVariable Long fId,
            @PathVariable Long rId,
            @Valid @ModelAttribute("recensione") Recensione recensioneForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        Recensione r = this.recensioneService.findById(rId);

        if (!this.recensioneService.isOwner(r, userDetails.getUsername())) {
            return "redirect:/film/" + fId;
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("fId", fId);
            return "recensioni/editForm";
        }

        this.recensioneService.updateRecensione(rId, recensioneForm.getTesto(), recensioneForm.getVoto());
        return "redirect:/film/" + fId;
    }

}
