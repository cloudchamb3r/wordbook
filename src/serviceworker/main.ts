import { useWordListStore } from "@/store/wordListStore";

chrome.runtime.onInstalled.addListener(() => {
    chrome.contextMenus.create({
        id: 'customMenu', 
        title: '단어장에 추가하기', 
        contexts: ['all'],
    });
});

chrome.contextMenus.onClicked.addListener((info, tab) => {
    if (info.menuItemId === 'customMenu') {
        if (!tab) return; 
        chrome.tabs.sendMessage(tab.id!!, { action: 'openDialog' });
    }
});

chrome.runtime.onConnect.addListener((port) => {
    if (port.name === "popup") {
        console.log("Popup opened");
        
        (async()=>{
            await useWordListStore.getState().sync();
            console.log('words', useWordListStore.getState().words);
        })();

        port.onDisconnect.addListener(()=>{
            console.log('Popup closed');
        });
    }
});