import { Component, inject, OnInit } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { ProjectService } from '../services/project.service';
import { MatButtonModule } from '@angular/material/button';
import { Project } from '../../domain/types';
import { MatDialog } from '@angular/material/dialog';
import { ProjectCreateComponent } from '../pages/project/project-create/project-create.component';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    RouterModule,
  ],
  template: `
    <main class="h-screen flex flex-col">
      <mat-toolbar class="space-x-4 text-white bg-yellow-500">
        <button mat-icon-button (click)="snav.toggle()">
          <mat-icon>menu</mat-icon>
        </button>
        <div>
          <h1>Dachser Task Board</h1>
        </div>
      </mat-toolbar>

      <mat-sidenav-container class="flex-1">
        <mat-sidenav #snav mode="side" opened="true">
          <button
            mat-flat-button
            color="accent"
            class="w-full "
            (click)="openProjecCreateDialog()"
          >
            <mat-icon>add</mat-icon> New Project
          </button>
          <p class="text-center pt-3 mb-0 font-bold border-b">Your projects</p>
          <mat-nav-list>
            @for (project of projects; track project.id) {
            <a mat-list-item [routerLink]="'project/' + project.id">{{
              project.name
            }}</a>
            }
          </mat-nav-list>
        </mat-sidenav>

        <mat-sidenav-content class="pl-4 pt-2">
          <router-outlet />
        </mat-sidenav-content>
      </mat-sidenav-container>
    </main>
  `,
})
export class LayoutComponent implements OnInit {
  private readonly dialog = inject(MatDialog);
  private projectService = inject(ProjectService);

  projects: Project[] = [];

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.projectService.getAll().subscribe({
      next: (projects) => {
        this.projects = projects;
      },
    });
  }

  openProjecCreateDialog(): void {
    this.dialog
      .open(ProjectCreateComponent, {
        width: '500px',
      })
      .afterClosed()
      .subscribe(() => this.loadProjects());
  }
}
