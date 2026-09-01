export interface FilmDTO {
  id: number;
  titolo: string;
  anno: number;
  genere: string;
  durata: number;
  paeseProduzione: string;
  registaNome: string | null;
  registaCognome: string | null;
}