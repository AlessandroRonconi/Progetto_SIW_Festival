package it.uniroma3.siw.siw_festival.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Regista;
import it.uniroma3.siw.siw_festival.repository.RegistaRepository;

@Service
@Transactional
public class RegistaService {

    private final RegistaRepository registaRepository;

    public RegistaService(RegistaRepository registaRepository) {
        this.registaRepository = registaRepository;
    }

    public Regista findById(Long id) {
        return this.registaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Regista non trovato"));
    }

    @Transactional(readOnly = true)
    public List<Regista> findAll() {
        List<Regista> list = new ArrayList<>();
        this.registaRepository.findAll().forEach(list::add);
        return list;
    }

    public void save(Regista regista) {
        if (this.registaRepository.existsByNomeAndCognomeAndDataNascita(regista.getNome(), regista.getCognome(),
                regista.getDataNascita()))
            throw new DuplicateElementException("Il regista " + regista.getNome() + " " + regista.getCognome() + " ("
                    + regista.getDataNascita() + ") è già presente nel sistema");
        this.registaRepository.save(regista);
    }

    public Regista update(Long id, String nome, String cognome, LocalDate dataNascita, String nazionalita) {
        Regista r = this.findById(id);
        r.setNome(nome);
        r.setCognome(cognome);
        r.setDataNascita(dataNascita);
        r.setNazionalita(nazionalita);
        return this.registaRepository.save(r);
    }

}
