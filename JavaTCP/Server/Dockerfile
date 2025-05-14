FROM openjdk:21-jdk-slim as build
WORKDIR /app
COPY src/EchoServer.java ./

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/EchoServer.class ./
COPY src/EchoServer.java ./
ENTRYPOINT ["java", "EchoServer"]