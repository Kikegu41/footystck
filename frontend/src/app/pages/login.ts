import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Usuario } from '../models/modelos';


// el login y el registro estan juntos en la misma pantalla, con un interruptor
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html'
})
export class Login {

  modoRegistro = false;
  nombre = '';
  email = '';
  password = '';
  error = '';


  constructor(private auth: AuthService, private router: Router) {}


  enviar(): void {

    this.error = '';

    // segun el modo llamo a registro o a login (los dos hacen casi lo mismo)
    const peticion = this.modoRegistro
      ? this.auth.registro(this.nombre, this.email, this.password)
      : this.auth.login(this.email, this.password);

    peticion.subscribe({
      next: (usuario: Usuario) => this.entrar(usuario),
      error: e => this.error = (e.error && e.error.error) ? e.error.error : 'Ha ocurrido un error'
    });
  }


  // guardo la sesion y vuelvo al inicio
  entrar(usuario: Usuario): void {
    this.auth.guardarSesion(usuario);
    this.router.navigate(['/']);
  }
}
