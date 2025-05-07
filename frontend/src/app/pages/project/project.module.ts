import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: ':projectId',
    loadComponent: () =>
      import('./project-detail/project-detail.component').then(
        (c) => c.ProjectDetailComponent
      ),
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
})
export class ProjectModule {}
