FROM alpine:latest
MAINTAINER gabrielibson@gmail.com
EXPOSE 8080

RUN apk add openjdk11
COPY /build/libs/*.jar jsondiff.jar
ENTRYPOINT ["java", "-jar", "/jsondiff.jar"]