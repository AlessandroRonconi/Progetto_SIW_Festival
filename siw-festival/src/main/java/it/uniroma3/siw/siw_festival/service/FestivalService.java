package it.uniroma3.siw.siw_festival.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.DuplicateElementException;
import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.repository.FestivalRepository;

@Service
@Transactional
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    @Transactional(readOnly = true)
    public List<Festival> findAll() {
        List<Festival> list = new ArrayList<>();
        this.festivalRepository.findAll().forEach(list::add);
        return list;
    }

    public Festival findById(Long id) {
        return this.festivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Festival non trovato"));
    }

    public Long count() {
        return this.festivalRepository.count();
    }

    public void save(Festival festivalForm) {
        if (this.festivalRepository.existsByNomeAndAnno(festivalForm.getNome(), festivalForm.getAnno()))
            throw new DuplicateElementException(
                    "Il festival " + festivalForm.getNome() + " " + festivalForm.getAnno() + " è già presente nel sistema.");
        this.festivalRepository.save(festivalForm);
    }

    public Festival update(Long id, String nome, Long anno, String citta, LocalDate dataInizio, LocalDate dataFine,
            String descrizione) {
        Festival f = this.findById(id);
        f.setNome(nome);
        f.setAnno(anno);
        f.setCitta(citta);
        f.setDataInizio(dataInizio);
        f.setDataFine(dataFine);
        f.setDescrizione(descrizione);

        return this.festivalRepository.save(f);
    }
}
