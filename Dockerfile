FROM amazoncorretto:11-alpine-jdk
MAINTAINER upload
COPY target/upload-0.0.1-SNAPSHOT.jar  upload.jar

RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN update-ms-fonts

ENTRYPOINT ["java","-jar","/upload.jar"]