import { Injectable } from '@angular/core';
import { ItemCarrito } from '../models/modelos';


// lo que cuesta personalizar una camiseta (ponerle nombre o dorsal)
export const EXTRA_PERSONALIZACION = 2;


// el carrito: guardo las camisetas en un array y tambien en el navegador
@Injectable({ providedIn: 'root' })
export class CarritoService {

  items: ItemCarrito[] = [];


  constructor() {

    // recupero lo que hubiera en el carrito del navegador
    const guardado = localStorage.getItem('footystck_carrito');

    if (guardado) {
      this.items = JSON.parse(guardado);
    }
  }


  // añado una camiseta al carrito
  agregar(item: ItemCarrito): void {
    this.items.push(item);
    this.guardar();
  }


  // quito una linea por su posicion
  eliminar(indice: number): void {
    this.items.splice(indice, 1);
    this.guardar();
  }


  // vaciar el carrito entero
  vaciar(): void {
    this.items = [];
    this.guardar();
  }


  // mira si la camiseta lleva nombre o dorsal
  estaPersonalizada(item: ItemCarrito): boolean {
    return Boolean(item.nombre_jugador || item.dorsal);
  }


  // precio de una unidad (le sumo el extra si esta personalizada)
  precioUnidad(item: ItemCarrito): number {
    return Number(item.precio) + (this.estaPersonalizada(item) ? EXTRA_PERSONALIZACION : 0);
  }


  // sumo lo que vale todo el carrito
  total(): number {
    let suma = 0;

    for (const item of this.items) {
      suma = suma + this.precioUnidad(item) * item.cantidad;
    }

    return suma;
  }


  // cuantos articulos hay en total (contando cantidades)
  cantidadTotal(): number {
    let n = 0;

    for (const item of this.items) {
      n = n + item.cantidad;
    }

    return n;
  }


  // guardo el carrito en el navegador
  guardar(): void {
    localStorage.setItem('footystck_carrito', JSON.stringify(this.items));
  }
}
