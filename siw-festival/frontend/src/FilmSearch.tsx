import { useState, useEffect, type SubmitEvent } from "react";
import type { FilmDTO } from "./FilmDTO";

function FilmSearch() {
    const [titolo, setTitolo] = useState<string>("");
    const [genere, setGenere] = useState<string>("");
    const [regista, setRegista] = useState<string>("");
    const [film, setFilm] = useState<FilmDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    const cercaFilm = async (): Promise<void> => {
        setLoading(true);
        const params = new URLSearchParams();
        if (titolo) params.append("titolo", titolo);
        if (genere) params.append("genere", genere);
        if (regista) params.append("regista", regista);

        try {
            const res = await fetch(`/api/films?${params.toString()}`);
            if (!res.ok) throw new Error(`Errore HTTP ${res.status}`);
            const data: FilmDTO[] = await res.json();
            setFilm(data);
        } catch (err) {
            console.error("Errore nella ricerca dei film:", err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        cercaFilm();
    }, []);

    const handleSubmit = (e: SubmitEvent<HTMLFormElement>): void => {
        e.preventDefault();
        cercaFilm();
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input
                    placeholder="Titolo"
                    value={titolo}
                    onChange={(e) => setTitolo(e.target.value)}
                />
                <input
                    placeholder="Genere"
                    value={genere}
                    onChange={(e) => setGenere(e.target.value)}
                />
                <input
                    placeholder="Regista (cognome)"
                    value={regista}
                    onChange={(e) => setRegista(e.target.value)}
                />
                <button type="submit">Cerca</button>
            </form>

            {loading ? (
                <p>Caricamento...</p>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th>Titolo</th>
                            <th>Anno</th>
                            <th>Genere</th>
                            <th>Regista</th>
                        </tr>
                    </thead>
                    <tbody>
                        {film.map((f) => (
                            <tr key={f.id}>
                                <td>{f.titolo}</td>
                                <td>{f.anno}</td>
                                <td>{f.genere}</td>
                                <td>{f.registaNome} {f.registaCognome}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default FilmSearch;