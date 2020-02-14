FROM openjdk:8-jdk-alpine AS build
COPY api-gate/ /app
# $ rm -rf .gradle/ build/
WORKDIR /app
RUN ./gradlew --no-daemon build

FROM openjdk:8-jdk-alpine AS prod
COPY --from=build /app/build/libs/api-gate-*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

FROM bash:4 as test
COPY --from=build /app /app
#WORKDIR /app
RUN apk add curl jq
RUN apk add wget

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz
CMD ["dockerize", "-wait", "http://api-gate:8080", "-timeout", "30s", "bash","/app/curl_8080_demo-temp.sh"]
