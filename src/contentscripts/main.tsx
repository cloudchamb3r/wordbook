import { createRoot } from "react-dom/client";
import { StrictMode } from "react";
import AddWordDialog from '@/contentscripts/AddWordDialog.tsx';

chrome.runtime.onMessage.addListener((message) => {
    if (message.action === "openDialog") {
        console.log('open dialog has been called');
        createDialog();
    }
});

function createDialog() {
    console.log('create dialog has been called')
    if (document.getElementById('customDialog')) {
        console.log('already existing..');
        return; 
    }
    // append mount point 
    console.log('append mount point');
    const dialog = document.createElement('div'); 
    
    dialog.id = 'customDialog'; 
    dialog.style.position = 'fixed'; 
    dialog.style.top = '50%'; 
    dialog.style.left = '50%'; 
    dialog.style.transform = 'translate(-50%, -50%)';
    dialog.style.zIndex = '9999999';

    document.body.appendChild(dialog); 

    // render react component
    console.log('render react component');
    createRoot(dialog).render(
        <StrictMode>
            <AddWordDialog />
        </StrictMode>
    );
}
