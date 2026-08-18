package it.uniroma3.siw.siw_festival.model;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Sala {
    private Long id;
    private String nome;
    private String indirizzo;
    private Long capienza;

    private List<Proiezione> proiezioni;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Long getCapienza() {
        return capienza;
    }

    public void setCapienza(Long capienza) {
        this.capienza = capienza;
    }

    public List<Proiezione> getProiezioni() {
        return proiezioni;
    }

    public void setProiezioni(List<Proiezione> proiezioni) {
        this.proiezioni = proiezioni;
    }
}
