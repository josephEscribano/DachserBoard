# ✅ DACHSER TECHNICAL TEST

## 🎯 Objective
Build a web-app for managing projects and tasks, displayed in a board format with task status columns (similar to Jira or Trello). In this web-app you will be able to:
- 🗂️ Create Projects
- ✅ Create Tasks (within projects)
- 🔄 Change the status of tasks by moving them between state columns

## 🗃️ Structure
- The structure of this monorepo consists of two folders: `backend` and `frontend`.
- The `frontend` folder already includes a partial implementation of the solution, which you will use as a starting point.
- The `backend` folder is empty — this is where you'll create the Java + Spring Boot project to power the frontend.

## 🛠️ Tasks

You must create/implement the following:

- 🧱 Create the backend (inside the `backend` folder). You can use [Spring Initializr](https://start.spring.io/) to bootstrap it.
- 🗄️ Define and configure the database schema.
- 🧩 Implement all required layers to support CRUD for project and task.
- ✅ Write at least 2 unit tests for backend functionality.
- 🔗 Integrate the frontend with the backend via REST API (services located at: `project.service.ts`, `task.service.ts`).
- 📁 Implement project creation in the frontend:
  - When clicking the 'New Project' button, a dialog/modal should appear with a form to create a new project.
- 📝 Implement task creation in the frontend:
  - When clicking the 'Add Task' button, a dialog/modal should appear with a form to create a new task.
- 🧪 Create at least one unit test for a frontend functionality.

## 🚫 Restrictions / ✅ Recommendations

- ❌ Do not use AI tools (we will know 👀)
- ✅ Use **Java 17**.
- ✅ Use **JPA** for persistence.
- ✅ Use **H2 in-memory** database.
- 🔍 The model structure can be found in `frontend/src/domain/types.ts`.
- ✅ For backend testing, use **JUnit** and **Mockito**.
- ✅ For frontend testing, use **Karma/Jasmine** or **Jest**.
- ✅ You can use **Lombok**.
- ✅ You may also use **MapStruct** if needed.

## 🧾 NOTE

- The frontend is an **Angular v17** project, configured using **standalone**.
- The frontend currently displays **mocked data**, which should be replaced with real data coming from the backend.
- **Good practices and clean code are evaluated**

## ⏱️ TIME
- You have **one week** to complete the challenge. If you need more time, please **communicate it in advance**.

## 📦 DELIVERY
- Upload your project to a **public GitHub/GitLab/Bitbucket repository** and shared the link
