package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.service.FestivalService;
import jakarta.validation.Valid;

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

    @GetMapping("/admin/festival")
    public String getAdminFestivalList(Model model) {
        model.addAttribute("festivalList", this.festivalService.findAll());
        return "admin/festival/list";
    }

    @GetMapping("/admin/festival/new")
    public String getFestivalForm(Model model) {
        model.addAttribute("festival", new Festival());
        return "admin/festival/form";
    }

    @PostMapping("/admin/festival/new")
    public String postFestivalForm(@Valid @ModelAttribute Festival festival,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("festival", festival);
            return "admin/festival/form";
        }
        try {
            this.festivalService.save(festival);
            return "redirect:/admin/festival";
        } catch (DuplicateElementException e) {
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
        if (bindingResult.hasErrors()) {
            model.addAttribute("festival", festivalForm);
            return "admin/festival/editForm";
        }
        try {
            this.festivalService.update(id, festivalForm.getNome(), festivalForm.getAnno(), festivalForm.getCitta(),
                    festivalForm.getDataInizio(), festivalForm.getDataFine(), festivalForm.getDescrizione());
        } catch (DuplicateElementException e) {
            model.addAttribute("festival", festivalForm);
            return "admin/festival/form";
        }
        return "redirect:/festival/" + id;
    }

}