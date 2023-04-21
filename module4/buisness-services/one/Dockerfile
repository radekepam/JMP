FROM openjdk:17-jdk as build
WORKDIR /build
COPY .. .
RUN ./mvnw clean package -DskipTests


FROM openjdk:17-jdk
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]