import { useEffect, useState } from 'react'
import reactLogo from '@/assets/react.svg'
import viteLogo from '/vite.svg'
import '@/popup/App.css'
import { useWordListStore } from '@/store/wordListStore'

function App() {
  const [count, setCount] = useState(0)
  const wlStore = useWordListStore();
  useEffect(() => {
    wlStore.sync();
  }, []);

  
  return (
    <>
      <h3>total word count : {wlStore.words.length}</h3>
      {wlStore.words.map(w => (
        <>
          <h3>{w.word}</h3> | <h4>{w.meaning}</h4>
        </>
      ))}   
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
