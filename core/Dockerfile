FROM openjdk:17-alpine
ARG JAR_FILE=target/core-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=$SPRING_PROFILES_ACTIVE", "-Djasypt.encryptor.password=$JASYPT_ENCRYPTOR_PASSWORD", "-jar","/app.jar"]