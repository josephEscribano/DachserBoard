import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'ui',
    pathMatch: 'full',
  },
  {
    path: 'ui',
    component: LayoutComponent,
    children: [
      {
        path: 'project',
        loadChildren: () =>
          import('./pages/project/project.module').then((m) => m.ProjectModule),
      },
    ],
  },
];
