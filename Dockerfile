#FROM maven:3.6.3-jdk-11 AS build
FROM maven:3.6.3-openjdk-17 AS build
#FROM maven:3.8.3-openjdk-17 AS build
COPY src /usr/src/app/src
COPY src/main/resources/storage /usr/src/app/storage
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java17-debian11

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
COPY src/main/resources/storage /usr/app/storage

ENV SPRING_SERVER_PORT=8080
ENV DATABASE_HOST=localhost
ENV DATABASE_USER=root
ENV DATABASE_PASSWORD=root
ENV DATABASE_NAME=db_name
ENV DATABASE_PORT=3306

EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
