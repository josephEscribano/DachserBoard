import { Component, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ProjectRequest } from '../../../../domain/types';
import { ProjectService } from '../../../services/project.service';
import {
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-project-create',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatDialogContent,
    MatDialogActions,
    MatDialogTitle,
  ],
  templateUrl: './project-create.component.html',
})
export class ProjectCreateComponent {
  readonly dialogRef = inject(MatDialogRef<ProjectCreateComponent>);
  projectService = inject(ProjectService);

  projectForm = new FormGroup({
    name: new FormControl('', Validators.required),
  });

  onSubmit(): void {
    const project: ProjectRequest = {
      name: this.projectForm.value.name!,
    };

    this.projectService.create(project).subscribe(() => this.dialogRef.close());
  }
}
