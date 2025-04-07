# 🚘 Camping Cars Marketplace

Welcome to the **Camping Cars Marketplace** platform! This project is designed to create a seamless experience for users to **offer and explore campers and caravans**. Whether you're a seller looking for a buyer or a traveler searching for the perfect vehicle, this platform has got you covered.

---

## 🚀 Features

- **🛒 Marketplace Functionality**: Users can list and search campers and caravans.
- **🌍 Internationalization**: Supports two languages for global reach.
- **💱 Dynamic Exchange Rates**: Automatically refreshed daily for two currencies via a scheduled REST job.
- **📊 Search Analytics**: Tracks the number of offer searches performed, providing valuable analytics.
- **🔗 Microservices Architecture**:
  - Main project for core functionalities.
  - [**CampingCars-Brand-Rest-Microservice**](https://github.com/ViktorShterev/CampingCars-Brand-Rest-Microservice) handles brand and model data separately, communication via secure REST APIs using JSON and JWT tokens.
- **🔒 Secure Environment**: Uses Spring Security for robust user authentication and authorization.

---

## 🛠 Tech Stack

### Backend

- **Java**: Built with Java for robust and scalable backend development.
- **Main App**: Uses **Maven** as a build tool.
- **Microservice**: Uses **Gradle** as a build tool.
- **Spring Framework**:
  - 🌱 Spring Boot for rapid application development.
  - 📂 Spring Data for database operations.
  - 🔐 Spring Security for application security.

### Database

- **MySQL**: A reliable relational database used by both the main app and the microservice.

### Frontend

- **Thymeleaf**: Server-side rendering for dynamic and SEO-friendly pages.

### Architecture

- **MVC (Model-View-Controller)**: Ensures separation of concerns.
- **OOP and SOLID Principles**: Modular, flexible, and scalable codebase.

### Testing ✅

- **JUnit 5** for unit testing.
- **Mockito** for integration testing.

---

## 🧱 Microservices Structure

This project uses a microservice architecture:

- **Main App**: Handles core platform functionality (users, offers, searches, etc.)
- **Brand Microservice**: A separate project built with Spring Boot and Gradle that handles brand and model data for campers and caravans.
  - Exposes RESTful JSON APIs.
  - Communicates securely using JWT.
  - Runs on port `8081`.

---

## 📦 Running the Application with Docker Compose

Everything is containerized with Docker. A single `docker-compose.yml` located in the root directory spins up:

- Main App
- Brand Microservice
- Two MySQL databases

### 🔧 Prerequisites

- ✅ [Docker](https://docs.docker.com/get-docker/)
- ✅ [Docker Compose](https://docs.docker.com/compose/)
- ✅ `.env` file in the root directory:

<details>
<summary>Example <code>.env</code> file</summary>

```env
MYSQL_ROOT_PASSWORD=yourRootPassword
MYSQL_USER=yourDbUsername
MYSQL_PASSWORD=yourDbPassword
JWT_SECRET=yourSecretKey
APP_ID=yourOpenExchangeRatesAppId
```
</details>

---

### ▶️ Step-by-Step Guide

#### 1. **Clone the Repository**

```bash
git clone https://github.com/yourusername/camper-caravan-marketplace.git
cd camper-caravan-marketplace
```

---

#### 2. **Build Docker Images Locally** (Optional if using Docker Hub)

##### 📦 Main App (Maven)

```bash
docker build -t viktorshterev/camping-cars .
```

##### 🔗 Microservice (Gradle)

```bash
cd CampingCars-Brand-Rest-Microservice
docker build -t viktorshterev/brand-microservice:1.0 .
cd ..
```

---

#### 3. **Start the Full System**

```bash
docker-compose up --build
```

This command will:

- Start MySQL containers
- Start the microservice and main app
- Ensure database health before app startup
- Inject all environment variables from `.env`

---

#### 4. **Access the Platform**

| App               | URL                               |
|------------------|------------------------------------|
| 🗅 Main App       | http://localhost:8080              |
| 🛠 Brand API      | http://localhost:8081/api/brands   |
| 📃 MySQL (Main)   | Host: `localhost`, Port: `3306`    |
| 📃 MySQL (Brands) | Host: `localhost`, Port: `3307`    |

---

### 📂 Dockerfile Structure

#### Main App (Java + Maven)

```dockerfile
FROM openjdk:19-jdk-slim AS build
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml /app/
COPY src /app/src/
RUN mvn clean install -DskipTests

FROM openjdk:19-jdk-slim
WORKDIR /app
COPY --from=build /app/target/campingCars-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

#### Microservice (Java + Gradle)

```dockerfile
FROM openjdk:19-jdk-slim
WORKDIR /app
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY src src
COPY src/main/resources/data.sql /docker-entrypoint-initdb.d/
RUN chmod +x gradlew
RUN ./gradlew build -x test
COPY build/libs/new_brand_rest_app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 🧱 Docker Compose Services Overview

| Service               | Description                            | Port     |
|-----------------------|----------------------------------------|----------|
| `camping-cars`        | Main Spring Boot App                   | `8080`   |
| `brands-microservice` | Brand & Model Microservice (Spring)    | `8081`   |
| `mysql-db`            | MySQL for main app                     | `3306`   |
| `mysql-microservice`  | MySQL for microservice                 | `3307`   |

All services share a network and communicate via internal DNS names.

---

### 📄 Shutting Down

To stop all running containers:

```bash
docker-compose down
```

To remove volumes and networks as well:

```bash
docker-compose down -v
```

---

## 👌 Contributions

Pull requests are welcome! If you’d like to contribute, feel free to fork the repo and submit a PR.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

