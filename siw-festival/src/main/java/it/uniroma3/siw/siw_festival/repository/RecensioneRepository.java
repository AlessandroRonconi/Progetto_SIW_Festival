package it.uniroma3.siw.siw_festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Recensione;
import it.uniroma3.siw.siw_festival.model.User;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

    public boolean existsByFilmAndUtente(Film film, User user);

}
