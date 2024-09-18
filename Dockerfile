FROM maven:3.9.9-ibm-semeru-17-focal

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=0 /usr/src/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]