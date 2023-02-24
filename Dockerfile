########
# Dockerfile to build delivery-service container image
#
########
FROM openjdk:17-slim

LABEL maintainer="Petrulin Alexander"

COPY target/delivery-service-*.jar app.jar

EXPOSE 8020

ENTRYPOINT ["java","-jar","/app.jar"]
