# 3Line Coding Exercise

This RESTful API provides an endpoint for opening a new current account for existing customers with an optional initial credit transaction, built using Java 17 and Spring Boot. The project uses in-memory data storage (H2) for easy setup and testing, structured with layered architecture to support enterprise-level scalability and testability.

## Quick Start

1. **Clone the repository:**
    ```bash
    git clone https://github.com/michaelik/3L.git
    cd 3l
    ```

2. **Run the application:**
    - Using the command line:
      ```bash
      ./gradlew bootRun
      ```
    - Using IntelliJ IDEA:
        - Open the project in IntelliJ IDEA.
        - Open `DemoApplication.java`, and click "Run" to start the server.

3. **Access the API Documentation:**
   Open [http://localhost:3030/swagger-ui.html](http://localhost:3030/swagger-ui.html) in your browser to explore the API endpoints.

---

## Table of Contents

- [Project Features](#project-features)
- [Commands](#commands)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Bootstrapped Data](#bootstrapped-data)
- [API Endpoints](#api-endpoints)
- [Running the Tests](#running-the-tests)

## Project Features

- **Account Management**: Creates and manages accounts for existing customers.
- **Transactions**: Generates transactions automatically for any initial credit applied during account creation.
- **Bootstrapped Data**: Includes two sample customers, `3LINE001` and `3LINE002`, for testing.
- **Documentation**: API documentation through Swagger.
- **Error Handling**: Centralized error handling.
- **In-Memory Database**: Uses H2 for fast, ephemeral storage.

## Commands

Start the project locally:

```bash
./gradlew bootRun
```

Or if using IntelliJ:

1. Open IntelliJ and import as a Gradle project.
2. Navigate to `src/main/java` and locate `DemoApplication.java`.
3. Run the `DemoApplication` class.

### Testing

Run tests with:

```bash
./gradlew test
```

Or in IntelliJ:

1. Right-click on the `test` folder and select `Run All Tests`.

## Configuration

The project has default settings in `application.yml`, so no changes are required.

### application.yml (default values)

```yaml
server:
  port: 3030

spring:
  datasource:
    url: jdbc:h2:mem:transactiondb;
    driverClassName: org.h2.Driver
    username: sa
    password: 
  jpa:
    hibernate:
      ddl-auto: create
```

## API Documentation

API documentation is available at [http://localhost:3030/swagger-ui.html](http://localhost:3030/swagger-ui.html) when the server is running. This includes details for each endpoint, parameters, and response formats.

## Bootstrapped Data

The application includes two preloaded customer records, with `customerId` values of `3LINE001` and `3LINE002`. These IDs can be used for testing account creation and transactions.

## API Endpoints

### Account Management

#### Create Current Account

```http
POST http://localhost:3030/api/accounts/3LINE001
```

**Request Body:**

```json
{
  "initialCredit": 100.00
}
```

**Response:**

```json
{
  "success": true,
  "message": "Current Account Created Successfully",
  "time": "2024-11-10T20:58:32.7338296"
}
```

### User Information

Retrieve customer details along with their account and transaction history.

```http
GET http://localhost:3030/api/customers/3LINE001
```

**Response:**

```json
{
  "success": true,
  "message": "Customer details retrieved successfully",
  "data": {
    "customerId": "3LINE001",
    "surname": "Lawanson",
    "firstName": "Ayo",
    "account": [
      {
        "id": "bd1963b1-44c4-4fa9-9b2d-e76f1676beeb",
        "accountType": "CURRENT",
        "balance": 100.00,
        "transaction": [
          {
            "id": "06ee2f5f-e43b-45de-9c43-b338dbfaf0ca",
            "amount": 100.00,
            "accountType": "CREDIT",
            "createdAt": "2024-11-10T22:58:32.7338296"
          }
        ]
      }
    ]
  }
}
```

## Running the Tests

Run all tests using Gradle:

```bash
./gradlew test
```

Or, in IntelliJ:

1. Open the `src/test` directory.
2. Right-click and select `Run All Tests` to execute both unit and integration tests.