import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CamisetasService } from '../services/camisetas.service';
import { Camiseta, Equipo } from '../models/modelos';
import { ProductCard } from '../components/product-card';


// la pagina de inicio. tiene dos modos:
//  - si entras en una liga, enseña los equipos de esa liga
//  - si no, enseña el catalogo de camisetas normal
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, ProductCard],
  templateUrl: './home.html'
})
export class Home implements OnInit {

  // cosas del catalogo
  coleccion = 'actual';
  orden = 'popularidad';
  buscar = '';
  camisetas: Camiseta[] = [];
  equipoId: number | null = null;
  equipoNombre = '';

  // cosas de cuando entras en una liga y ves los equipos
  ligaId: number | null = null;
  ligaNombre = '';
  otros = false;
  equipos: Equipo[] = [];

  cargando = false;


  constructor(
    private servicio: CamisetasService,
    private route: ActivatedRoute,
    private router: Router
  ) {}


  // ¿estoy enseñando equipos en vez de camisetas?
  modoEquipos(): boolean {
    return this.ligaId !== null || this.otros;
  }


  tituloEquipos(): string {

    if (this.otros) {
      return 'Otros equipos destacados';
    }

    return 'Equipos de ' + this.ligaNombre;
  }


  ngOnInit(): void {

    // cada vez que cambia la url miro que parametros traen y recargo
    this.route.queryParamMap.subscribe(params => {

      const liga = params.get('liga');
      const equipo = params.get('equipo');

      this.ligaId = liga ? Number(liga) : null;
      this.ligaNombre = params.get('liga_nombre') || '';
      this.otros = params.get('otros') === 'true';
      this.equipoId = equipo ? Number(equipo) : null;
      this.equipoNombre = params.get('nombre') || '';

      if (this.modoEquipos()) {
        this.cargarEquipos();
      } else {
        this.cargarCamisetas();
      }
    });
  }


  // pido los equipos (los de la liga, o los de "Otros")
  cargarEquipos(): void {

    this.cargando = true;

    const peticion = this.otros ? this.servicio.equiposOtros() : this.servicio.equiposDeLiga(this.ligaId!);

    peticion.subscribe(lista => {
      this.equipos = lista;
      this.cargando = false;
    });
  }


  // pido las camisetas. en la portada pongo un limite de 60 para que cargue rapido
  cargarCamisetas(): void {

    this.cargando = true;

    const limite = this.equipoId ? null : 60;

    this.servicio.listar(this.coleccion, this.equipoId, this.buscar, this.orden, limite).subscribe(lista => {
      this.camisetas = lista;
      this.cargando = false;
    });
  }


  cambiarOrden(evento: any): void {
    this.orden = evento.target.value;
    this.cargarCamisetas();
  }


  // al pulsar un equipo, voy al catalogo filtrado por ese equipo
  verEquipo(equipo: Equipo): void {
    this.router.navigate(['/'], { queryParams: { equipo: equipo.id, nombre: equipo.nombre } });
  }


  verTodo(): void {
    this.router.navigate(['/']);
  }


  // si una imagen no carga, la escondo
  ocultar(evento: any): void {
    evento.target.style.display = 'none';
  }
}
