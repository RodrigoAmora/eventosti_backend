# Build stage
FROM maven:3.9.5-amazoncorretto-21 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests


# Run stage
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
