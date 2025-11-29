# Stage 1: Build
FROM eclipse-temurin:25-jdk as builder
WORKDIR /build
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=builder /build/target/NetfixClone-0.0.1-SNAPSHOT.jar app.jar

# Set PORT environment variable for Cloud Run
ENV PORT=8080

# Optional: expose port (good practice)
EXPOSE 8080

# Start Spring Boot with server.port set from environment variable
ENTRYPOINT ["java","-jar","app.jar","--server.port=${PORT}"]
