package it.uniroma3.siw.siw_festival.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Regista;
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

    public void save(Film film) {
        if (this.filmRepository.existsByTitoloAndAnno(film.getTitolo(), film.getAnno()))
            throw new DuplicateElementException(
                    "Il film " + film.getTitolo() + " (" + film.getAnno() + ") è già presente nel sistema.");
        this.filmRepository.save(film);
    }

    @Transactional(readOnly = true)
    public List<Film> findAll() {
        List<Film> list = new ArrayList<>();
        this.filmRepository.findAll().forEach(list::add);
        return list;
    }

    public Film update(Long id, String titolo, Long anno, Long durata, String genere, Regista regista,
            String paeseProduzione) {
        Film f = this.findById(id);
        f.setTitolo(titolo);
        f.setAnno(anno);
        f.setDurata(durata);
        f.setGenere(genere);
        f.setRegista(regista);
        f.setPaeseProduzione(paeseProduzione);
        return this.filmRepository.save(f);
    }

}
