package it.uniroma3.siw.siw_festival.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.repository.FestivalRepository;
import it.uniroma3.siw.siw_festival.repository.FilmRepository;

@Service
@Transactional
public class FestivalService {

    private final FilmRepository filmRepository;
    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository, FilmRepository filmRepository) {
        this.festivalRepository = festivalRepository;
        this.filmRepository = filmRepository;
    }

    @Transactional(readOnly = true)
    public List<Festival> findAll() {
        List<Festival> list = new ArrayList<>();
        this.festivalRepository.findAll().forEach(list::add);
        return list;
    }

    public Festival findById(Long id) {
        return this.festivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Festival non trovato"));
    }

    public Long count() {
        return this.festivalRepository.count();
    }

    public void save(Festival festivalForm) {
        if (this.festivalRepository.existsByNomeAndAnno(festivalForm.getNome(), festivalForm.getAnno()))
            throw new DuplicateElementException(
                    "Il festival " + festivalForm.getNome() + " " + festivalForm.getAnno() + " è già presente nel sistema.");
        this.festivalRepository.save(festivalForm);
    }

    public Festival update(Long id, String nome, Long anno, String citta, LocalDate dataInizio, LocalDate dataFine,
            String descrizione) {
        Festival f = this.findById(id);
        f.setNome(nome);
        f.setAnno(anno);
        f.setCitta(citta);
        f.setDataInizio(dataInizio);
        f.setDataFine(dataFine);
        f.setDescrizione(descrizione);

        return this.festivalRepository.save(f);
    }

    public void addFilmToFestival(Long festivalId, Long filmId) {
    Festival festival = this.festivalRepository.findById(festivalId)
            .orElseThrow(() -> new NoSuchElementException("Festival non trovato"));
    Film film = this.filmRepository.findById(filmId)
            .orElseThrow(() -> new NoSuchElementException("Film non trovato"));

    if (!festival.getFilm().contains(film)) {
        festival.getFilm().add(film);
        this.festivalRepository.save(festival);
    }
}

public void removeFilmFromFestival(Long festivalId, Long filmId) {
    Festival festival = this.festivalRepository.findById(festivalId)
            .orElseThrow(() -> new NoSuchElementException("Festival non trovato"));
    Film film = this.filmRepository.findById(filmId)
            .orElseThrow(() -> new NoSuchElementException("Film non trovato"));

    festival.getFilm().remove(film);
    this.festivalRepository.save(festival);
}
}
