package it.uniroma3.siw.siw_festival.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Recensione {
    private Long id;
    private String testo;
    private Long voto;
    private LocalDate data;

    private Film film;
    private User utente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Long getVoto() {
        return voto;
    }

    public void setVoto(Long voto) {
        this.voto = voto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }
}
