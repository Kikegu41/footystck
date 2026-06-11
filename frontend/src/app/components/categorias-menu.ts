import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CamisetasService } from '../services/camisetas.service';
import { UiService } from '../services/ui.service';
import { Liga, Equipo } from '../models/modelos';


// el menu lateral de categorias. enseña las ligas y al pulsar una te lleva a sus equipos
@Component({
  selector: 'app-categorias-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './categorias-menu.html'
})
export class CategoriasMenu implements OnInit {

  ligas: Liga[] = [];
  otros: Equipo[] = [];


  constructor(
    public ui: UiService,
    private servicio: CamisetasService,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.servicio.menuLigas().subscribe(ligas => this.ligas = ligas);
    this.servicio.equiposOtros().subscribe(otros => this.otros = otros);
  }


  // solo enseño las ligas que tienen al menos un equipo
  ligasConEquipos(): Liga[] {
    return this.ligas.filter(l => l.equipos && l.equipos.length > 0);
  }


  // entro en una liga: la home enseña sus equipos. y de paso cierro el menu
  entrarLiga(liga: Liga): void {
    this.router.navigate(['/'], { queryParams: { liga: liga.id, liga_nombre: liga.nombre } });
    this.ui.cerrar();
  }


  entrarOtros(): void {
    this.router.navigate(['/'], { queryParams: { otros: true } });
    this.ui.cerrar();
  }


  verTodo(): void {
    this.router.navigate(['/']);
    this.ui.cerrar();
  }
}
