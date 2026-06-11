import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../config';
import { Camiseta, Liga, Equipo } from '../models/modelos';


// este servicio pide al backend las camisetas, las ligas y los equipos
@Injectable({ providedIn: 'root' })
export class CamisetasService {

  constructor(private http: HttpClient) {}


  // trae las camisetas. los filtros son opcionales, asi que solo añado el que venga
  listar(coleccion: string, equipoId: number | null, buscar: string, orden: string, limite: number | null): Observable<Camiseta[]> {

    let params = new HttpParams();

    if (coleccion) params = params.set('coleccion', coleccion);
    if (equipoId)  params = params.set('equipo_id', equipoId);
    if (buscar)    params = params.set('buscar', buscar);
    if (orden)     params = params.set('orden', orden);
    if (limite)    params = params.set('limite', limite);

    return this.http.get<Camiseta[]>(API_URL + '/camisetas', { params: params });
  }


  // las ligas con sus equipos dentro (para el menu de categorias)
  menuLigas(): Observable<Liga[]> {
    return this.http.get<Liga[]>(API_URL + '/ligas/menu');
  }


  // los equipos de la categoria "Otros"
  equiposOtros(): Observable<Equipo[]> {
    return this.http.get<Equipo[]>(API_URL + '/equipos?otros=true');
  }


  // los equipos de una liga concreta
  equiposDeLiga(ligaId: number): Observable<Equipo[]> {
    return this.http.get<Equipo[]>(API_URL + '/equipos?liga_id=' + ligaId);
  }
}
