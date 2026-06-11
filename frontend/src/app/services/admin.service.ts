import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../config';
import { Camiseta, Liga, Equipo, Pedido } from '../models/modelos';


// servicio del panel de admin: aqui van todos los crear/editar/borrar y los listados
@Injectable({ providedIn: 'root' })
export class AdminService {

  constructor(private http: HttpClient) {}


  // ----- camisetas -----

  listarCamisetas(): Observable<Camiseta[]> {
    return this.http.get<Camiseta[]>(API_URL + '/camisetas');
  }

  crearCamiseta(c: any): Observable<any> {
    return this.http.post(API_URL + '/camisetas', c);
  }

  editarCamiseta(id: number, c: any): Observable<any> {
    return this.http.put(API_URL + '/camisetas/' + id, c);
  }

  borrarCamiseta(id: number): Observable<any> {
    return this.http.delete(API_URL + '/camisetas/' + id);
  }


  // ----- ligas -----

  listarLigas(): Observable<Liga[]> {
    return this.http.get<Liga[]>(API_URL + '/ligas');
  }

  crearLiga(l: any): Observable<any> {
    return this.http.post(API_URL + '/ligas', l);
  }

  editarLiga(id: number, l: any): Observable<any> {
    return this.http.put(API_URL + '/ligas/' + id, l);
  }


  // ----- equipos -----

  // si me pasan la liga, filtro por ella; si no, los traigo todos
  listarEquipos(ligaId?: number): Observable<Equipo[]> {

    let url = API_URL + '/equipos';

    if (ligaId) {
      url = url + '?liga_id=' + ligaId;
    }

    return this.http.get<Equipo[]>(url);
  }

  crearEquipo(e: any): Observable<any> {
    return this.http.post(API_URL + '/equipos', e);
  }

  editarEquipo(id: number, e: any): Observable<any> {
    return this.http.put(API_URL + '/equipos/' + id, e);
  }

  borrarEquipo(id: number): Observable<any> {
    return this.http.delete(API_URL + '/equipos/' + id);
  }


  // ----- pedidos -----

  listarPedidos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(API_URL + '/pedidos');
  }
}
