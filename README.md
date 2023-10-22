# Ticket Service

A simple ticket service implementation using Spring Boot that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue.

## Building the Solution

Ensure that you have Maven installed:

```
mvn --version
```

Navigate to the project's root directory and build the project:

```
mvn clean install
```

## Running the Application

After a successful build, run the application:

```
mvn spring-boot:run
```

Access the application at: http://localhost:8080

For in-memory database console: http://localhost:8080/h2-console

## Running Tests

From the project's root directory:

```
mvn test
```

## API Endpoints
Get Available Seats: GET /api/tickets/available?venueLevel={venueLevelId}

Hold Seats: POST /api/tickets/hold

Reserve Seats: POST /api/tickets/reserve
