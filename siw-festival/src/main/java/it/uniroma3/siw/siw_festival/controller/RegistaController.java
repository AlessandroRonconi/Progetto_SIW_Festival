package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.siw_festival.model.Regista;
import it.uniroma3.siw.siw_festival.service.RegistaService;

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

}
