import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { Camiseta, Equipo } from '../../models/modelos';


// gestion de camisetas del admin: la tabla con el buscador y el formulario de crear/editar/borrar
@Component({
  selector: 'app-admin-camisetas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-camisetas.html'
})
export class AdminCamisetas implements OnInit {

  camisetas: Camiseta[] = [];
  camisetasFiltradas: Camiseta[] = [];
  hayMas = false;
  equipos: Equipo[] = [];
  buscar = '';
  mensaje = '';

  mostrarForm = false;
  editandoId: number | null = null;
  form: any = this.formVacio();


  constructor(private admin: AdminService) {}


  ngOnInit(): void {

    this.cargar();

    // los equipos los necesito para el desplegable del formulario
    this.admin.listarEquipos().subscribe(e => this.equipos = e);
  }


  cargar(): void {
    this.admin.listarCamisetas().subscribe(c => {
      this.camisetas = c;
      this.filtrar();
    });
  }


  // filtra por nombre o equipo y muestra como mucho 100 (hay muchas)
  filtrar(): void {

    const b = this.buscar.toLowerCase();
    let lista = this.camisetas;

    if (b) {
      lista = this.camisetas.filter(c =>
        (c.nombre || '').toLowerCase().includes(b) ||
        (c.equipo_nombre || '').toLowerCase().includes(b));
    }

    this.hayMas = lista.length > 100;
    this.camisetasFiltradas = lista.slice(0, 100);
  }


  // un formulario vacio con valores por defecto, para cuando creo una nueva
  formVacio() {
    return { nombre: '', equipo_id: null, temporada: '2025/26', tipo: 'local', coleccion: 'actual', precio: 20, stock: 20, imagen_url: '', descripcion: '', destacado: false };
  }


  nuevo(): void {
    this.editandoId = null;
    this.form = this.formVacio();
    this.mensaje = '';
    this.mostrarForm = true;
  }


  // al editar relleno el formulario con los datos de esa camiseta
  editar(c: Camiseta): void {
    this.editandoId = c.id;
    this.form = { nombre: c.nombre, equipo_id: c.equipo_id, temporada: c.temporada, tipo: c.tipo, coleccion: c.coleccion, precio: c.precio, stock: c.stock, imagen_url: c.imagen_url, descripcion: c.descripcion, destacado: c.destacado };
    this.mensaje = '';
    this.mostrarForm = true;
  }


  cancelar(): void {
    this.mostrarForm = false;
  }


  // si hay id es que estoy editando, si no es nueva
  guardar(): void {

    const peticion = this.editandoId
      ? this.admin.editarCamiseta(this.editandoId, this.form)
      : this.admin.crearCamiseta(this.form);

    peticion.subscribe({
      next: () => this.guardado(),
      error: () => this.mensaje = 'No se pudo guardar.'
    });
  }


  guardado(): void {
    this.mostrarForm = false;
    this.cargar();
    this.mensaje = 'Guardado correctamente.';
  }


  borrar(c: Camiseta): void {

    if (!confirm('¿Borrar "' + c.nombre + '"?')) { return; }

    this.admin.borrarCamiseta(c.id).subscribe(() => this.cargar());
  }
}
