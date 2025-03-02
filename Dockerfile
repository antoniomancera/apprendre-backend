FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY . /app

RUN ./gradlew clean build -x test

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/apprendre-backend-*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
