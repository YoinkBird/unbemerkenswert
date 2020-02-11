FROM openjdk:8-jdk-alpine AS build
COPY api-gate/ /app
WORKDIR /app
RUN ./gradlew --no-daemon build

RUN ln -s /app/build/libs/api-gate-*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
