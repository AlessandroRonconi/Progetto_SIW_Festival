package it.uniroma3.siw.siw_festival.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Proiezione;
import it.uniroma3.siw.siw_festival.model.Sala;

public interface ProiezioneRepository extends CrudRepository<Proiezione, Long> {

        public boolean existsBySalaAndDataAndOra(Sala sala, LocalDate data, LocalTime ora);

        public void deleteByFestivalAndFilm(Festival festival, Film film);

        @Query("SELECT p FROM Proiezione p " +
                        "JOIN FETCH p.sala s " +
                        "JOIN FETCH p.festival fe " +
                        "WHERE p.film.id = :filmId " +
                        "ORDER BY p.data ASC, p.ora ASC")
        public List<Proiezione> findByFilmIdWithSalaAndFestival(@Param("filmId") Long filmId);

        @Query("SELECT p FROM Proiezione p " +
                        "JOIN FETCH p.film f " +
                        "JOIN FETCH p.sala s " +
                        "WHERE p.festival.id = :festivalId " +
                        "ORDER BY p.data ASC, p.ora ASC")
        public List<Proiezione> findByFestivalIdWithFilmAndSala(@Param("festivalId") Long festivalId);

        public List<Proiezione> findByFestivalIdOrderByDataAscOraAsc(Long festivalId);

}
