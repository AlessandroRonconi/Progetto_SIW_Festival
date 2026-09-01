package it.uniroma3.siw.siw_festival.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Proiezione;
import it.uniroma3.siw.siw_festival.model.Sala;
import it.uniroma3.siw.siw_festival.repository.FestivalRepository;
import it.uniroma3.siw.siw_festival.repository.ProiezioneRepository;

@Service
@Transactional
public class ProiezioneService {
    private final FestivalRepository festivalRepository;
    private final ProiezioneRepository proiezioneRepository;

    public ProiezioneService(ProiezioneRepository proiezioneRepository, FestivalRepository festivalRepository) {
        this.proiezioneRepository = proiezioneRepository;
        this.festivalRepository = festivalRepository;
    }

    public void addProiezione(Long id, LocalDate data, LocalTime ora, Film film, Sala sala) {
        Festival f = this.festivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Festival non trovato"));
        if (this.proiezioneRepository.existsBySalaAndDataAndOra(sala, data, ora)) {
            throw new DuplicateElementException("Sala già occupata in quell'orario");
        }
        Proiezione p = new Proiezione();
        p.setFestival(f);
        p.setData(data);
        p.setOra(ora);
        p.setSala(sala);
        p.setFilm(film);
        this.proiezioneRepository.save(p);
    }

    public Proiezione findById(Long id) {
        return this.proiezioneRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Proiezione non trovata"));
    }

    @Transactional
    public void updateProiezione(Long id, LocalDate data, LocalTime ora, Film film, Sala sala) {
        Proiezione p = this.proiezioneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proiezione non trovata"));

        p.setData(data);
        p.setOra(ora);
        p.setFilm(film);
        p.setSala(sala);

        this.proiezioneRepository.save(p); // Salva le modifiche sul database
    }

    public void deleteProiezione(Long id) {
        Proiezione p = this.findById(id);
        this.proiezioneRepository.delete(p);
    }

    public List<Proiezione> findByFilmId(Long id) {
        return this.proiezioneRepository.findByFilmIdWithSalaAndFestival(id);
    }

    public List<Proiezione> findByFestivalId(Long id) {
        return this.proiezioneRepository.findByFestivalIdWithFilmAndSala(id);
    }
}
