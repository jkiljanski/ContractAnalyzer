FROM amazoncorretto:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ADD src/main/resources/application.yml application.yml
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app.jar", "--spring.config.location=classpath:file:/application.yml"]


#, "--spring.config.location=classpath:file:/app/application.yml"]