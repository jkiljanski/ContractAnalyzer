FROM amazoncorretto:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ADD src/main/resources/application.yml application.yml
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app.jar", "--spring.config.location=classpath:file:/application.yml"]



#, "-agentlib:jdwp=transport=dt_socket,address=*:8000,server=y,suspend=n"
#-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5050

#, "--spring.config.location=classpath:file:/app/application.yml"]

