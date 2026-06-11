import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Camiseta } from '../models/modelos';
import { PersonalizacionDialog } from './personalizacion-dialog';


// la tarjeta de una camiseta en el catalogo. el boton "Personalizar" abre el dialogo
@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, PersonalizacionDialog],
  templateUrl: './product-card.html'
})
export class ProductCard {

  // la camiseta me llega desde fuera (desde la home)
  @Input() camiseta!: Camiseta;

  // si el dialogo de personalizar esta abierto o no
  abierto = false;
}
