package it.uniroma3.siw.siw_festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.siw_festival.service.FestivalService;

@Controller
public class HomeController {

    private final FestivalService festivalService;

    public HomeController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @GetMapping("/")
    public String getHome() {
        return "index.html";
    }

    @GetMapping("/admin")
    public String getAdminConsole(Model model) {
        model.addAttribute("numeroFestival", this.festivalService.count());
        return "admin/index";
    }

}
