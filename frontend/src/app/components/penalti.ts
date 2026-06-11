import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

type Zona = 'izquierda' | 'centro' | 'derecha';

// Minijuego de penaltis que sale al terminar una compra.
// Si marcas, ganas un codigo de descuento (5/10/15%) para la proxima compra.
@Component({
  selector: 'app-penalti',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './penalti.html'
})
export class Penalti implements OnInit {
  @Input() disparos = 1;          // numero de tiros (1 por cada 20 € del pedido)
  @Output() cerrar = new EventEmitter<void>();

  restantes = 0;
  mejorPremio = 0;                // mejor descuento conseguido (0 = aun sin gol)
  mensaje = 'Elige dónde chutar';
  animando = false;
  terminado = false;
  ladoPelota: Zona | null = null;
  ladoPortero: Zona | null = null;

  ngOnInit(): void {
    this.restantes = this.disparos;
  }

  // El jugador chuta a una zona
  chutar(zona: Zona): void {
    if (this.animando || this.terminado) { return; }
    this.animando = true;
    this.ladoPelota = zona;
    this.ladoPortero = this.zonaAzar();

    // Esperamos a que termine la animacion para ver el resultado
    setTimeout(() => {
      if (zona !== this.ladoPortero) {
        const premio = [5, 10, 15][Math.floor(Math.random() * 3)];
        if (premio > this.mejorPremio) { this.mejorPremio = premio; }
        this.mensaje = '¡GOOOL! +' + premio + '% de descuento';
      } else {
        this.mensaje = '¡Paradón del portero!';
      }
      this.restantes--;
      if (this.restantes <= 0) { this.terminado = true; }
      this.animando = false;
    }, 850);
  }

  // El portero se tira a un lado al azar
  zonaAzar(): Zona {
    const zonas: Zona[] = ['izquierda', 'centro', 'derecha'];
    return zonas[Math.floor(Math.random() * 3)];
  }

  // Codigo que se lleva el cliente (GOL5 / GOL10 / GOL15)
  codigoPremio(): string {
    return 'GOL' + this.mejorPremio;
  }
}
