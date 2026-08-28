package it.uniroma3.siw.siw_festival.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Regista> findAll() {
        List<Regista> list = new ArrayList<>();
        this.registaRepository.findAll().forEach(list::add);
        return list;
    }

}
