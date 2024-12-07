import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import '@/popup/index.css'
import App from '@/popup/App.tsx'


const port = chrome.runtime.connect({ name: "popup" });
port.postMessage({status: 'opened'});

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
