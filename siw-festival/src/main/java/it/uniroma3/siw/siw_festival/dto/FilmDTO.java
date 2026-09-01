package it.uniroma3.siw.siw_festival.dto;

public class FilmDTO {
    private Long id;
    private String titolo;
    private Long anno;
    private String genere;
    private Long durata;
    private String paeseProduzione;
    private String registaNome;
    private String registaCognome;

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