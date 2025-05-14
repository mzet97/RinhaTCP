FROM openjdk:21-jdk-slim as build
WORKDIR /app
COPY src/EchoClient.java ./

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/EchoClient.class ./
COPY src/EchoClient.java ./
ENTRYPOINT ["java", "EchoClient"]