package it.uniroma3.siw.siw_festival.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_festival.model.Regista;

public interface RegistaRepository extends CrudRepository<Regista, Long> {

    public boolean existsByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);

    public boolean existsByNomeAndCognomeAndDataNascitaAndIdNot(String nome, String cognome, LocalDate dataNascita,
            Long id);

}
