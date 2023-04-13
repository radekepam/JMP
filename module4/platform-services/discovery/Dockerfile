FROM openjdk:17-jdk as build
WORKDIR /build
COPY .. .
RUN ./mvnw clean package


FROM openjdk:17-jdk
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]