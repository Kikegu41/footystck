import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../config';
import { Usuario } from '../models/modelos';


// aqui llevo todo lo del usuario: entrar, registrarse y la sesion
@Injectable({ providedIn: 'root' })
export class AuthService {

  // el usuario que esta dentro ahora mismo (null si no hay nadie)
  usuario: Usuario | null = null;


  constructor(private http: HttpClient) {

    // al arrancar miro si quedo alguien guardado en el navegador
    const guardado = localStorage.getItem('footystck_usuario');

    if (guardado) {
      this.usuario = JSON.parse(guardado);
    }
  }


  // envio el correo y la contraseña al backend para entrar
  login(email: string, password: string): Observable<Usuario> {
    return this.http.post<Usuario>(API_URL + '/auth/login', { email: email, password: password });
  }


  // lo mismo pero para crear una cuenta nueva
  registro(nombre: string, email: string, password: string): Observable<Usuario> {
    return this.http.post<Usuario>(API_URL + '/auth/registro', { nombre: nombre, email: email, password: password });
  }


  // guardo al usuario en memoria y en el navegador para que no se pierda al recargar
  guardarSesion(usuario: Usuario): void {
    this.usuario = usuario;
    localStorage.setItem('footystck_usuario', JSON.stringify(usuario));
  }


  // cerrar sesion: lo borro de memoria y del navegador
  logout(): void {
    this.usuario = null;
    localStorage.removeItem('footystck_usuario');
  }


  // true si el que esta dentro es admin
  esAdmin(): boolean {
    return this.usuario != null && this.usuario.rol === 'admin';
  }
}
