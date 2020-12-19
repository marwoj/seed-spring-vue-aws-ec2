FROM openjdk:11-jdk-slim
VOLUME /tmp
EXPOSE 8080
COPY ./build/libs/seed-spring-0.0.1-SNAPSHOT.jar ./tmp/app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://seed-mongo:27017/seed", "-Djava.security.egd=file:/dev/./urandom", "-jar","/tmp/app.jar"]
