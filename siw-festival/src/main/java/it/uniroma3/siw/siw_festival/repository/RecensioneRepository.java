package it.uniroma3.siw.siw_festival.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Recensione;
import it.uniroma3.siw.siw_festival.model.User;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

    public boolean existsByFilmAndUtente(Film film, User user);

    @Query("SELECT r FROM Recensione r " +
                        "JOIN FETCH r.utente u " +
                        "WHERE r.film.id = :filmId ")
        public List<Recensione> findByFilmIdWithUtente(@Param("filmId") Long filmId);

}
