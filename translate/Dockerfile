FROM openjdk:21-jdk

WORKDIR /app

COPY . /app

RUN ./mvnw clean package

EXPOSE 8080

CMD ["java", "-jar", "target/translate-beta.jar"]
