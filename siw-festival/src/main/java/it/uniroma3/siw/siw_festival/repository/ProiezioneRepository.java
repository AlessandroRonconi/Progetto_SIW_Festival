package it.uniroma3.siw.siw_festival.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Proiezione;
import it.uniroma3.siw.siw_festival.model.Sala;

public interface ProiezioneRepository extends CrudRepository<Proiezione, Long> {

    boolean existsBySalaAndDataAndOra(Sala sala, LocalDate data, LocalTime ora);

    void deleteByFestivalAndFilm(Festival festival, Film film);

}
