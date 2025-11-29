# Stage 1: Build
FROM eclipse-temurin:25-jdk as builder
WORKDIR /build
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /build/target/NetfixClone-0.0.1-SNAPSHOT.jar app.jar

# Cloud Run port
ENV PORT=8080
EXPOSE 8080

# Start Spring Boot, binding to 0.0.0.0
ENTRYPOINT ["java","-jar","app.jar","--server.port=8080","--server.address=0.0.0.0"]
