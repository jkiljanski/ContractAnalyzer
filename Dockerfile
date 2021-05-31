FROM amazoncorretto:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ADD src/main/resources/application.yml application.yml
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app.jar", "--spring.config.location=classpath:file:/application.yml"]



#, "-agentlib:jdwp=transport=dt_socket,address=*:8000,server=y,suspend=n"
#-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5050

#, "--spring.config.location=classpath:file:/app/application.yml"]
#
## Build stage
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY pom.xml /app/
#COPY src /app/src
#RUN mvn -f /app/pom.xml clean package
#
## Run stage
#FROM openjdk:8-jdk-alpine # Use your target JDK here !
#COPY --from=build /app/target/app*.jar /app/app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]