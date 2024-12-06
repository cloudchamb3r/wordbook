import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'; 


// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
  ],
  resolve: {
    alias: [
      { find: "@", replacement: resolve(__dirname, 'src') },
    ]
  },
  build: {
    outDir: 'dist', 
    rollupOptions: {
      input: {
        content: resolve(__dirname, 'src/contentscripts/main.tsx'),
      },
      output: {
        format: 'iife', 
        entryFileNames: '[name].js',
        chunkFileNames: '[name]-[hash].js'
      },
    },
  },
});
