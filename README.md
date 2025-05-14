# Java Maven App

A Spring Boot application configured with Jenkins CI/CD pipeline.

## Project Overview

This is a simple Spring Boot web application that demonstrates CI/CD practices using Jenkins. The application is packaged as a JAR file and can be deployed in a containerized environment.

## Tech Stack

-   Java 8
-   Maven
-   Spring Boot 2.3.4
-   JUnit 4.13.1
-   Docker
-   Jenkins

## Building the Application

### Prerequisites

-   Java 8 JDK
-   Maven 3.x
-   Docker (for containerization)

### Build Commands

Build the application:

```bash
mvn clean package
```

Run tests:

```bash
mvn test
```

## Docker Deployment

The application can be containerized using the provided Dockerfile:

```bash
docker build -t java-maven-app .
docker run -p 8080:8080 java-maven-app
```

## CI/CD Pipeline

This project includes a Jenkins pipeline configured in the Jenkinsfile with the following stages:

-   Test
-   Bump version
-   Build JAR
-   Build Docker image
-   Deploy
-   Commit version update

## License

[Add your license here]
