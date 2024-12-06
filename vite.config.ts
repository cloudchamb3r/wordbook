import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { viteStaticCopy } from 'vite-plugin-static-copy'
import { resolve } from 'path'; 


// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    viteStaticCopy({
      targets: [
        {
          src: 'public/manifest.json', 
          dest: '.',
        }
      ]
    }),
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
        main: resolve(__dirname, 'src/popup/index.html'),
        worker: resolve(__dirname, 'src/serviceworker/main.ts'), 
      },
      output: {
        entryFileNames: '[name].js',
      },
    },
  },
});
