## Introduction:

The goal of this service is to expose a REST API for executing CRUD operations (single and bulk), replicating the
behavior of an email server. The API allows clients to create emails, change their state, and perform soft deletion by
marking them as 'deleted'.

### Service design:

I followed the Spring MVC pattern, separating Model, Logic, and Controller to ensure maintainable code.

I defined the Email Entity to represent email data within the system, with the Email repository interface utilizing JPA
methods on the entity. The Email service class contains the logic, and the RestController’s EmailEndpoint class has
endpoints .

To encapsulate the serialization's logic and decouple domain models from the presentation layer, I used the DTO (Data
Transfer Object) pattern. Although I could use manual DTO Mapper, I have opted to use the ModelMapper library to
simplify the mapping process between DTO and Entity.

I applied validation constraints and constraint groups to validate client input, as well as using @RestControllerAdvice
to centralize exception management.

### What Stack I have used:

intellij 2022.1.4, Java 21, Spring boot 3.22, Maven, Docker, MySql, git

I have used Spring Boot to simplify and speed up development as it provides autoconfigured features, such as Dependency
Injection, embedded web server, Spring Data JPA to work with databases as well as validation and unit testing etc.

For database, I used MySQL driven by the fact that the email entity structure is unlikely to change. I used MySQL Docker
Container in order to avoid manual installation and configuration in different environment.

### Installation guide:

MySql docker container used for storing data in Database

Run the following Docker command to create/run the MySQL container

docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --name mysql mysql

before you run the service you need to create MySQL Schema. I have used MySql Workbench 8. the Schema name is :
email-management

in case you have problem with maven. in intellij go to File -> Invalidate Caches/Restart

### The service has the following Endpoints:

Base URL: http://localhost:8080/

Insert Email: POST /emails

Mark Email as Sent: PATCH /emails/{id}/status

Update Email Content: PATCH /emails/{id}

Retrieve Single Email: GET /emails/{id}

Retrieve All Emails: GET /emails

Mark Email as Deleted: DELETE /emails/{id}

#### For Bulk Operation:

> **Note:**  following REST naming convention, the resource URI should have been **/bulk/emails** however the
> requirement of the task is that all Endpoints should be under the route /emails

DELETE http://localhost:8080/emails/bulk?ids-to-delete=1,2,3

To query multiple entities .pass the IDs with comma separated as a query parameter

GET http://localhost:8080/emails/bulk?ids-to-query=1,2

To insert multiple entities Make POST request and pass the data in the request body as JSON format, an array of emails,
{["emails": {email1, email2}]}

POST http://localhost:8080/emails/bulk

