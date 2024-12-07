import { create } from 'zustand';
import { z } from 'zod'; 

const WORDBOOK_STORAGE_ID = '__wordbook_saved_words';

/// schema definition 
const WordEntrySchema = z.object({
    word: z.string(), 
    meaning: z.string(), 
});

/// type definition 
export type WordEntry = z.infer<typeof WordEntrySchema>;

/// validator function 
const isWordEntry = (obj: unknown): obj is WordEntry => WordEntrySchema.safeParse(obj).success;
const isWordEntryArray = (obj: unknown): obj is WordEntry[] => (
    Array.isArray(obj) && obj.every(item => isWordEntry(item))
);

export interface WordListState { 
    words: WordEntry[], 
    addWord: (entry: WordEntry) => void, 
    removeWord: (entry: WordEntry) => void, 
    sync: () => Promise<void>,
}

export const useWordListStore = create<WordListState>()(
    (set, get) => ({
        words: [], 
        addWord: (entry) => { 
            const addedWords = [...get().words, entry]; 
            chrome.storage.local.set({ [WORDBOOK_STORAGE_ID]: addedWords }); 
            set({words: addedWords});
        }, 
        removeWord: (entry) =>  {
            const filteredWords = get().words.filter(item => item != entry);
            chrome.storage.local.set({ [WORDBOOK_STORAGE_ID]: filteredWords }); 
            set({words: filteredWords})
        },
        sync: async () => {
            const savedWords = (await chrome.storage.local.get([WORDBOOK_STORAGE_ID]))[WORDBOOK_STORAGE_ID];
            if (isWordEntryArray(savedWords)) {
                set({words: savedWords});
            }
        }
    })
)