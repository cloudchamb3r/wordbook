import { useWordBookDialogStore } from "@/store/wordBookDialogStore";
import { useWordListStore } from "@/store/wordListStore";
import { useEffect } from "react";

function AddWordDialog() {
    const dialog = useWordBookDialogStore();    
    const wordList = useWordListStore(); 
    useEffect(() => {
        wordList.sync();
    }, []); 
    if (!dialog.show) return; 

    return (
        <div style={
            {
                display: 'flex', 
                flexDirection: 'column', 
                backgroundColor: 'white',
                // height: '300px',
                border: '1px solid black',
                padding: '1rem', 
            }
        }>
            <h3>저장된 단어들</h3>
            {
                wordList.words.map((entry) => (
                    <div>
                        {entry.word} / {entry.meaning}
                    </div>
                ))
            }
            <h3>단어 추가하기</h3>

            <div style={{ display: 'flex', padding: '12px 0'}}>
                <label htmlFor="word">WORD</label>
                <input id="word" type="text" value={dialog.word}/>
                <label htmlFor="meaning">MEANING</label>
                <input id="meaning" type="text" value={dialog.meaning} onChange={e => dialog.setMeaning(e.target.value)} />
            </div>
            
            <div style={{ display: 'flex', justifyContent: 'flex-end'}}>
                <button onClick={async () => {
                    wordList.addWord({
                        word: dialog.word, 
                        meaning: dialog.meaning,
                    });
                    dialog.close();
                }}>추가</button>
                <button onClick={() => dialog.close()}>닫기</button>
            </div>
        </div>
    )
}

export default AddWordDialog;