    # ===== STAGE 1: BUILD =====
    FROM --platform=linux/amd64 maven:3.9.9-eclipse-temurin-17 AS build

    WORKDIR /app

    COPY pom.xml .
    COPY src ./src

    RUN mvn clean package -DskipTests


    # ===== STAGE 2: RUN =====
    FROM --platform=linux/amd64 eclipse-temurin:17-jre

    WORKDIR /app

    COPY --from=build /app/target/*.jar app.jar

    EXPOSE 8099

    ENTRYPOINT ["java", "-jar", "app.jar"]