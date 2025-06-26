# Start with Java and Maven pre-installed image
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Second stage: run the JAR
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/ojp-v1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]