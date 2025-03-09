FROM openjdk:19-jdk-slim AS build

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src/

RUN mvn clean install -DskipTests

FROM openjdk:19-jdk-slim

WORKDIR /app

COPY --from=build /app/target/campingCars-0.0.1-SNAPSHOT.jar /app/campingCars-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/campingCars-0.0.1-SNAPSHOT.jar"]
