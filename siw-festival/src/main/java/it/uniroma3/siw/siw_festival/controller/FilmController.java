package it.uniroma3.siw.siw_festival.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Recensione;
import it.uniroma3.siw.siw_festival.service.FilmService;
import it.uniroma3.siw.siw_festival.service.ProiezioneService;
import it.uniroma3.siw.siw_festival.service.RecensioneService;
import it.uniroma3.siw.siw_festival.service.RegistaService;
import jakarta.validation.Valid;

@Controller
public class FilmController {

    private final ProiezioneService proiezioneService;
    private final RegistaService registaService;
    private final RecensioneService recensioneService;
    private final FilmService filmService;

    public FilmController(FilmService filmService, RecensioneService recensioneService, RegistaService registaService,
            ProiezioneService proiezioneService) {
        this.filmService = filmService;
        this.recensioneService = recensioneService;
        this.registaService = registaService;
        this.proiezioneService = proiezioneService;
    }

    @GetMapping("/film/{id}")
    public String getFilmDetail(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Film f = this.filmService.findById(id);
        model.addAttribute("film", f);
        model.addAttribute("regista", f.getRegista());
        model.addAttribute("festivalList", f.getFestival());
        model.addAttribute("proiezioniList", this.proiezioneService.findByFilmId(id));
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
            @Valid @ModelAttribute("recensione") Recensione recensioneForm, BindingResult bindingResult,
            Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("film", this.filmService.findById(id));
            return "recensioni/form";
        }
        try {
            this.recensioneService.creaRecensione(id, authentication.getName(), recensioneForm.getTesto(),
                    recensioneForm.getVoto());
        } catch (DuplicateElementException e) {
            model.addAttribute("film", this.filmService.findById(id));

            model.addAttribute("error", "Hai già scritto una recensione per questo film");
            return "recensioni/form";
        }

        return "redirect:/film/" + id;
    }

    @GetMapping("/film/{fId}/recensioni/{rId}/edit")
    public String getRecensioneEditForm(@PathVariable Long fId, @PathVariable Long rId, Model model,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        Recensione r = this.recensioneService.findById(rId);
        if (!this.recensioneService.isOwner(r, authentication.getName())) {
            throw new AccessDeniedException("Non sei l'autore di questa recensione.");
        }
        model.addAttribute("recensione", r);
        model.addAttribute("fId", fId);
        return "recensioni/editForm";
    }

    @PostMapping("/film/{fId}/recensioni/{rId}/edit")
    public String postRecensioneEditForm(@PathVariable Long fId,
            @PathVariable Long rId,
            @Valid @ModelAttribute("recensione") Recensione recensioneForm,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        Recensione r = this.recensioneService.findById(rId);

        if (!this.recensioneService.isOwner(r, authentication.getName())) {
            throw new AccessDeniedException("Non sei l'autore di questa recensione.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("fId", fId);
            return "recensioni/editForm";
        }

        this.recensioneService.update(rId, recensioneForm.getTesto(), recensioneForm.getVoto());
        return "redirect:/film/" + fId;
    }

    @GetMapping("/film/{fId}/recensioni/{rId}/delete")
    public String getRecensioneDelete(@PathVariable Long fId,
            @PathVariable Long rId,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        Recensione r = this.recensioneService.findById(rId);

        if (!this.recensioneService.isOwner(r, authentication.getName())) {
            throw new AccessDeniedException("Non sei l'autore di questa recensione.");
        }

        this.recensioneService.deleteById(rId);

        return "redirect:/film/" + fId;
    }

    @PostMapping("/film/{fId}/recensioni/{rId}/delete")
    public String postRecensioneDelete(@PathVariable Long fId,
            @PathVariable Long rId,
            Authentication authentication) {

        // Controlla se l'utente è effettivamente autenticato
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        Recensione r = this.recensioneService.findById(rId);

        if (!this.recensioneService.isOwner(r, authentication.getName())) {
            throw new AccessDeniedException("Non sei l'autore di questa recensione.");
        }

        this.recensioneService.deleteById(rId);
        return "redirect:/film/" + fId;
    }

    @GetMapping("/admin/film")
    public String getAdminFilmList(Model model) {
        model.addAttribute("filmList", this.filmService.findAll());
        return "admin/film/list";
    }

    @GetMapping("/admin/film/new")
    public String getFilmForm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("registiList", this.registaService.findAll());
        return "/admin/film/form";
    }

    @PostMapping("/admin/film/new")
    public String postFilmForm(@Valid @ModelAttribute Film film,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("film", film);
            model.addAttribute("registiList", this.registaService.findAll());
            return "admin/film/form";
        }
        try {
            this.filmService.save(film);
            return "redirect:/admin/film";
        } catch (DuplicateElementException e) {
            model.addAttribute("film", film);
            model.addAttribute("registiList", this.registaService.findAll());
            model.addAttribute("errore", "Un film con questo titolo ed anno esiste già.");
            return "admin/film/form";
        }
    }

    @GetMapping("/admin/film/{id}/edit")
    public String getFilmEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("film", this.filmService.findById(id));
        model.addAttribute("registiList", this.registaService.findAll());
        return "admin/film/editForm";
    }

    @PostMapping("/admin/film/{id}/edit")
    public String postFilmEditForm(@PathVariable Long id, @Valid @ModelAttribute Film filmForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("film", this.filmService.findById(id));
            model.addAttribute("registiList", this.registaService.findAll());
            return "admin/film/editForm";
        }
        try {
            this.filmService.update(id, filmForm.getTitolo(), filmForm.getAnno(), filmForm.getDurata(),
                    filmForm.getGenere(), filmForm.getRegista(), filmForm.getPaeseProduzione());

        } catch (DuplicateElementException e) {
            model.addAttribute("film", this.filmService.findById(id));
            model.addAttribute("registiList", this.registaService.findAll());
            model.addAttribute("errore", "Un film con questo titolo ed anno esiste già.");
            return "admin/film/editForm";
        }
        return "redirect:/film/" + id;
    }

}