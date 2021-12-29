FROM alpine:3.14
MAINTAINER Benjamin OFlaherty
RUN  apk update \
  && apk upgrade \
  && apk add ca-certificates \
  && update-ca-certificates \
  && apk add --update coreutils && rm -rf /var/cache/apk/*   \
  && apk add --update openjdk11 tzdata curl unzip bash \
  && apk add --no-cache nss \
  && rm -rf /var/cache/apk/*
COPY /build/libs/company-service-0.0.1-SNAPSHOT.jar company-service.jar
ENTRYPOINT ["java","-jar","/company-service.jar"]