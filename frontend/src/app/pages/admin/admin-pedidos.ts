import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../services/admin.service';
import { Pedido } from '../../models/modelos';


// la lista de todos los pedidos que han llegado, con el correo de cada cliente
@Component({
  selector: 'app-admin-pedidos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-pedidos.html'
})
export class AdminPedidos implements OnInit {

  pedidos: Pedido[] = [];
  expandido: number | null = null;


  constructor(private admin: AdminService) {}


  ngOnInit(): void {
    this.admin.listarPedidos().subscribe(p => this.pedidos = p);
  }


  // enseña o esconde el detalle de un pedido
  alternar(id: number): void {

    if (this.expandido === id) {
      this.expandido = null;
    } else {
      this.expandido = id;
    }
  }
}
