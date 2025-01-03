#create a Jar file
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
RUN gradle dependencies --no-daemon
COPY . /app
RUN gradle build --no-daemon

#build the image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
