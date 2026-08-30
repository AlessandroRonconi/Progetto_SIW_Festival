package it.uniroma3.siw.siw_festival.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.siw_festival.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Long> {

    public boolean existsByNomeAndAnno(String nome, Long anno);

    @Query("SELECT f FROM Festival f LEFT JOIN FETCH f.film WHERE f.id = :id")
    Optional<Festival> findByIdWithFilm(@Param("id") Long id);

}
