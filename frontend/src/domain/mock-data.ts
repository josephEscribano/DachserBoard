import { Project, Task, User } from './types';

export const usersMockData: User[] = [
  {
    id: '696336d6-d891-4271-84d6-8e8a552765e2',
    name: 'Ronald Pacheco',
    email: 'ronald.pachecoalarcon@dachser.com',
    photoUrl: '/assets/imgs/1.webp',
  },
  {
    id: '0be06abb-d0e4-4063-bf64-0c6408117edd',
    name: 'Candidate',
    email: 'candidate@dachser.com',
  },
];

export const ProjectMockData: Project[] = [
  {
    id: '3423cd8f-104c-4cd5-a8b6-c114d096798d', // Dont touch this id
    name: '💻 Technical Challenge',
  },
  {
    id: '003341cb-0850-467b-85d7-ff108ed1068c',
    name: '👀 Another test project',
  },
];

export const TasksMockData: Task[] = [
  {
    id: '78bb0446-46d4-45da-8c85-f33cb7946162',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '🚀 Create a Spring boot project (backend)',
    description:
      'Use the official page of spring (https://start.spring.io/), select java and maven options',
    status: 'TO_DO',
  },
  {
    id: 'f5b0c982-0847-428c-a8a3-4da57c51c81c',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '📥 Create DB schema in H2 (backend)',
    description: 'Create the database for this project, check domain',
    status: 'TO_DO',
  },
  {
    id: '08d44100-d8ca-44a3-ab95-940837de6f08',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '🅱️ Create the domain layer (backend)',
    description:
      'Create the domain classes of the project, eg. Project, task ..., can you use lombok library',
    status: 'TO_DO',
  },
  {
    id: 'f7da1a52-fa62-46eb-95a0-7d23b2bddb58',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '🫵 Configure H2 database (backend)',
    description:
      'Configure the h2 database, create the create table scripts and insert init data',
    status: 'TO_DO',
  },
  {
    id: 'f1e4e8e6-ac6b-4127-94a7-26b013f9d3e6',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '📦 Implement persistence layer (backend)',
    description: 'Create the entities, repositories, spects',
    status: 'TO_DO',
  },
  {
    id: '4ae1f85a-df2f-4df5-a72c-93a2429d1579',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '💼 Implement business layer (backend)',
    description: 'Create the services and mappers',
    status: 'TO_DO',
  },
  {
    id: 'e5738d9b-2e4f-4265-b11b-b07423edd0f5',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '🕯️ Implement presentation layer (backend)',
    description: 'Create the rest controllers to expose the service layer',
    status: 'TO_DO',
  },
  {
    id: 'b9b2bc3b-a94a-4321-b8d8-27b547345164',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '⛓️ Integrate API-REST (front)',
    description:
      'Refactor the services to get the information from the backend',
    status: 'TO_DO',
  },
  {
    id: '5411298f-2474-4a45-8130-1db46d567973',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '🟡 Implement New Project feature (front)',
    description:
      'The creation of a new project will be done through a dialog, where a form will be displayed with a single input, the name of the project and the save button.',
    status: 'TO_DO',
  },
  {
    id: '215a49bc-97a0-4ff4-be60-39c01312ba4c',
    idProject: '3423cd8f-104c-4cd5-a8b6-c114d096798d',
    title: '🔵 Implement New Task feature (front)',
    description:
      'The creation of a new task will be done through a dialog, where a form will be displayed with the title, description, assigned to inputs.',
    status: 'TO_DO',
  },
];
