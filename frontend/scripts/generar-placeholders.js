// Genera imagenes SVG de camisetas (placeholders) con los colores de cada club.
// Se guardan en public/img y la web las sirve desde /img/...
// Ejecutar una sola vez:  node scripts/generar-placeholders.js
const fs = require('fs');
const path = require('path');

const carpeta = path.join(__dirname, '..', 'public', 'img');
fs.mkdirSync(carpeta, { recursive: true });

// Lista de camisetas: archivo, colores y numero.
const camisetas = [
  { archivo: 'rm-local.svg',        nombre: 'Real Madrid',       primario: '#ffffff', secundario: '#00529F', numero: '7',  textoOscuro: true },
  { archivo: 'rm-visitante.svg',    nombre: 'Real Madrid',       primario: '#2b2b2b', secundario: '#d4af37', numero: '7',  textoOscuro: false },
  { archivo: 'barca-local.svg',     nombre: 'FC Barcelona',      primario: '#A50044', secundario: '#004D98', numero: '10', textoOscuro: false },
  { archivo: 'barca-visitante.svg', nombre: 'FC Barcelona',      primario: '#FFCC00', secundario: '#004D98', numero: '10', textoOscuro: true },
  { archivo: 'atletico-local.svg',  nombre: 'Atletico',          primario: '#CB3524', secundario: '#ffffff', numero: '9',  textoOscuro: false },
  { archivo: 'manutd-local.svg',    nombre: 'Man. United',       primario: '#DA291C', secundario: '#FBE122', numero: '7',  textoOscuro: false },
  { archivo: 'liverpool-local.svg', nombre: 'Liverpool',         primario: '#C8102E', secundario: '#ffffff', numero: '11', textoOscuro: false },
  { archivo: 'boca-local.svg',      nombre: 'Boca Juniors',      primario: '#004F9E', secundario: '#FFD700', numero: '10', textoOscuro: false },
  { archivo: 'rm-retro.svg',        nombre: 'Real Madrid Retro', primario: '#f5f5f5', secundario: '#c0a062', numero: '5',  textoOscuro: true },
  { archivo: 'barca-retro.svg',     nombre: 'Barcelona Retro',   primario: '#A50044', secundario: '#004D98', numero: '6',  textoOscuro: false },
  { archivo: 'al-nassr.svg',        nombre: 'Al Nassr',          primario: '#FFD400', secundario: '#1a3a8f', numero: '7',  textoOscuro: true },
  { archivo: 'al-hilal.svg',        nombre: 'Al Hilal',          primario: '#1f4fbf', secundario: '#ffffff', numero: '29', textoOscuro: false }
];

// Plantilla SVG: fondo claro + camiseta con el color del club + numero + nombre.
function generarSVG(c) {
  const colorTexto = c.textoOscuro ? '#1e293b' : '#ffffff';
  const camino = 'M120,130 C140,118 160,114 200,128 C240,114 260,118 280,130 ' +
                 'L330,160 L300,220 L285,205 L285,380 Q285,392 273,392 ' +
                 'L127,392 Q115,392 115,380 L115,205 L100,220 L70,160 Z';
  return `<svg xmlns="http://www.w3.org/2000/svg" width="400" height="500" viewBox="0 0 400 500">
  <rect width="400" height="500" fill="#eef2f7"/>
  <path d="${camino}" fill="${c.primario}" stroke="#0000001a" stroke-width="2"/>
  <!-- cuello -->
  <path d="M168,124 Q200,150 232,124 L226,118 Q200,138 174,118 Z" fill="${c.secundario}"/>
  <!-- numero -->
  <text x="200" y="320" font-family="Arial, sans-serif" font-size="120" font-weight="bold"
        fill="${colorTexto}" text-anchor="middle">${c.numero}</text>
  <!-- nombre del equipo -->
  <text x="200" y="460" font-family="Arial, sans-serif" font-size="26" font-weight="bold"
        fill="#1e293b" text-anchor="middle">${c.nombre}</text>
</svg>`;
}

camisetas.forEach(c => {
  fs.writeFileSync(path.join(carpeta, c.archivo), generarSVG(c), 'utf8');
  console.log('Creada: ' + c.archivo);
});
console.log('Listo. ' + camisetas.length + ' imagenes en public/img');
