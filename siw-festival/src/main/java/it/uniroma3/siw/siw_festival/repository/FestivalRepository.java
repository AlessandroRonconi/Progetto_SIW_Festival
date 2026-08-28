package it.uniroma3.siw.siw_festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_festival.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Long> {

    public boolean existsByNomeAndAnno(String nome, Long anno);

}
