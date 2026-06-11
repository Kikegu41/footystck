import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CarritoService } from '../services/carrito.service';
import { AuthService } from '../services/auth.service';
import { PedidosService } from '../services/pedidos.service';
import { DatosEnvio } from '../models/modelos';
import { Penalti } from '../components/penalti';


// los codigos de descuento que valen. los GOL* se ganan en el minijuego
const PROMOS: { [codigo: string]: number } = {
  FOOTY5: 5, FOOTY10: 10, FOOTY15: 15, FOOTY20: 20,
  GOL5: 5, GOL10: 10, GOL15: 15
};

// el iva en españa es el 21%
const IVA = 0.21;


// la segunda pantalla del carrito: datos de envio a la izquierda y el desglose a la derecha
@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, Penalti],
  templateUrl: './checkout.html'
})
export class Checkout implements OnInit {

  datos: DatosEnvio = { nombre: '', apellidos: '', pais: '', region: '', ciudad: '', cp: '', direccion: '', telefono: '' };
  codigo = '';
  descuentoPct = 0;
  mensajePromo = '';

  // el minijuego del penalti, que sale despues de pagar
  juegoVisible = false;
  disparosJuego = 1;


  constructor(
    public carrito: CarritoService,
    private auth: AuthService,
    private pedidos: PedidosService,
    private router: Router
  ) {}


  ngOnInit(): void {

    // si el carrito esta vacio no hay nada que finalizar, vuelvo al carrito
    if (this.carrito.items.length === 0) {
      this.router.navigate(['/carrito']);
    }
  }


  // miro el codigo que han escrito y pongo (o quito) el descuento
  aplicarPromo(): void {

    const c = this.codigo.trim().toUpperCase();

    if (PROMOS[c]) {
      this.descuentoPct = PROMOS[c];
      this.mensajePromo = 'Código aplicado: ' + this.descuentoPct + '% de descuento.';
    } else {
      this.descuentoPct = 0;
      this.mensajePromo = c ? 'Código no válido.' : '';
    }
  }


  // ----- las cuentas del desglose -----

  subtotal(): number { return this.redondear(this.carrito.total()); }

  baseSinIva(): number { return this.redondear(this.subtotal() / (1 + IVA)); }

  iva(): number { return this.redondear(this.subtotal() - this.baseSinIva()); }

  descuento(): number { return this.redondear(this.subtotal() * this.descuentoPct / 100); }

  total(): number { return this.redondear(this.subtotal() - this.descuento()); }

  // para dejar solo 2 decimales en los euros
  redondear(n: number): number { return Math.round(n * 100) / 100; }


  // ¿estan rellenos todos los datos de envio?
  datosCompletos(): boolean {
    const d = this.datos;
    return !!(d.nombre && d.apellidos && d.pais && d.region && d.ciudad && d.cp && d.direccion && d.telefono);
  }


  // hace el pedido
  pagar(): void {

    // para comprar tienes que estar logueado
    if (!this.auth.usuario) {
      alert('Debes iniciar sesión para finalizar la compra.');
      this.router.navigate(['/login']);
      return;
    }

    // y tienes que haber rellenado la direccion
    if (!this.datosCompletos()) {
      alert('Rellena todos los datos de envío.');
      return;
    }

    this.pedidos.crear(this.auth.usuario.id, this.carrito.items, this.datos, this.codigo).subscribe({
      next: (res: any) => {

        // 1 disparo del penalti por cada 20 € del pedido (minimo 1)
        this.disparosJuego = Math.max(1, Math.floor(res.total / 20));

        this.carrito.vaciar();
        this.juegoVisible = true;
      },
      error: () => alert('Hubo un error al procesar el pedido.')
    });
  }


  // cuando se cierra el minijuego, lo llevo a "mis pedidos"
  finJuego(): void {
    this.router.navigate(['/mis-pedidos']);
  }
}
