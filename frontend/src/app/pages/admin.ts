import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';
import { AdminCamisetas } from './admin/admin-camisetas';
import { AdminLigas } from './admin/admin-ligas';
import { AdminPedidos } from './admin/admin-pedidos';


// el panel de admin. solo tiene unas pestañas para cambiar entre las 3 secciones
@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, AdminCamisetas, AdminLigas, AdminPedidos],
  templateUrl: './admin.html'
})
export class Admin {

  // que pestaña esta puesta ahora mismo
  tab = 'camisetas';

  constructor(public auth: AuthService) {}
}
