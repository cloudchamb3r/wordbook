import { useEffect } from "react";
import { getSelectionText } from "./util";

console.log('import add word dialog!!!');
function AddWordDialog() {
    useEffect(() => {
        console.log('add word dialog has been rendered!')
    }, []); 
    
    return (
        <>
            <h3>Add hi Dialog</h3> 
            <div>key: {getSelectionText()}</div>
            <div>value</div>
        </>
    );
}

export default AddWordDialog;