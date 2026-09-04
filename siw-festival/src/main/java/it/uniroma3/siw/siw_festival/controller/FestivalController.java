package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.model.Proiezione;
import it.uniroma3.siw.siw_festival.service.FestivalService;
import it.uniroma3.siw.siw_festival.service.FilmService;
import it.uniroma3.siw.siw_festival.service.ProiezioneService;
import it.uniroma3.siw.siw_festival.service.SalaService;
import it.uniroma3.siw.siw_festival.validator.FestivalValidator;
import jakarta.validation.Valid;

@Controller
public class FestivalController {

    private final FestivalValidator festivalValidator;
    private final ProiezioneService proiezioneService;
    private final SalaService salaService;
    private final FilmService filmService;
    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService, FilmService filmService, SalaService salaService,
            ProiezioneService proiezioneService, FestivalValidator festivalValidator) {
        this.festivalService = festivalService;
        this.filmService = filmService;
        this.salaService = salaService;
        this.proiezioneService = proiezioneService;
        this.festivalValidator = festivalValidator;
    }

    @GetMapping("/festival")
    public String getFestivalList(Model model) {
        model.addAttribute("festivalList", this.festivalService.findAll());
        return "festival/list";
    }

    @GetMapping("/festival/{id}")
    public String getFestivalDetail(@PathVariable Long id, Model model) {
        Festival f = this.festivalService.findById(id);
        model.addAttribute("festival", f);
        model.addAttribute("filmList", f.getFilm());
        model.addAttribute("proiezioniList", this.proiezioneService.findByFestivalId(id));
        return "festival/show";
    }

    @GetMapping("/admin/festival")
    public String getAdminFestivalList(Model model) {
        model.addAttribute("festivalList", this.festivalService.findAll());
        return "admin/festival/list";
    }

    @GetMapping("/admin/festival/{id}")
    public String getAdminFestivalDetail(@PathVariable Long id, Model model) {
        Festival f = this.festivalService.findById(id);
        model.addAttribute("festival", f);
        model.addAttribute("filmList", f.getFilm());
        model.addAttribute("proiezioniList", this.proiezioneService.findByFestivalId(id));
        return "admin/festival/show";
    }

    @GetMapping("/admin/festival/new")
    public String getFestivalForm(Model model) {
        model.addAttribute("festival", new Festival());
        return "admin/festival/form";
    }

    @PostMapping("/admin/festival/new")
    public String postFestivalForm(@Valid @ModelAttribute Festival festival,
            BindingResult bindingResult, Model model) {

        this.festivalValidator.validate(festival, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("festival", festival);
            return "admin/festival/form";
        }
        try {
            this.festivalService.save(festival);
            return "redirect:/admin/festival";
        } catch (DuplicateElementException e) {
            model.addAttribute("errore", "Un festival con questo nome ed anno esiste già.");
            model.addAttribute("festival", festival);
            return "admin/festival/form";
        }
    }

    @GetMapping("/admin/festival/{id}/edit")
    public String getFestivalEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("festival", this.festivalService.findById(id));
        return "admin/festival/editForm";
    }

    @PostMapping("/admin/festival/{id}/edit")
    public String postFestivalEditForm(@PathVariable Long id, @Valid @ModelAttribute Festival festivalForm,
            BindingResult bindingResult, Model model) {

        this.festivalValidator.validate(festivalForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("festival", festivalForm);
            return "admin/festival/editForm";
        }
        try {
            this.festivalService.update(id, festivalForm.getNome(), festivalForm.getAnno(), festivalForm.getCitta(),
                    festivalForm.getDataInizio(), festivalForm.getDataFine(), festivalForm.getDescrizione());
        } catch (DuplicateElementException e) {
            model.addAttribute("errore", "Un festival con questo nome ed anno esiste già.");
            model.addAttribute("festival", festivalForm);
            return "admin/festival/editForm";
        }
        return "redirect:/festival/" + id;
    }

    @GetMapping("/admin/festival/{id}/addFilm")
    public String getFestivalAddFilmForm(@PathVariable Long id, Model model) {
        Festival f = this.festivalService.findById(id);
        model.addAttribute("festival", f);
        model.addAttribute("filmList", f.getFilm()); // film già iscritti
        model.addAttribute("filmDisponibili", this.filmService.findAll()); // tutti i film per la select
        return "admin/festival/addFilmForm";
    }

    @PostMapping("/admin/festival/{id}/addFilm")
    public String postFestivalAddFilmForm(@PathVariable Long id,
            @RequestParam("filmId") Long filmId) {
        this.festivalService.addFilmToFestival(id, filmId);
        return "redirect:/admin/festival/" + id;
    }

    @GetMapping("/admin/festival/{festivalId}/film/{filmId}/delete")
    public String getRemoveFilmFromFestival(@PathVariable Long festivalId,
            @PathVariable Long filmId) {
        this.festivalService.removeFilmFromFestival(festivalId, filmId);
        return "redirect:/admin/festival/" + festivalId;
    }

    @PostMapping("/admin/festival/{festivalId}/film/{filmId}/delete")
    public String postRemoveFilmFromFestival(@PathVariable Long festivalId,
            @PathVariable Long filmId) {
        this.festivalService.removeFilmFromFestival(festivalId, filmId);
        return "redirect:/admin/festival/" + festivalId;
    }

    @GetMapping("/admin/festival/{id}/proiezioni/new")
    public String getProiezioneForm(@PathVariable Long id, Model model) {
        Festival f = this.festivalService.findById(id);
        model.addAttribute("festival", f);
        model.addAttribute("proiezione", new Proiezione());
        model.addAttribute("filmList", f.getFilm()); // solo i film iscritti a QUESTO festival
        model.addAttribute("salaList", this.salaService.findAll());
        return "admin/festival/proiezioneForm";
    }

    @PostMapping("/admin/festival/{id}/proiezioni/new")
    public String postProiezioneForm(@PathVariable Long id,
            @Valid @ModelAttribute Proiezione proiezione,
            BindingResult bindingResult, Model model) {

        Festival festival = this.festivalService.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("festival", festival);
            model.addAttribute("filmList", festival.getFilm());
            model.addAttribute("salaList", this.salaService.findAll());
            return "admin/festival/proiezioneForm";
        }

        try {
            this.proiezioneService.addProiezione(id, proiezione.getData(), proiezione.getOra(), proiezione.getFilm(),
                    proiezione.getSala());
            return "redirect:/admin/festival/" + id;
        } catch (DuplicateElementException e) {
            Festival f = this.festivalService.findById(id);
            model.addAttribute("festival", f);
            model.addAttribute("filmList", f.getFilm());
            model.addAttribute("salaList", this.salaService.findAll());
            model.addAttribute("errore", "Sala già occupata in quell'orario.");
            return "admin/festival/proiezioneForm";
        }
    }

    @GetMapping("/admin/proiezioni/{id}/edit")
    public String getProiezioneEditForm(@PathVariable Long id, Model model) {
        Proiezione p = this.proiezioneService.findById(id);
        model.addAttribute("proiezione", p);
        model.addAttribute("festival", p.getFestival());
        model.addAttribute("filmList", p.getFestival().getFilm());
        model.addAttribute("salaList", this.salaService.findAll());
        return "admin/festival/proiezioneEditForm";
    }

    @PostMapping("/admin/proiezioni/{id}/edit")
    public String postProiezioneEditForm(@PathVariable Long id,
            @Valid @ModelAttribute Proiezione proiezioneForm,
            BindingResult bindingResult, Model model) {

        Proiezione esistente = this.proiezioneService.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("festival", esistente.getFestival());
            model.addAttribute("filmList", esistente.getFestival().getFilm());
            model.addAttribute("salaList", this.salaService.findAll());
            return "admin/festival/proiezioneEditForm";
        }

        try {
            this.proiezioneService.updateProiezione(id, proiezioneForm.getData(),
                    proiezioneForm.getOra(), proiezioneForm.getFilm(), proiezioneForm.getSala());
            return "redirect:/admin/festival/" + esistente.getFestival().getId();
        } catch (DuplicateElementException e) {
            Festival f = this.festivalService.findById(id); // fetch nuovo, sessione valida
            model.addAttribute("festival", f);
            model.addAttribute("filmList", f.getFilm());
            model.addAttribute("salaList", this.salaService.findAll());
            model.addAttribute("errore", "Sala già occupata in quell'orario.");
            return "admin/festival/proiezioneForm";
        }
    }

    @PostMapping("/admin/proiezioni/{id}/delete")
    public String postDeleteProiezione(@PathVariable Long id) {
        Long festivalId = this.proiezioneService.findById(id).getFestival().getId();
        this.proiezioneService.deleteProiezione(id);
        return "redirect:/admin/festival/" + festivalId;
    }

}