package it.uniroma3.siw.siw_festival.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.exception.ResourceNotFoundException;
import it.uniroma3.siw.siw_festival.model.Credentials;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Recensione;
import it.uniroma3.siw.siw_festival.model.User;
import it.uniroma3.siw.siw_festival.repository.CredentialsRepository;
import it.uniroma3.siw.siw_festival.repository.FilmRepository;
import it.uniroma3.siw.siw_festival.repository.RecensioneRepository;

@Service
@Transactional
public class RecensioneService {

    private final RecensioneRepository recensioneRepository;
    private final CredentialsRepository credentialsRepository;
    private final FilmRepository filmRepository;

    public RecensioneService(FilmRepository filmRepository, CredentialsRepository credentialsRepository,
            RecensioneRepository recensioneRepository) {
        this.filmRepository = filmRepository;
        this.credentialsRepository = credentialsRepository;
        this.recensioneRepository = recensioneRepository;
    }

    public Recensione creaRecensione(Long id, String username, String testo, Long voto) {
        Film film = this.filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film non trovato"));
        Credentials credentials = this.credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
        Recensione recensione = new Recensione();
        User user = credentials.getUser();
        recensione.setUtente(user);
        recensione.setTesto(testo);
        recensione.setFilm(film);
        recensione.setData(LocalDate.now());
        recensione.setVoto(voto);
        return this.recensioneRepository.save(recensione);
    }

    public Recensione findById(Long rId) {
        return this.recensioneRepository.findById(rId)
                .orElseThrow(() -> new ResourceNotFoundException("Recensione non trovata"));
    }

    public boolean isOwner(Recensione r, String username) {
        if (r == null || r.getUtente() == null || r.getUtente().getCredentials() == null) {
            return false;
        }
        return r.getUtente().getCredentials().getUsername().equals(username);
    }

    public Recensione updateRecensione(Long rId, String nuovoTesto, Long nuovoVoto) {
        Recensione recensione = this.findById(rId);
        recensione.setTesto(nuovoTesto);
        recensione.setVoto(nuovoVoto);
        return this.recensioneRepository.save(recensione);
    }

    public void deleteById(Long rId) {
        this.recensioneRepository.deleteById(rId);
    }

}
