import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { PedidosService } from '../services/pedidos.service';
import { AuthService } from '../services/auth.service';
import { Pedido } from '../models/modelos';


// los pedidos que ha hecho el cliente que esta logueado
@Component({
  selector: 'app-mis-pedidos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mis-pedidos.html'
})
export class MisPedidos implements OnInit {

  pedidos: Pedido[] = [];
  expandido: number | null = null;


  constructor(private pedidosSrv: PedidosService, private auth: AuthService, private router: Router) {}


  ngOnInit(): void {

    // esto es solo para clientes logueados, si no hay nadie, lo envio al login
    if (!this.auth.usuario) {
      this.router.navigate(['/login']);
      return;
    }

    this.pedidosSrv.mios(this.auth.usuario.id).subscribe(p => this.pedidos = p);
  }


  // abre o cierra el detalle de un pedido (si pulso el que ya estaba abierto, lo cierro)
  alternar(id: number): void {
    this.expandido = this.expandido === id ? null : id;
  }
}
