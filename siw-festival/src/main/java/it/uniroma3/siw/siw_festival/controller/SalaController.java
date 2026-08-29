package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.model.Sala;
import it.uniroma3.siw.siw_festival.service.SalaService;
import jakarta.validation.Valid;

@Controller
public class SalaController {
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping("/admin/sale")
    public String getAdminSaleList(Model model) {
        model.addAttribute("saleList", this.salaService.findAll());
        return "admin/sale/list";
    }

    @GetMapping("/admin/sale/{id}")
    public String getAdminSalaDetail(@PathVariable Long id, Model model) {
        Sala s = this.salaService.findById(id);
        model.addAttribute("sala", s);
        return "/admin/sale/show";
    }

    @GetMapping("/admin/sale/new")
    public String getSalaForm(Model model) {
        model.addAttribute("sala", new Sala());
        return "/admin/sale/form";
    }

    @PostMapping("/admin/sale/new")
    public String postSalaForm(@Valid @ModelAttribute Sala sala,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("sala", sala);
            return "admin/sale/form";
        }
        try {
            this.salaService.save(sala);
            return "redirect:/admin/sale";
        } catch (DuplicateElementException e) {
            model.addAttribute("sala", sala);
            return "admin/sale/form";
        }
    }

    @GetMapping("/admin/sale/{id}/edit")
    public String getSalaEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("sala", this.salaService.findById(id));
        return "admin/sale/editForm";
    }

    @PostMapping("/admin/sale/{id}/edit")
    public String postSalaEditForm(@PathVariable Long id, @Valid @ModelAttribute Sala salaForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sala", this.salaService.findById(id));
            return "admin/sale/editForm";
        }
        try {
            this.salaService.update(id, salaForm.getNome(), salaForm.getIndirizzo(), salaForm.getCapienza());

        } catch (DuplicateElementException e) {
            model.addAttribute("sala", this.salaService.findById(id));
            return "admin/sale/editForm";
        }
        return "redirect:/admin/sale/" + id;
    }

}