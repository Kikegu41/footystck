import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CarritoService } from '../services/carrito.service';
import { AuthService } from '../services/auth.service';
import { UiService } from '../services/ui.service';


// la barra de arriba: logo, menu, carrito y la sesion
// no tiene logica, solo recibe los servicios para usarlos en la plantilla
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './header.html'
})
export class Header {

  constructor(
    public carrito: CarritoService,
    public auth: AuthService,
    public ui: UiService
  ) {}
}
