import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './components/header';
import { CategoriasMenu } from './components/categorias-menu';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Header, CategoriasMenu],
  templateUrl: './app.html'
})
export class App {}
