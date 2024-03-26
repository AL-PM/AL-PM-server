FROM gradle:7.6-jdk AS build
WORKDIR /build

COPY src/main /build/src/main

COPY build.gradle.kts settings.gradle.kts /build/

RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

FROM openjdk:17.0.1-jdk-slim AS run
WORKDIR /app

RUN adduser --system --group app-api

COPY --from=build --chown=app-api:app-api /build/build/libs/*.jar ./app.jar

USER app-api

CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "app.jar"]