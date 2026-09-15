
FROM ghcr.io/graalvm/native-image-community:21-ol9 AS build
WORKDIR /code

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN tr -d '\r' < mvnw > mvnw_unix && mv mvnw_unix mvnw && chmod +x mvnw

COPY src src

RUN ./mvnw package -Dnative -DskipTests

FROM oraclelinux:9-slim
WORKDIR /work/

COPY --from=build --chown=1001:root /code/target/*-runner /work/application
RUN chmod 775 /work/application

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]