package it.uniroma3.siw.siw_festival.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.siw_festival.model.Film;

public interface FilmRepository extends CrudRepository<Film, Long> {

        public boolean existsByTitoloAndAnno(String titolo, Long anno);

        @Query("SELECT f FROM Film f JOIN FETCH f.regista r WHERE " +
                        "(:titolo IS NULL OR LOWER(f.titolo) LIKE LOWER(CAST(:titolo AS string))) AND " +
                        "(:genere IS NULL OR LOWER(f.genere) = LOWER(CAST(:genere AS string))) AND " +
                        "(:cognomeRegista IS NULL OR LOWER(r.cognome) LIKE LOWER(CAST(:cognomeRegista AS string)))")
        List<Film> search(@Param("titolo") String titolo, @Param("genere") String genere,
                        @Param("cognomeRegista") String cognomeRegista);
}