import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../config';
import { ItemCarrito, DatosEnvio, Pedido } from '../models/modelos';


// servicio de pedidos: envia el carrito al backend y trae los pedidos del cliente
@Injectable({ providedIn: 'root' })
export class PedidosService {

  constructor(private http: HttpClient) {}


  // crea el pedido. le paso el usuario, las camisetas, el codigo de descuento y la direccion
  crear(usuarioId: number, items: ItemCarrito[], datos: DatosEnvio, codigo: string): Observable<any> {

    // del carrito solo cojo lo que necesita el backend de cada linea
    const lineas = [];

    for (const i of items) {
      lineas.push({
        camiseta_id: i.camiseta_id,
        cantidad: i.cantidad,
        talla: i.talla,
        nombre_jugador: i.nombre_jugador,
        dorsal: i.dorsal
      });
    }

    const cuerpo = { usuario_id: usuarioId, items: lineas, codigo_promo: codigo, datos_envio: datos };

    return this.http.post(API_URL + '/pedidos', cuerpo);
  }


  // los pedidos del cliente que esta logueado
  mios(usuarioId: number): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(API_URL + '/pedidos/mios?usuario_id=' + usuarioId);
  }
}
