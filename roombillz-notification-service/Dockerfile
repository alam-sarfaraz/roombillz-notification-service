# ----------- Stage 1: Build -----------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests -B


# ----------- Stage 2: Runtime -----------
FROM eclipse-temurin:21-jre

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl

# Create non-root user
RUN useradd -ms /bin/bash appuser

# Working directory for application
WORKDIR /home/appuser/app

# Copy JAR file from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Give permission to appuser
RUN chown -R appuser:appuser /home/appuser/app

# Switch to appuser
USER appuser

# Expose correct service port
EXPOSE 8082

# Healthcheck
HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
  CMD curl -f http://localhost:8082/actuator/health || exit 1

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]
