-- USER
insert into users(id, name, surname, email) values(nextval('users_seq'), 'Mario', 'Rossi', 'mario.rossi@example.com');
insert into users(id, name, surname, email) values(nextval('users_seq'), 'Giorgia', 'Verdi', 'giorgia.verdi@example.com');
insert into users(id, name, surname, email) values(nextval('users_seq'), 'Alessandro', 'Ronconi', 'admin.siwf@example.com');

-- CREDENTIALS
insert into credentials(id, username, password, role, user_id) values(nextval('credentials_seq'), 'MR2', '$2a$12$n7v3o3M6J5GojKUqLZVtf.N8alw9ZuVucMepqAAzRYT7ewg37tzRe', 'USER', 1)
-- la password sopra è sus
insert into credentials(id, username, password, role, user_id) values(nextval('credentials_seq'), 'giogio', '$2a$12$Veifq.6mjwKOu4YP4PxE/OTlFXX/H8fDYC8QoVZuc4BKQ0Zsn1iW6', 'USER', 51)
-- la password sopra è pollo
insert into credentials(id, username, password, role, user_id) values(nextval('credentials_seq'), 'admin', '$2a$12$ol5BiZqmA7bbO6yGoC2Zee8firB8jvM..h.ksZCOrO1ebO2nhdQdq', 'ADMIN', 101)
-- la password sopra è admin

-- FESTIVAL
insert into festival(id, nome, anno, citta, data_inizio, data_fine, descrizione) values(nextval('festival_seq'), 'Monte Mario Sci-Fi Festival', 2026, 'Roma', '2026-08-01', '2026-08-08', 'Festival di film di fantascienza nel XIV municipio.');
insert into festival(id, nome, anno, citta, data_inizio, data_fine, descrizione) values(nextval('festival_seq'), 'Monte Mario Sci-Fi Festival', 2025, 'Roma', '2025-07-06', '2025-07-13', 'Festival di film di fantascienza nel XIV municipio.');
insert into festival(id, nome, anno, citta, data_inizio, data_fine, descrizione) values(nextval('festival_seq'), 'SBTCinema', 2025, 'San Benedetto del Tronto', '2025-06-05', '2025-06-12', 'Festival di film italiani.');

-- REGISTA
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Christopher', 'Nolan', '1970-07-30', 'UK');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Denis', 'Villeneuve', '1967-10-03', 'Canada');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Greta', 'Gerwig', '1983-08-04', 'USA');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Bong', 'Joon-ho', '1969-09-14', 'Corea del Sud');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Roberto', 'Benigni', '1952-10-27', 'Italia');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Paolo', 'Sorrentino', '1970-05-31', 'Italia');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Nanni', 'Moretti', '1953-08-19', 'Italia');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Matteo', 'Garrone', '1968-10-15', 'Italia');
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Paolo', 'Genovese', '1966-11-06', 'Italia');

-- FILM (ManyToOne verso Regista -> colonna regista_id)
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Inception', 2010, 148, 'Fantascienza', 'UK/USA', 1);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Dune', 2021, 155, 'Fantascienza', 'USA/Canada', 51);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Barbie', 2023, 114, 'Commedia', 'USA', 101);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Parasite', 2019, 132, 'Thriller', 'Corea del Sud', 151);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Interstellar', 2014, 169, 'Fantascienza', 'UK/USA', 1);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'La vita è bella', 1997, 116, 'Commedia/Drammatico', 'Italia', 201);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'La grande bellezza', 2013, 142, 'Drammatico', 'Italia', 251);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Caro diario', 1993, 100, 'Commedia', 'Italia', 301);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Gomorra', 2008, 135, 'Drammatico', 'Italia', 351);
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Perfetti sconosciuti', 2016, 97, 'Commedia', 'Italia', 401);

