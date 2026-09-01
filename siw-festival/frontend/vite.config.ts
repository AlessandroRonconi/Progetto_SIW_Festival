import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  base: '/react/',
  plugins: [react()],
  build: {
    outDir: path.resolve(import.meta.dirname, "../src/main/resources/static/react"),
    emptyOutDir: true,
    rollupOptions: {
      output: {
        entryFileNames: "assets/index.js",
        chunkFileNames: "assets/[name].js",
        assetFileNames: "assets/index.[ext]",
      },
    },
  },
})
