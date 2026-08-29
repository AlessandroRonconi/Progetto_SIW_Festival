package it.uniroma3.siw.siw_festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_festival.model.Sala;

public interface SalaRepository extends CrudRepository<Sala, Long>{

    public boolean existsByNomeAndIndirizzo(String nome, String indirizzo);

}
