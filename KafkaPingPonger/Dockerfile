FROM amazoncorretto:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8888
#ENTRYPOINT ["java","-jar", "/app.jar"]

ADD src/main/resources/application.yml application.yml
#EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app.jar", "--spring.config.location=classpath:file:/application.yml"]
