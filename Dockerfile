FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY build/libs/wallet_service-1.0.jar /app/wallet.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wallet.jar"]