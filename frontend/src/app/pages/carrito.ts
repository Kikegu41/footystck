import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CarritoService } from '../services/carrito.service';


// la primera pantalla del carrito. el pago de verdad se hace en /finalizar
@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './carrito.html'
})
export class Carrito {

  constructor(public carrito: CarritoService, private router: Router) {}


  // pasa a la pantalla de finalizar (datos de envio + desglose)
  proceder(): void {
    this.router.navigate(['/finalizar']);
  }
}
