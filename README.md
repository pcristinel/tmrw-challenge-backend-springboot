# Google Docs "*clone*"

## What?

- This simple microservice handles real time updates of a Google Docs like application.
- It is veryyyyyy far from being a Google Docs (it supports only basic text, no formatting whatsoever), but it is a simple example of how to handle real time updates in a document.

## Why?

- This project has been created to demostrate my knowledge in Java using Spring Boot and concurrency for a job interview.
- It was quite challenging to handle the real time, deltas, scheduling snapshots, etc.

## Project structure
The project tries to follow best practices from what I've learned over the years regarding clean/hex architecture. The organization ideas have been taken from the book [Get Your Hands Dirty on Clean Architecture (2nd edition)](https://reflectoring.io/book/).

Most of the business logic is handled inside [DocumentEditWebSocketHandler](src/main/java/com/cristinelpavel/tmrw_challenge_backend_springboot/adapter/in/websocket/DocumentEditWebSocketHandler.java), [DocumentDeltaProcessorImpl](src/main/java/com/cristinelpavel/tmrw_challenge_backend_springboot/application/domain/service/DocumentDeltaProcessorImpl.java) and [DocumentSnapshotProcessorImpl](src/main/java/com/cristinelpavel/tmrw_challenge_backend_springboot/application/domain/service/DocumentSnapshotProcessorImpl.java),

## Tech stack
- IDE: IntelliJ IDEA 2024.3.2.2 (Ultimate Edition)
- Java 21 to be able to make use of Virtual Threads
- Spring Boot 3.4.2
    - Spring Data JPA
    - Spring WebSocket
    - Spring DevTools
- Lombok
- Maven 3.9
- PostgreSQL 17.2
- Docker 27.5.1
- Liquibase for versioning

## Prerequisites to test/run the application

- Java 21
- Maven 3.9
- Docker 27.5.1

## How to run the backend

1. Clone the repository
2. Since the project uses PostgreSQL, you need to have it running. For that you can use the [docker compose file](docker-compose.yml) provided in the repository. Placed on the root of the repository, run the following command:
   ```shell
   docker compose -f docker-compose.yml up -d
   ```
3. You have 2 options to run the project:
   1. In the cloned folder repository, run the following command:
      ```shell
      mvn spring-boot:run
      ```
   2. Open it in your favorite IDE and run the main class `TmrwChallengeBackendSpringbootApplication`

4. Once you have the backend running, you need the frontend to be able to interact with the backend. For that, you can clone the repository
   of the frontend from [here](https://github.com/pcristinel/tmrw-challenge-frontend)

5. Open the index.html file.
6. For simplicity, [I've created 3 documents](src/main/java/com/cristinelpavel/tmrw_challenge_backend_springboot/adapter/out/persistence/NoProdEnvDataLoader.java)  that you can interact with. As of now there is no way to create new documents, but you can interact with the existing ones.

## Using the application
Once you open the index.html file, you will see a list of documents. You can click on any of them to open it. Once you open it, you can start typing. The changes will be saved in the database and will be reflected in real time in the frontend. To see the changes in real time, you can open the document in another tab or browser.

## Future improvements
### Backend
- Add the possibility to create new documents
- Add the possibility to delete documents
- Handle formatting (bold text, italic, etc...)
- TESTING, lots of it. Due to the time constraints, I had no time to write tests. I would like to write unit tests, integration tests, and end-to-end tests. Right now I've only covered the happy path and I'm pretty sure that there are a lot of things that will fail if the user pushes just by a tiny bit the functionality.


### Frontend
The frontend is very basic (the challenge's focus was the backend development) and it needs a lot of improvements. Some of the improvements that I can think of are:
- Nicer UI
- Use a frontend framework like React, Angular, Vue.js since a real time application requires functionalities that easier to implement with a frontend framework
- Add the possibility to create new documents
- Add the possibility to delete documents
- Handle formatting (bold text, italic, etc...)