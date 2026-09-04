package it.uniroma3.siw.siw_festival.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Sala;
import it.uniroma3.siw.siw_festival.repository.SalaRepository;

@Service
@Transactional
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala findById(Long id) {
        return this.salaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala non trovata"));
    }

    @Transactional(readOnly = true)
    public List<Sala> findAll() {
        List<Sala> list = new ArrayList<>();
        this.salaRepository.findAll().forEach(list::add);
        return list;
    }

    public void save(Sala sala) {
        if (this.salaRepository.existsByNomeAndIndirizzo(sala.getNome(), sala.getIndirizzo()))
            throw new DuplicateElementException(
                    "La sala " + sala.getNome() + " (" + sala.getIndirizzo() + ") è già presente nel sistema.");
        this.salaRepository.save(sala);
    }

    public Sala update(Long id, String nome, String indirizzo, Long capienza) {
        if (this.salaRepository.existsByNomeAndIndirizzo(nome, indirizzo))
            throw new DuplicateElementException(
                    "La sala " + nome + " (" + indirizzo + ") è già presente nel sistema.");
        Sala s = this.findById(id);
        s.setNome(nome);
        s.setIndirizzo(indirizzo);
        s.setCapienza(capienza);
        return this.salaRepository.save(s);
    }

}
