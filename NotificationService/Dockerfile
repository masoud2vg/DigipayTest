FROM maven:3.6.0-jdk-8-slim AS build
COPY src  /usr/src/app/src
COPY pom.xml /usr/src/app
#RUN mvn dependency:go-offline 
RUN mvn -f /usr/src/app/pom.xml package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY --from=build /usr/src/app/target/NotificationService-0.0.1-SNAPSHOT.jar /usr/app/NotificationService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/app/NotificationService-0.0.1-SNAPSHOT.jar"]