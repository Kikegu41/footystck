import { Routes } from '@angular/router';
import { Home } from './pages/home';
import { Carrito } from './pages/carrito';
import { Checkout } from './pages/checkout';
import { MisPedidos } from './pages/mis-pedidos';
import { Login } from './pages/login';
import { Admin } from './pages/admin';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'carrito', component: Carrito },
  { path: 'finalizar', component: Checkout },
  { path: 'mis-pedidos', component: MisPedidos },
  { path: 'login', component: Login },
  { path: 'admin', component: Admin },
  { path: '**', redirectTo: '' }   // cualquier ruta desconocida vuelve al inicio
];
