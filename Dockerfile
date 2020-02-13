FROM openjdk:8-jdk-alpine AS build
COPY api-gate/ /app
WORKDIR /app
RUN ./gradlew --no-daemon build

FROM openjdk:8-jdk-alpine AS prod
COPY --from=build /app/build/libs/api-gate-*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

FROM bash:4 as test
COPY --from=build /app /app
CMD /app/curl_8080_demo.sh
