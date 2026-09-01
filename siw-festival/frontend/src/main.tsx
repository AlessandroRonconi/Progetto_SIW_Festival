import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import FilmSearch from './FilmSearch.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <FilmSearch />
  </StrictMode>,
)
