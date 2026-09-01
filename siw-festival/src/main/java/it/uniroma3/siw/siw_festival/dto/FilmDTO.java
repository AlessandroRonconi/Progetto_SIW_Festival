package it.uniroma3.siw.siw_festival.dto;

public class FilmDTO {
    private final Long id;
    private final String titolo;
    private final Long anno;
    private final String genere;
    private final Long durata;
    private final String paeseProduzione;
    private final String registaNome;
    private final String registaCognome;

    public FilmDTO(Long id, String titolo, Long anno, String genere, Long durata,
            String paeseProduzione, String registaNome, String registaCognome) {
        this.id = id;
        this.titolo = titolo;
        this.anno = anno;
        this.genere = genere;
        this.durata = durata;
        this.paeseProduzione = paeseProduzione;
        this.registaNome = registaNome;
        this.registaCognome = registaCognome;
    }

    public Long getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public Long getAnno() {
        return anno;
    }

    public String getGenere() {
        return genere;
    }

    public Long getDurata() {
        return durata;
    }

    public String getPaeseProduzione() {
        return paeseProduzione;
    }

    public String getRegistaNome() {
        return registaNome;
    }

    public String getRegistaCognome() {
        return registaCognome;
    }
}