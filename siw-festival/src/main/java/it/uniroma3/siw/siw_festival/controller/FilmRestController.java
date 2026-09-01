package it.uniroma3.siw.siw_festival.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.siw_festival.dto.FilmDTO;
import it.uniroma3.siw.siw_festival.service.FilmService;

@RestController
@RequestMapping("/api")
public class FilmRestController {

    private final FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<FilmDTO> search(
            @RequestParam(required = false) String titolo,
            @RequestParam(required = false) String genere,
            @RequestParam(required = false) String regista) {
        return this.filmService.search(titolo, genere, regista);
    }
}