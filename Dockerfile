# Builder
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app
COPY . .

# Compilacion
RUN mvn dependency:resolve
RUN mvn install -Dmaven.compiler.failOnError=false -DskipTests

# Ejecucion
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]