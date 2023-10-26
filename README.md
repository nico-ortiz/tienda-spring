# Spring Boot CRUD Application 

##  Requirements

For building and running the application you need:

- Java Version 17.0+
- Maven 3

## Configuration

Lets first create a database in PostgreSQL server using the below command:

```
create database <database_name>;
```

We need to configure URL, username and password so that Spring can establish a connection with the database on startup.
Add the above properties in [application.properties](https://github.com/nico-ortiz/tienda-spring/blob/main/src/main/resources/application.properties) file.

```
spring.datasource.url=jdbc:postgresql://localhost:<port>/<database_name>
spring.datasource.username = USERNAME
spring.datasource.password = PASSWORD
```

## How to run 

Run command: 

```
./mvnw spring-boot:run
```
Run tests:

```
./mvnw test
```

To run especific test:
```
./mvnw test -Dtest="<test class name>"
```
## UML Diagram
- [Diagram](https://github.com/nico-ortiz/tienda-spring/blob/main/docs/UML.png)

## API Documentation

- [Documentation](https://documenter.getpostman.com/view/17467236/2s9YJZ3jGt)