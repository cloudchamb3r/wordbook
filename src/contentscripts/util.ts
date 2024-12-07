export function getSelectionText(): string {
    const activeEl = document.activeElement; 
    const isTextAreaElement = activeEl instanceof HTMLTextAreaElement;
    const isNumberInputElement = activeEl instanceof HTMLInputElement && 
        /^(?:text|search|password|tel|url)$/i.test(activeEl.type) && 
        typeof activeEl.selectionStart === 'number';
    return (isTextAreaElement || isNumberInputElement)
        ? activeEl.value.slice(activeEl.selectionStart!!, activeEl.selectionEnd!!)
        : window.getSelection()?.toString() ?? "";
}