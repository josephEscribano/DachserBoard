export type User = {
  id: string;
  name: string;
  email: string;
  photoUrl?: string;
};

export type Project = {
  id: string;
  name: string;
};

export type ProjectRequest = Partial<Omit<Project, 'id'>>;

export type Task = {
  id: string;
  idProject: string;
  title: string;
  description?: string;
  status: TaskStatus;
  assignedTo?: User[];
};

export type TaskRequest = Partial<Omit<Task, 'id'>>;

export type PatchTask = Partial<Omit<Task, 'id' | 'idProject'>>;

export type TaskStatus = 'TO_DO' | 'IN_PROGRESS' | 'ON_HOLD' | 'DONE';

export const TaskStatusValues: TaskStatus[] = [
  'TO_DO',
  'IN_PROGRESS',
  'ON_HOLD',
  'DONE',
];
