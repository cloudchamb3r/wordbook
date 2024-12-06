chrome.runtime.onInstalled.addListener(() => {
    chrome.contextMenus.create({
        id: 'customMenu', 
        title: 'Open Dialog', 
        contexts: ['all'],
    });
});

chrome.contextMenus.onClicked.addListener((info, tab) => {
    if (info.menuItemId === 'customMenu') {
        if (!tab) return; 
        chrome.tabs.sendMessage(tab.id!!, { action: 'openDialog' });
    }
});