# OnlineTrainingSystem💪🏋️‍♀️

![home](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/9751cf9e-292a-421f-ac9e-4ca3f3233ec2)
![login-page](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/4b968272-17a6-4c30-b9d4-2399e2225c91)

## List of Contents📜
* [Developers](#developers)
* [Goal of the Application](#goal-of-the-application)
* [Key Features](#key-features)
* [Registration of Clients and Coaches](#registration-of-clients-and-coaches)
  * [For Clients](#for-clients)
  * [For Coaches](#for-coaches)  
* [Business Logic](#business-logic)
* [Some functionalities](#some-functionalities)
* [Database](#database)
* [Technologies](#technologies)


## Developers👨‍💻
  
* [Josip Čeprnić](https://github.com/Cepa95)
  
  [![text](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/josip-ceprnic/)
  
* [Luka Polić](https://github.com/PolicLL)
  
  [![text](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/luka-polic-a3a848231/)


## Goal of the Application⚽🥅
The primary objective of this application is to facilitate the connection between clients and coaches in a mutually beneficial way. This app serves as a tool to streamline communication between both parties, helping coaches and clients achieve their fitness and training goals. This system is designed to enhance the experience of both clients and coaches, ensuring effective communication and support in achieving fitness and training goals. The matching algorithm and customized workout plans contribute to a more personalized and successful training experience for all users.

## Key Features🔑

1. **Registration of Clients and Coaches**
   - Clients and coaches can sign up and provide essential information about themselves.

2. **Adding Different Types of Workouts**
   - Coaches can add various types of workouts to the system, such as bodybuilding, street workout, pilates, and yoga.

3. **Matching Clients and Coaches**
   - The application matches clients with suitable coaches based on the information provided during registration.

4. **For Coaches**
   - Coaches can create customized workout plans for their clients.
   - Coaches can streamline their workflow and enhance efficiency by utilizing templates to create workouts.

5. **For Clients**
   - Clients can fill out forms designed to track their progress.

6. **Tracking Progress**
   - The system can calculate and track the progress of clients based on the data they input.

7. **Workout Plan Generation**
   - Clients can download their workout plans in PDF format or a similar format.
     
8. **Admin CRUD Operations**
   - Admin has full control over user management, including creating, reading, updating, and deleting client, coach profiles, contracts, measurments...
    
![contract-list](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/49037fc0-5894-43b0-bedb-d7c33c758ee4)
![admin-exercises](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/98de5890-1342-400b-b5e8-ada30421c670)


## Registration of Clients and Coaches📲📝

### For Clients:

- **Registration**
  - Insert basic information, including gender, age, current height, and current weight.
  - Specify the desired types of training.
  - Provide information about previous training experience.
  - Indicate preferences for a coach, including gender, age, experience, and price.

### For Coaches:

- **Registration**
  - Insert basic information, including gender and age.
  - Specify the types of training offered and educational background.
  - Provide years of coaching experience and pricing details.

![register](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/b2fbe59a-dec8-4956-9ee0-d5066f6cdebf)
![client-registration-form](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/73a1f4e0-503b-4d2b-afc2-0dee6dfed50d)
![coach-registration-form](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/ab3bcc34-f5f8-4508-904c-17b3c3270150)


## Business Logic🎓💡

- **Account Creation and Welcome Email**
  - When a new user account is created, the application sends a "Welcome" email to the user's registered email address.

- **Matching Algorithm**
  - The application uses an algorithm to match clients with coaches based on factors such as gender, age, experience, types of training, and price.

- **Coach-Client Interaction**
  - Coaches can create tailored training programs for clients.
  - Coaches can streamline their workflow and enhance efficiency by utilizing templates to create workouts.

- **Progress Tracking**
  - Clients can fill out forms to track their progress, and the software calculates and monitors their improvements.
  - Clients can visualize their fitness journey through dynamic charts that illustrate their measurements, offering an engaging and visually appealing representation of their progress over time.

- **Workout Plan Delivery**
  - Clients have the ability to download their training programs in PDF format.



## Some functionalities📊

![client-page](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/a84f0da4-5498-48a8-b9fa-dfb51417700a)
![measurements](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/f3e65a22-2fc6-419c-ad82-43656b92c08a)
![charts](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/9573c78e-bb20-4d8d-b930-e28c69f3f254)
![contracts](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/99fdd9bb-c7bf-4466-8103-406931eb3640)
![client-workout-list](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/7f2a21df-72ac-440a-a53d-8e87c75f8c22)
![client-workout-details](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/5f0d8eb4-790f-4fad-a515-921303489173)


![coach-page](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/19361914-263a-4824-a96e-6e053422422b)
![workout-details](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/4ae23270-5f4b-4bf8-a309-b47419603223)
![workout-details-exercises](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/39a2598e-2375-4743-83cb-09c2c44f5e05)
![update-workout](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/38b59efa-2d8d-4d4f-b059-ca86eaf9d0a4)



## Database⛓🔑
![database](https://github.com/OSS-Java-Seminar-2023/OnlineTrainingSystem/assets/124800316/3826f440-fc49-43d3-a68c-f4211f2fb792)


## Technologies🖥
  <p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" height="70"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" height="70"/>  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original-wordmark.svg" height="70"/> 
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original-wordmark.svg" height="70"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original-wordmark.svg" height="70"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/bootstrap/bootstrap-original-wordmark.svg" height="70" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" height="70" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original-wordmark.svg" height="70" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original-wordmark.svg" height="70"/>

    
  ![Flyway](https://img.shields.io/badge/flyway-flyway?color=red)
  ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Thymeleaf?color=greem)
  </p>

  


  
