FROM openjdk:22-jdk
ARG FILE=target/shop-app-email-0.0.1-SNAPSHOT.jar
ARG DEPENDENCY=target/original-shop-app-email-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY ${FILE} app.jar
COPY ${DEPENDENCY} dop.jar
CMD ["java", "-jar", "app.jar"]