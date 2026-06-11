import { Injectable } from '@angular/core';


// cosas de la interfaz: si el menu lateral esta abierto y el tema (claro/oscuro)
@Injectable({ providedIn: 'root' })
export class UiService {

  menuAbierto = false;
  oscuro = false;


  constructor() {

    // al arrancar miro si dejo el tema oscuro guardado y lo pongo
    this.oscuro = localStorage.getItem('footystck_tema') === 'oscuro';
    this.aplicarTema();
  }


  abrir(): void { this.menuAbierto = true; }

  cerrar(): void { this.menuAbierto = false; }

  alternar(): void { this.menuAbierto = !this.menuAbierto; }


  // cambia de claro a oscuro y al reves, y lo guarda
  alternarTema(): void {
    this.oscuro = !this.oscuro;
    localStorage.setItem('footystck_tema', this.oscuro ? 'oscuro' : 'claro');
    this.aplicarTema();
  }


  // pone o quita la clase 'oscuro' del body, que es lo que cambia los colores
  aplicarTema(): void {
    if (this.oscuro) {
      document.body.classList.add('oscuro');
    } else {
      document.body.classList.remove('oscuro');
    }
  }
}
