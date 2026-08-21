package it.uniroma3.siw.siw_festival.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return this.festivalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Festival non trovato"));
    }
}
