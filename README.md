# Task Manager

## Project Overview
The Task Manager project provides a platform for users to manage their tasks efficiently. Users can add tasks, update their statuses, and manage tasks assigned to them.

## Purpose
This project aims to offer users a system where they can manage their tasks by adding, updating, and assigning them to others. The system keeps track of task statuses and allows users to organize tasks by priority.

## Features
- User Registration and Login
- Secure Authentication using Firebase Authentication
- Task creation and assignment
- Task priority management (Normal, Important, Very Important)
- Task status updates
- Task filtering options ("Assigned to me" and "All tasks")
  
## Technologies Used
- **Firebase Authentication**: Allows secure login and user registration with email and password.
- **Firestore Database**: Used for real-time data synchronization, task management, and sorting.

## Screens
### User Login

![image](https://github.com/user-attachments/assets/171f121b-1904-4202-b6bf-ffac74e7c1a7)

Users log in using their email and password. The system checks if the user exists in the database before granting access.



### User Registration

![image](https://github.com/user-attachments/assets/38ee93bc-e825-4775-820e-e9bc9c6640c3)

If a user does not have an account, they can create one. Users can also navigate back to the login screen if needed.



### Task List

![image](https://github.com/user-attachments/assets/f09e8944-f8cd-4a87-aa31-9fc543f343cf)

Tasks are displayed in two sections:
1. **Assigned to Me**: Shows tasks assigned to the logged-in user.
2. **All Tasks**: Shows all available tasks.
   
Tasks are displayed using a `ListView` due to the small dataset size, but for larger datasets, `RecyclerView` is used.

### Add Task Screen

![image](https://github.com/user-attachments/assets/1db8b7ce-d61b-4876-8507-3673f3fd3999)

Users can add new tasks and assign them to themselves or others. Tasks are categorized by priority levels, which are:
- Normal (1)
- Important (2)
- Very Important (3)

Tasks are sorted based on their priority for better organization.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/canercoskuun/TaskManager.git
