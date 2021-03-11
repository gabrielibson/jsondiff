# JsonDiff

Spring Boot REST API for saving Base64 encoded json files and obtaining differences between them.

## Built With

* 	[Gradle](https://gradle.org/) - Dependency Management
* 	[JDK11](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit Version 11
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring
     Applications
* 	[MongoDB](https://www.mongodb.com/) - document oriented NoSql database, for data storage
*   [Lombok](https://projectlombok.org/) - for helping with the boilerplate code on POJO objects
*   [Docker](https://www.docker.com/) - for containerizing the solution
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system
* 	[Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTFUL Web services.
*   [JUnit 5](https://junit.org/junit5/) - Unit tests.

## Running application

The application can be executed in two ways:
- running with Docker (recommended)
- running with Gradle

### running with Docker
- Requirement: Docker
- Run the docker-compose command to start the application:
```sh
~/jsondiff: docker-compose up
```
### running with Gradle

- Requirement: JDK 11 + Mongo instance
- Run the Gradle command to start the application:
```shell
./gradlew bootRun
```

## Running tests
- Open Command Prompt and Change directory (cd) to project root folder.
- Run the Gradle command to running tests:
```shell
./gradlew test
```

## Opportunities of improvement

* Add an async diff processing for scalability
* Improve debug logs
* Test hypothesis with a relational database for lesser interactions with data layer

## External Tools Used

* [Postman](https://www.getpostman.com/) - API Development Environment (Testing Documentation)

## Documentation

* [Postman Collection Online](https://www.getpostman.com/collections/e23cbbaf73c5a905c950) - Endpoint collection online
* [Postman Collection Offline](https://github.com/gabrielibson/jsondiff/JsonDiff-API.postman_collection.json) - Endpoint collection offline
* [Swagger](http://localhost:8080/swagger-ui.html) - Documentation & Testing

## Packages

- `model` — to hold entities;
- `repository` — to communicate with the database;
- `service` — to hold business logic;
- `controller` — to listen to the client;
- `config` -  to hold application config;
- `enums` -  to hold enums;
- `exception` -  to hold exception classes;
- `dto` -  to hold data transfer objects.

- `resources/` - contains all the static resources and property files.

- `test/` - contains unit and integration tests

- `build.gradle` - contains all the project dependencies