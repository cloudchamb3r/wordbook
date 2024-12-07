import { createRoot } from "react-dom/client";
import { StrictMode } from "react";
import AddWordDialog from '@/contentscripts/AddWordDialog.tsx';
import { useWordBookDialogStore } from "@/store/wordBookDialogStore";
import { useWordListStore } from "@/store/wordListStore";

const initWordBookComponent = () => {
    const WORDBOOK_ID = 'wordbook__add_diagram';

    if (document.getElementById(WORDBOOK_ID)) {
        return; 
    }
    // append mount point 
    const dialog = document.createElement('div'); 
    
    dialog.id = WORDBOOK_ID; 
    dialog.style.position = 'fixed'; 
    dialog.style.top = '50%'; 
    dialog.style.left = '50%'; 
    dialog.style.transform = 'translate(-50%, -50%)';
    dialog.style.zIndex = '9999999';

    document.body.appendChild(dialog); 

    createRoot(dialog).render(
        <StrictMode>
            <AddWordDialog />
        </StrictMode>
    );
}

(function main() {
    window.addEventListener('load', () => initWordBookComponent()); 
    
    chrome.runtime.onMessage.addListener((message) => {
        const wbStore = useWordBookDialogStore.getState();
        const wlStore = useWordListStore.getState(); 
        if (message.action === "openDialog") {
            (async()=> {
                await wlStore.sync();
                wbStore.openWithSelection();
            })();
        }
    });
})();