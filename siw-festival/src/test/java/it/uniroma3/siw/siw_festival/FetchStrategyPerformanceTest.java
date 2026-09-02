package it.uniroma3.siw.siw_festival;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siw_festival.model.Festival;
import it.uniroma3.siw.siw_festival.model.Film;
import it.uniroma3.siw.siw_festival.model.Proiezione;
import it.uniroma3.siw.siw_festival.model.Regista;
import it.uniroma3.siw.siw_festival.model.Sala;
import it.uniroma3.siw.siw_festival.repository.FestivalRepository;
import it.uniroma3.siw.siw_festival.repository.FilmRepository;
import it.uniroma3.siw.siw_festival.repository.ProiezioneRepository;
import it.uniroma3.siw.siw_festival.repository.RegistaRepository;
import it.uniroma3.siw.siw_festival.repository.SalaRepository;

@SpringBootTest
public class FetchStrategyPerformanceTest {

    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private ProiezioneRepository proiezioneRepository;
    @Autowired
    private RegistaRepository registaRepository;
    @Autowired
    private SessionFactory sessionFactory;

    private Statistics statistics;
    private Long festivalId;

    @BeforeEach
    @Transactional
    public void setup() {
        // Abilita le statistiche di Hibernate
        statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        // Popola un dataset: 1 festival, 1 sala, 100 film con proiezioni
        Regista regista = new Regista();
        regista.setNome("Mario");
        regista.setCognome("Rossi");
        regista.setDataNascita(LocalDate.of(1970, 1, 1));
        regista.setNazionalita("Italia");
        registaRepository.save(regista);

        Sala sala = new Sala();
        sala.setNome("Sala 1");
        sala.setIndirizzo("Via Roma 1");
        sala.setCapienza(100L);
        salaRepository.save(sala);

        Festival festival = new Festival();
        festival.setNome("Test Festival");
        festival.setAnno(2026L);
        festival.setCitta("Roma");
        festival.setDataInizio(LocalDate.of(2026, 9, 1));
        festival.setDataFine(LocalDate.of(2026, 9, 10));
        festivalRepository.save(festival);
        festivalId = festival.getId();

        for (int i = 0; i < 100; i++) {
            Film film = new Film();
            film.setTitolo("Film " + i);
            film.setAnno(2026L);
            film.setDurata(100L);
            film.setGenere("Drammatico");
            film.setPaeseProduzione("Italia");
            film.setRegista(regista);
            filmRepository.save(film);

            Proiezione proiezione = new Proiezione();
            proiezione.setData(LocalDate.of(2026, 9, 5));
            proiezione.setOra(LocalTime.of(20, 0).plusMinutes(i));
            proiezione.setFestival(festival);
            proiezione.setFilm(film);
            proiezione.setSala(sala);
            proiezioneRepository.save(proiezione);
        }
    }

    @Test
    @Transactional
    public void confrontaStrategieDiFetch() {
        System.out.println("=== Test accesso alle proiezioni del festival ===\n");

        // --- Strategia 1: LAZY (naviga le associazioni nel loop -> N+1) ---
        statistics.clear();
        long start1 = System.nanoTime();

        List<Proiezione> proiezioniLazy = proiezioneRepository
                .findByFestivalIdOrderByDataAscOraAsc(festivalId);
        int count1 = 0;
        for (Proiezione p : proiezioniLazy) {
            // accesso alle associazioni lazy: scatena una query per ciascuna
            String titolo = p.getFilm().getTitolo();
            String sala = p.getSala().getNome();
            count1++;
        }

        long time1 = (System.nanoTime() - start1) / 1_000_000;
        long queries1 = statistics.getPrepareStatementCount();

        System.out.println("Strategia 1: LAZY");
        System.out.println("Proiezioni caricate: " + count1);
        System.out.println("Query SQL: " + queries1);
        System.out.println("Tempo: " + time1 + " ms\n");

        // --- Strategia 2: JOIN FETCH (un'unica query) ---
        statistics.clear();
        long start2 = System.nanoTime();

        List<Proiezione> proiezioniFetch = proiezioneRepository
                .findByFestivalIdWithFilmAndSala(festivalId);
        int count2 = 0;
        for (Proiezione p : proiezioniFetch) {
            String titolo = p.getFilm().getTitolo();
            String sala = p.getSala().getNome();
            count2++;
        }

        long time2 = (System.nanoTime() - start2) / 1_000_000;
        long queries2 = statistics.getPrepareStatementCount();

        System.out.println("Strategia 2: JOIN FETCH");
        System.out.println("Proiezioni caricate: " + count2);
        System.out.println("Query SQL: " + queries2);
        System.out.println("Tempo: " + time2 + " ms\n");
    }

    @AfterEach
    public void tearDown() {
        statistics.setStatisticsEnabled(false);
    }
}