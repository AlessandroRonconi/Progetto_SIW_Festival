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
insert into festival(id, nome, anno, citta, data_inizio, data_fine, descrizione) values(nextval('festival_seq'), 'Ottavia Sci-Fi Festival', 2026, 'Roma', '2026-08-01', '2026-08-08', 'Festival di film di fantascienza.');
insert into festival(id, nome, anno, citta, data_inizio, data_fine, descrizione) values(nextval('festival_seq'), 'Ottavia Sci-Fi Festival', 2025, 'Roma', '2025-07-06', '2025-07-13', 'Festival di film di fantascienza.');
insert into festival(id, nome, anno, citta, data_inizio, data_fine, descrizione) values(nextval('festival_seq'), 'SBTCinema', 2025, 'San Benedetto del Tronto', '2025-06-05', '2025-06-12', 'Cinema all''aperto con una selezione di grandi classici.');

-- REGISTA
insert into regista(id, nome, cognome, data_nascita, nazionalita) values(nextval('regista_seq'), 'Christopher', 'Nolan', '1970-07-30', 'UK');

-- FILM (ManyToOne verso Regista -> colonna regista_id)
insert into film(id, titolo, anno, durata, genere, paese_produzione, regista_id) values(nextval('film_seq'), 'Inception', 2010, 148, 'Fantascienza', 'UK/USA', 1);

-- RELAZIONE ManyToMany Festival <-> Film (tabella di join festival_film)
insert into festival_film(festival_id, film_id) values(1, 1);

-- SALA
insert into sala(id, nome, indirizzo, capienza) values(nextval('sala_seq'), 'Sala Nolan', 'Via Roma 1, Roma', 200);

-- PROIEZIONE (ManyToOne verso Festival, Film, Sala)
insert into proiezione(id, data, ora, festival_id, film_id, sala_id) values(nextval('proiezione_seq'), '2026-08-02', '21:00:00', 1, 1, 1);

-- RECENSIONE (ManyToOne verso Film e User)
insert into recensione(id, testo, voto, data, film_id, utente_id) values(nextval('recensione_seq'), 'Un capolavoro di fantascienza intelligente.', 5, '2026-08-03', 1, 1);