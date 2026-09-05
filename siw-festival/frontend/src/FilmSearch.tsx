import { useState, useEffect } from "react";
import type { FilmDTO } from "./FilmDTO";

function FilmSearch() {
    const [titolo, setTitolo] = useState<string>("");
    const [genere, setGenere] = useState<string>("");
    const [regista, setRegista] = useState<string>("");
    const [film, setFilm] = useState<FilmDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [searched, setSearched] = useState<boolean>(false);

    const cercaFilm = async (): Promise<void> => {
        setLoading(true);
        setError(null);
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
            setError("Non è stato possibile completare la ricerca. Riprova più tardi.");
            setFilm([]);
        } finally {
            setLoading(false);
            setSearched(true);
        }
    };

    useEffect(() => {
        cercaFilm();
    }, []);

    const handleReset = (): void => {
        setTitolo("");
        setGenere("");
        setRegista("");
    };

    return (
        <div>
            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    cercaFilm();
                }}
                className="form-container mb-3"
                style={{ maxWidth: "100%" }}
            >
                <div className="grid grid-3 mb-2">
                    <div className="form-group" style={{ marginBottom: 0 }}>
                        <label htmlFor="titolo">Titolo</label>
                        <input
                            id="titolo"
                            className="form-control"
                            placeholder="Es. Inception"
                            value={titolo}
                            onChange={(e) => setTitolo(e.target.value)}
                        />
                    </div>
                    <div className="form-group" style={{ marginBottom: 0 }}>
                        <label htmlFor="genere">Genere</label>
                        <input
                            id="genere"
                            className="form-control"
                            placeholder="Es. Drammatico"
                            value={genere}
                            onChange={(e) => setGenere(e.target.value)}
                        />
                    </div>
                    <div className="form-group" style={{ marginBottom: 0 }}>
                        <label htmlFor="regista">Regista (cognome)</label>
                        <input
                            id="regista"
                            className="form-control"
                            placeholder="Es. Nolan"
                            value={regista}
                            onChange={(e) => setRegista(e.target.value)}
                        />
                    </div>
                </div>
                <div className="form-actions" style={{ justifyContent: "flex-start" }}>
                    <button type="submit" className="btn btn-primary" disabled={loading}>
                        {loading ? "Ricerca..." : "Cerca"}
                    </button>
                    <button type="button" className="btn btn-outline" onClick={handleReset} disabled={loading}>
                        Reimposta
                    </button>
                </div>
            </form>

            {error && <div className="alert alert-danger">{error}</div>}

            {loading ? (
                <p className="text-muted">Caricamento...</p>
            ) : searched && film.length === 0 ? (
                <p className="text-muted">Nessun film trovato con questi criteri.</p>
            ) : (
                <div className="table-wrapper">
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
                                    <td>
                                        <a href={`/film/${f.id}`}>{f.titolo}</a>
                                    </td>
                                    <td>{f.anno}</td>
                                    <td><span className="badge badge-outline">{f.genere}</span></td>
                                    <td>{f.registaNome} {f.registaCognome}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

export default FilmSearch;