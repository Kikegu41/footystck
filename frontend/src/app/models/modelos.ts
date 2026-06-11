// aqui van las "formas" de los datos que maneja la app
// los nombres coinciden con lo que devuelve el backend (por eso van en snake_case)

export interface Liga {
  id: number;
  nombre: string;
  pais: string;
  division: number;
  codigo_api?: number | null;
  equipos?: Equipo[];
}

export interface Equipo {
  id: number;
  nombre: string;
  escudo_url: string | null;
  liga_id: number | null;
  es_otros?: boolean;
  codigo_api?: number | null;
}

export interface Camiseta {
  id: number;
  nombre: string;
  equipo_id: number | null;
  temporada: string | null;
  tipo: 'local' | 'visitante' | 'tercera';
  coleccion: 'actual' | 'retro';
  precio: number;
  stock: number;
  imagen_url: string | null;
  descripcion: string | null;
  destacado: boolean;
  equipo_nombre?: string;
  equipo_escudo?: string;
  liga_id?: number;
  liga_nombre?: string;
}

export interface Usuario {
  id: number;
  nombre: string;
  rol: 'cliente' | 'admin';
}

export interface ItemCarrito {
  camiseta_id: number;
  nombre: string;
  equipo_nombre?: string;
  imagen_url: string | null;
  precio: number;
  cantidad: number;
  talla: string;
  nombre_jugador?: string;
  dorsal?: string;
}

export interface PedidoItem {
  id: number;
  camiseta_id: number;
  camiseta_nombre?: string;
  imagen_url?: string | null;
  cantidad: number;
  precio_unit: number;
  talla?: string;
  nombre_jugador?: string;
  dorsal?: string;
}

export interface DatosEnvio {
  nombre: string;
  apellidos: string;
  pais: string;
  region: string;
  ciudad: string;
  cp: string;
  direccion: string;
  telefono: string;
}

export interface Pedido {
  id: number;
  usuario_id: number | null;
  cliente_nombre?: string;
  cliente_email?: string;
  fecha: string;
  total: number;
  codigo_promo?: string;
  descuento?: number;
  envio?: number;
  estado: string;
  envio_ciudad?: string;
  envio_direccion?: string;
  items: PedidoItem[];
}
