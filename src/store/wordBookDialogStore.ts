import { getSelectionText } from '@/contentscripts/util';
import { create } from 'zustand'; 

export interface WordBookDialogState {
    show: boolean, 
    word: string, 
    meaning: string, 
    open: (word: string, meaning: string) => void, 
    openWithSelection: (meaning?: string) => void, 
    close: () => void, 
    setMeaning: (meaning: string) => void,
}

export const useWordBookDialogStore = create<WordBookDialogState>()(
    (set) => ({
        show: false, 
        word: '', 
        meaning: '', 
        open: (word, meaning) => set({ show: true, word, meaning }),
        openWithSelection: (meaning = "") => set({
            show: true, 
            word: getSelectionText().toLowerCase(), 
            meaning,
        }),
        close: () => set({ show: false }),
        setMeaning: (meaning) => set({meaning}),
    })
)
