# ============================================================
# Stage 1 — Build com Maven (JDK completo)
# ============================================================
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw

COPY src src
RUN ./mvnw -B -DskipTests package

# ============================================================
# Stage 2 — Runtime (só JRE, imagem slim)
# ============================================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
