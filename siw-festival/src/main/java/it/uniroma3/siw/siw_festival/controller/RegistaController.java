package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.model.Regista;
import it.uniroma3.siw.siw_festival.service.RegistaService;
import jakarta.validation.Valid;

@Controller
public class RegistaController {

    private final RegistaService registaService;

    public RegistaController(RegistaService registaService) {
        this.registaService = registaService;
    }

    @GetMapping("/registi/{id}")
    public String getRegistaDetail(@PathVariable Long id, Model model) {
        Regista r = this.registaService.findById(id);
        model.addAttribute("regista", r);
        model.addAttribute("filmList", r.getFilm());
        return "registi/show";
    }

    @GetMapping("/admin/registi")
    public String getAdminRegistiList(Model model) {
        model.addAttribute("registiList", this.registaService.findAll());
        return "admin/registi/list";
    }

    @GetMapping("/admin/registi/new")
    public String getRegistaForm(Model model) {
        model.addAttribute("regista", new Regista());
        return "/admin/registi/form";
    }

    @PostMapping("/admin/registi/new")
    public String postRegistaForm(@Valid @ModelAttribute Regista regista,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("regista", regista);
            return "admin/registi/form";
        }
        try {
            this.registaService.save(regista);
            return "redirect:/admin/registi";
        } catch (DuplicateElementException e) {
            model.addAttribute("regista", regista);
            return "admin/registi/form";
        }
    }

    @GetMapping("/admin/registi/{id}/edit")
    public String getRegistaEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("regista", this.registaService.findById(id));
        return "admin/registi/editForm";
    }

    @PostMapping("/admin/registi/{id}/edit")
    public String postRegistaEditForm(@PathVariable Long id, @Valid @ModelAttribute Regista registaForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regista", this.registaService.findById(id));
            return "admin/registi/editForm";
        }
        try {
            this.registaService.update(id, registaForm.getNome(), registaForm.getCognome(),
                    registaForm.getDataNascita(),
                    registaForm.getNazionalita());

        } catch (DuplicateElementException e) {
            model.addAttribute("regista", this.registaService.findById(id));
            return "admin/registi/editForm";
        }
        return "redirect:/registi/" + id;
    }

}
