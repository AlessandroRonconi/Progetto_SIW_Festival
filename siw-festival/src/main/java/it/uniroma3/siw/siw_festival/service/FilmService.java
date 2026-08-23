package it.uniroma3.siw.siw_festival.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.repository.FilmRepository;

@Service
@Transactional
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film findById(Long id) {
        return this.filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Film non trovato"));
    }

}
