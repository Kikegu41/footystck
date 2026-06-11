import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Camiseta } from '../models/modelos';
import { CarritoService, EXTRA_PERSONALIZACION } from '../services/carrito.service';


// el dialogo para personalizar la camiseta (talla, nombre, dorsal)
// la camiseta dibujada copia el color de la imagen original
@Component({
  selector: 'app-personalizacion-dialog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './personalizacion-dialog.html'
})
export class PersonalizacionDialog implements OnInit {

  @Input() camiseta!: Camiseta;
  @Output() cerrar = new EventEmitter<void>();

  tallas = ['S', 'M', 'L', 'XL', '2XL', '3XL', '4XL'];
  talla = 'M';
  nombre = '';
  numero = '';
  cantidad = 1;

  // los colores que obtengo de la imagen (vacios = colores por defecto)
  color = '';
  colorCuello = '';
  colorTexto = '';


  constructor(private carrito: CarritoService) {}


  ngOnInit(): void {

    // cargo la imagen de la camiseta y cuando este lista obtengo su color
    if (!this.camiseta.imagen_url) { return; }

    const img = new Image();
    img.onload = () => this.copiarColor(img);
    img.src = this.camiseta.imagen_url;
  }


  // dibujo la imagen en un canvas y miro que color se repite mas (ese es el de la camiseta)
  copiarColor(img: HTMLImageElement): void {
    try {

      const canvas = document.createElement('canvas');
      canvas.width = 80;
      canvas.height = 100;

      const ctx = canvas.getContext('2d')!;
      ctx.drawImage(img, 0, 0, 80, 100);
      const pixeles = ctx.getImageData(0, 0, 80, 100).data;

      // voy contando colores. salto lo transparente y el fondo gris del dibujo
      const cuenta: { [color: string]: number } = {};

      for (let i = 0; i < pixeles.length; i += 4) {
        const r = pixeles[i], g = pixeles[i + 1], b = pixeles[i + 2];

        if (pixeles[i + 3] < 200) { continue; }
        if (Math.abs(r - 238) < 14 && Math.abs(g - 242) < 14 && Math.abs(b - 247) < 14) { continue; }

        // agrupo colores parecidos para no tener demasiados colores distintos
        const clave = Math.min(255, Math.round(r / 24) * 24) + ',' +
                      Math.min(255, Math.round(g / 24) * 24) + ',' +
                      Math.min(255, Math.round(b / 24) * 24);
        cuenta[clave] = (cuenta[clave] || 0) + 1;
      }

      // me quedo con el que mas veces sale
      let dominante = '';
      let max = 0;

      for (const clave in cuenta) {
        if (cuenta[clave] > max) { max = cuenta[clave]; dominante = clave; }
      }

      if (!dominante) { return; }

      const [r, g, b] = dominante.split(',').map(Number);
      this.color = 'rgb(' + r + ',' + g + ',' + b + ')';

      // el cuello lo pongo un poco mas oscuro
      this.colorCuello = 'rgb(' + Math.round(r * 0.65) + ',' + Math.round(g * 0.65) + ',' + Math.round(b * 0.65) + ')';

      // si la camiseta es clara, pongo el texto oscuro para que se lea
      const luz = 0.299 * r + 0.587 * g + 0.114 * b;
      this.colorTexto = luz > 150 ? '#1e293b' : '#ffffff';

    } catch (e) {
      // si la imagen es de otra web no la puedo leer, asi que dejo el color por defecto
    }
  }


  // 2 € extra si pone nombre o dorsal
  extra(): number {
    return (this.nombre || this.numero) ? EXTRA_PERSONALIZACION : 0;
  }


  // el tamaño de la letra del nombre: mas pequeña cuanto mas largo, para que quepa
  tamNombre(): number {
    const texto = this.nombre || 'NOMBRE';
    return Math.max(11, Math.min(32, 240 / texto.length));
  }


  // el numero, mas pequeño si son dos cifras
  tamNumero(): number {
    return this.numero.length >= 2 ? 110 : 140;
  }


  total(): number {
    return (Number(this.camiseta.precio) + this.extra()) * this.cantidad;
  }


  cambiarCantidad(delta: number): void {
    this.cantidad = Math.max(1, this.cantidad + delta);
  }


  // añade la camiseta ya personalizada al carrito
  anadir(): void {

    this.carrito.agregar({
      camiseta_id: this.camiseta.id,
      nombre: this.camiseta.nombre,
      equipo_nombre: this.camiseta.equipo_nombre,
      imagen_url: this.camiseta.imagen_url,
      precio: Number(this.camiseta.precio),
      cantidad: this.cantidad,
      talla: this.talla,
      nombre_jugador: this.nombre,
      dorsal: this.numero
    });

    this.cerrar.emit();
  }
}