-- RELAZIONE ManyToMany Festival <-> Film (tabella di join festival_film)
insert into festival_film(festival_id, film_id) values(1, 1);   -- Monte Mario Sci-Fi 2026 - Inception
insert into festival_film(festival_id, film_id) values(1, 201); -- Monte Mario Sci-Fi 2026 - Interstellar
insert into festival_film(festival_id, film_id) values(51, 51); -- Monte Mario Sci-Fi 2025 - Dune
insert into festival_film(festival_id, film_id) values(101, 251); -- SBTCinema - La vita è bella
insert into festival_film(festival_id, film_id) values(101, 301); -- SBTCinema - La grande bellezza
insert into festival_film(festival_id, film_id) values(101, 351); -- SBTCinema - Caro diario
insert into festival_film(festival_id, film_id) values(101, 401); -- SBTCinema - Gomorra
insert into festival_film(festival_id, film_id) values(101, 451); -- SBTCinema - Perfetti sconosciuti

-- SALA
insert into sala(id, nome, indirizzo, capienza) values(nextval('sala_seq'), 'The Screen', 'Via della Lucchina 90, Roma', 200);
insert into sala(id, nome, indirizzo, capienza) values(nextval('sala_seq'), 'PalaRiviera', 'Piazzale Aldo Moro 1, San Benedetto del Tronto', 150);
insert into sala(id, nome, indirizzo, capienza) values(nextval('sala_seq'), 'Andromeda', 'Via Mattia Battistini, 195, Roma', 300);

-- PROIEZIONE (ManyToOne verso Festival, Film, Sala)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2026-08-02', '20:00:00', 1, 1, 101);   -- Inception @ Andromeda (Roma)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2026-08-05', '21:00:00', 1, 201, 1);   -- Interstellar @ The Screen (Roma)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2025-07-08', '20:30:00', 51, 51, 1);   -- Dune @ The Screen (Roma)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2025-06-06', '18:00:00', 101, 251, 51); -- La vita è bella @ PalaRiviera (San Benedetto)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2025-06-07', '21:00:00', 101, 301, 51); -- La grande bellezza @ PalaRiviera (San Benedetto)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2025-06-08', '19:00:00', 101, 351, 51); -- Caro diario @ PalaRiviera (San Benedetto)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2025-06-09', '21:30:00', 101, 401, 51); -- Gomorra @ PalaRiviera (San Benedetto)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2025-06-10', '20:00:00', 101, 451, 51); -- Perfetti sconosciuti @ PalaRiviera (San Benedetto)

-- RECENSIONE (ManyToOne verso Film e User)
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Un capolavoro di fantascienza intelligente.', 9, '2026-08-03', 1, 1);
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Visivamente straordinario, un''esperienza da vedere al cinema.', 9, '2026-08-04', 51, 1);   -- Dune, Mario
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Ottimo film ma forse un po'' lungo.', 8, '2025-07-09', 51, 51);                          -- Dune, Giorgia
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Leggero e divertente, sorprendentemente profondo.', 8, '2025-06-08', 101, 101);           -- Barbie, admin
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Una sceneggiatura perfetta dall''inizio alla fine.', 10, '2025-06-10', 151, 1);            -- Parasite, Mario
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Uno dei migliori film di fantascienza mai fatti.', 9, '2026-08-05', 201, 51);             -- Interstellar, Giorgia
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Capolavoro assoluto, dramma e comicità si fondono magistralmente.', 10, '2025-06-07', 251, 1);   -- La vita è bella, Mario
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Immagini di rara bellezza, un ritratto della Roma decadente.', 9, '2025-06-08', 301, 51);        -- La grande bellezza, Giorgia
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Delicato e ironico, un viaggio personale che colpisce.', 8, '2025-06-09', 351, 101);            -- Caro diario, admin
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Crudo e realistico, racconta la Napoli della camorra senza filtri.', 9, '2025-06-10', 401, 1);   -- Gomorra, Mario
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Una serata che diventa uno specchio impietoso delle nostre vite digitali.', 8, '2025-06-11', 451, 51); -- Perfetti sconosciuti, Giorgia