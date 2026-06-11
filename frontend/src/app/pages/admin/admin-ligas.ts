import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { Liga, Equipo } from '../../models/modelos';


// aqui gestiono las ligas y, dentro de cada liga, sus equipos
@Component({
  selector: 'app-admin-ligas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-ligas.html'
})
export class AdminLigas implements OnInit {

  ligas: Liga[] = [];
  ligaSel: Liga | null = null;
  equipos: Equipo[] = [];
  mensaje = '';

  // formulario de la liga
  mostrarLigaForm = false;
  editandoLigaId: number | null = null;
  ligaForm: any = {};

  // formulario del equipo
  mostrarEquipoForm = false;
  editandoEquipoId: number | null = null;
  equipoForm: any = {};


  constructor(private admin: AdminService) {}


  ngOnInit(): void {
    this.cargarLigas();
  }

  cargarLigas(): void {
    this.admin.listarLigas().subscribe(l => this.ligas = l);
  }


  // ----- ligas -----

  nuevaLiga(): void {
    this.editandoLigaId = null;
    this.ligaForm = { nombre: '', pais: '', division: 1, codigo_api: null };
    this.mensaje = '';
    this.mostrarLigaForm = true;
  }

  editarLiga(l: Liga): void {
    this.editandoLigaId = l.id;
    this.ligaForm = { nombre: l.nombre, pais: l.pais, division: l.division, codigo_api: l.codigo_api };
    this.mensaje = '';
    this.mostrarLigaForm = true;
  }

  guardarLiga(): void {

    const peticion = this.editandoLigaId
      ? this.admin.editarLiga(this.editandoLigaId, this.ligaForm)
      : this.admin.crearLiga(this.ligaForm);

    peticion.subscribe({
      next: () => this.ligaGuardada(),
      error: () => this.mensaje = 'No se pudo guardar.'
    });
  }

  ligaGuardada(): void {
    this.mostrarLigaForm = false;
    this.cargarLigas();
    this.mensaje = 'Liga guardada.';
  }


  // ----- equipos de la liga que tengo seleccionada -----

  verEquipos(l: Liga): void {
    this.ligaSel = l;
    this.mostrarEquipoForm = false;
    this.admin.listarEquipos(l.id).subscribe(e => this.equipos = e);
  }

  nuevoEquipo(): void {
    this.editandoEquipoId = null;
    this.equipoForm = { nombre: '', escudo_url: '', es_otros: false, liga_id: this.ligaSel!.id };
    this.mostrarEquipoForm = true;
  }

  editarEquipo(e: Equipo): void {
    this.editandoEquipoId = e.id;
    this.equipoForm = { nombre: e.nombre, escudo_url: e.escudo_url, es_otros: e.es_otros, liga_id: e.liga_id };
    this.mostrarEquipoForm = true;
  }

  guardarEquipo(): void {

    // si no es de "Otros", el equipo va en la liga que tengo seleccionada
    if (!this.equipoForm.es_otros) {
      this.equipoForm.liga_id = this.ligaSel!.id;
    }

    const peticion = this.editandoEquipoId
      ? this.admin.editarEquipo(this.editandoEquipoId, this.equipoForm)
      : this.admin.crearEquipo(this.equipoForm);

    peticion.subscribe(() => {
      this.mostrarEquipoForm = false;
      this.verEquipos(this.ligaSel!);
    });
  }

  borrarEquipo(e: Equipo): void {

    if (!confirm('¿Borrar el equipo "' + e.nombre + '"?')) { return; }

    this.admin.borrarEquipo(e.id).subscribe(() => this.verEquipos(this.ligaSel!));
  }
}
