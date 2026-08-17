FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

COPY gradle/ gradle/
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY src/ src/

RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "--enable-native-access=ALL-UNNAMED", "-jar", "app.jar"] 

