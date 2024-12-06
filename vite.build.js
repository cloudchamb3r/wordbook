import { build } from 'vite'; 

async function runBuilds() {
    Promise.all([
        build({configFile: 'vite.config.ts'}), 
        build({configFile: 'vite.iife.config.ts'}),
    ]);
}

runBuilds().catch(err => {
    console.error(err); 
    process.exit(1); 
})